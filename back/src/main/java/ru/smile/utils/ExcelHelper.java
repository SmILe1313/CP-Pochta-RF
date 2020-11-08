package ru.smile.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.ToCleanAddress;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "id", "original-address"};

  static Long user_id = 1L;


  public static boolean hasExcelFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public static List<ToCleanAddress> excelToCleanAddress(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheetAt(1);
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
              toCleanAddress.setOriginal_address(currentCell.getStringCellValue());
              break;

            default:
              break;
          }

          toCleanAddress.setUser_id(user_id);

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
}

