package ru.smile.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.AddrRequest;
import ru.smile.entities.AddrResponse;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;
import ru.smile.services.ExcelCsvService;
import ru.smile.services.PochtaService;
import ru.smile.services.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelHelper {

//  @Autowired UserService userService;

  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  static String SHEET = "Нормализованные адреса";

//  { "Идентификатор записи", "Тип адреса", "Район", "Часть здания: Строение", "Часть здания: Корпус",
//    "Название гостиницы", "Часть адреса: Номер здания", "Почтовый индекс", "Часть здания: Литера", "Микрорайон",
//    "Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта", "Оригинальные адрес одной строкой",
//    "Населенный пункт", "Код качества нормализации адреса", "Область, регион", "Часть здания: Номер помещения",
//    "Часть здания: Дробь", "Часть адреса: Улица", "Код проверки нормализации адреса"};


  public static boolean hasExcelFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public List<ValidateRequest> excelToValidateRequest(InputStream is, UserService userService) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rows = sheet.iterator();

      List<ValidateRequest> validateRequestList = new ArrayList<ValidateRequest>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // пропуск шапки
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        ValidateRequest validateRequest = new ValidateRequest();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              validateRequest.setId((long) currentCell.getNumericCellValue());
              break;

            case 1:
              AddrRequest addrRequest = new AddrRequest(currentCell.getStringCellValue());
              validateRequest.setAddr(new ArrayList<>(Collections.singletonList(addrRequest)));
              break;

            default:
              break;
          }

          validateRequest.setReqId(PochtaService.reqId);
          validateRequest.setVersion(PochtaService.version);
          validateRequest.setUserId(userService.getUserId());

          cellIdx++;
        }

        validateRequestList.add(validateRequest);
      }

      workbook.close();

      return validateRequestList;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка парсинга xlsx-файла: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream validateResponseToExcel(List<ValidateResponse> validateResponseList) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLUE.getIndex());

      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < ExcelCsvService.HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(ExcelCsvService.HEADERs[col]);
        cell.setCellStyle(headerCellStyle);
      }

      int rowIdx = 1;
      for (ValidateResponse validateResponse : validateResponseList) {
        Row row = sheet.createRow(rowIdx++);

        String outAddr = "";
        String inAddr = "";
        if (validateResponse.getAddr() != null) {
          outAddr = validateResponse.getAddr().getOutaddr() != null ? validateResponse.getAddr().getOutaddr() : "";
          inAddr = validateResponse.getAddr().getInaddr() != null ? validateResponse.getAddr().getInaddr() : "";
        }
//        row.createCell(0).setCellValue(validateResponse.getId()); // Идентификатор записи
        row.createCell(0).setCellValue(inAddr); // "Первоначальный адрес одной строкой"
        row.createCell(1).setCellValue(outAddr); // "Найденный адрес одной строкой"

//        row.createCell(1).setCellValue(cleanAddress.getAddressType()); // Тип адреса
//        row.createCell(2).setCellValue(cleanAddress.getArea()); // Район
//        row.createCell(3).setCellValue(cleanAddress.getRegion()); // Область, регион
//        row.createCell(4).setCellValue(cleanAddress.getPlace()); // Населенный пункт
//        row.createCell(5).setCellValue(cleanAddress.getLocation()); // Микрорайон
//        row.createCell(6).setCellValue(cleanAddress.getStreet()); // Часть адреса: Улица
//        row.createCell(7).setCellValue(cleanAddress.getHouse()); // Часть адреса: Номер здания
//        row.createCell(8).setCellValue(cleanAddress.getBuilding()); // Часть здания: Строение
//        row.createCell(9).setCellValue(cleanAddress.getCorpus()); // Часть здания: Корпус
//        row.createCell(10).setCellValue(cleanAddress.getSlash()); // Часть здания: Дробь
//        row.createCell(11).setCellValue(cleanAddress.getLetter()); // Часть здания: Литера
//        row.createCell(12).setCellValue(cleanAddress.getRoom()); // Часть здания: Номер помещения
//        row.createCell(13).setCellValue(cleanAddress.getIndex()); // Почтовый индекс
//        row.createCell(14).setCellValue(cleanAddress.getHotel()); // Название гостиницы
//        row.createCell(15).setCellValue(cleanAddress.getNumAddressType()); // Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта
//        row.createCell(16).setCellValue(cleanAddress.getQualityCode()); // Код качества нормализации адреса
//        row.createCell(17).setCellValue(cleanAddress.getValidationCode()); // Код проверки нормализации адреса
//        row.createCell(18).setCellValue(cleanAddress.getOriginalAddress()); // Оригинальные адрес одной строкой
      }

      workbook.write(out);
      workbook.close();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Ошибка импорта в эксель: " + e.getMessage());
    }
  }
}

