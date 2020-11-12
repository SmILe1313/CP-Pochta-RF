package ru.smile.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Идентификатор записи", "Тип адреса", "Район", "Область, регион", "Населенный пункт",
    "Микрорайон", "Часть адреса: Улица", "Часть адреса: Номер здания", "Часть здания: Строение", "Часть здания: Корпус",
    "Часть здания: Дробь", "Часть здания: Литера", "Часть здания: Номер помещения", "Почтовый индекс",
    "Название гостиницы",
    "Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта",
    "Код качества нормализации адреса", "Код проверки нормализации адреса",
    "Оригинальные адрес одной строкой"};
  static String SHEET = "Нормализованные адреса";

//  { "Идентификатор записи", "Тип адреса", "Район", "Часть здания: Строение", "Часть здания: Корпус",
//    "Название гостиницы", "Часть адреса: Номер здания", "Почтовый индекс", "Часть здания: Литера", "Микрорайон",
//    "Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта", "Оригинальные адрес одной строкой",
//    "Населенный пункт", "Код качества нормализации адреса", "Область, регион", "Часть здания: Номер помещения",
//    "Часть здания: Дробь", "Часть адреса: Улица", "Код проверки нормализации адреса"};

  static Long user_id = 1L;


  public static boolean hasExcelFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public static List<ToCleanAddress> excelToCleanAddress(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rows = sheet.iterator();

      List<ToCleanAddress> toCleanAddresses = new ArrayList<ToCleanAddress>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // пропуск шапки
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        ToCleanAddress toCleanAddress = new ToCleanAddress();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              toCleanAddress.setId((long) currentCell.getNumericCellValue());
              break;

            case 1:
              toCleanAddress.setOriginalAddress(currentCell.getStringCellValue());
              break;

            default:
              break;
          }

          toCleanAddress.setUserId(user_id);

          cellIdx++;
        }

        toCleanAddresses.add(toCleanAddress);
      }

      workbook.close();

      return toCleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка парсинга xlsx-файла: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream cleanAddressToExcel(List<CleanAddress> cleanAddresses) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLUE.getIndex());

      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
        cell.setCellStyle(headerCellStyle);
      }

      int rowIdx = 1;
      for (CleanAddress cleanAddress : cleanAddresses) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(cleanAddress.getId()); // Идентификатор записи
        row.createCell(1).setCellValue(cleanAddress.getAddressType()); // Тип адреса
        row.createCell(2).setCellValue(cleanAddress.getArea()); // Район
        row.createCell(3).setCellValue(cleanAddress.getRegion()); // Область, регион
        row.createCell(4).setCellValue(cleanAddress.getPlace()); // Населенный пункт
        row.createCell(5).setCellValue(cleanAddress.getLocation()); // Микрорайон
        row.createCell(6).setCellValue(cleanAddress.getStreet()); // Часть адреса: Улица
        row.createCell(7).setCellValue(cleanAddress.getHouse()); // Часть адреса: Номер здания
        row.createCell(8).setCellValue(cleanAddress.getBuilding()); // Часть здания: Строение
        row.createCell(9).setCellValue(cleanAddress.getCorpus()); // Часть здания: Корпус
        row.createCell(10).setCellValue(cleanAddress.getSlash()); // Часть здания: Дробь
        row.createCell(11).setCellValue(cleanAddress.getLetter()); // Часть здания: Литера
        row.createCell(12).setCellValue(cleanAddress.getRoom()); // Часть здания: Номер помещения
        row.createCell(13).setCellValue(cleanAddress.getIndex()); // Почтовый индекс
        row.createCell(14).setCellValue(cleanAddress.getHotel()); // Название гостиницы
        row.createCell(15).setCellValue(cleanAddress.getNumAddressType()); // Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта
        row.createCell(16).setCellValue(cleanAddress.getQualityCode()); // Код качества нормализации адреса
        row.createCell(17).setCellValue(cleanAddress.getValidationCode()); // Код проверки нормализации адреса
        row.createCell(18).setCellValue(cleanAddress.getOriginalAddress()); // Оригинальные адрес одной строкой
      }

      workbook.write(out);
      workbook.close();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Ошибка импорта в эксель: " + e.getMessage());
    }
  }
}

