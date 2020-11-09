package ru.smile.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;

public class CsvHelper {
  public static String TYPE = "text/csv";

  public static boolean hasCSVFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public static List<ToCleanAddress> csvToCleanAddress(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
         CSVParser csvParser = new CSVParser(fileReader,
           CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<ToCleanAddress> toCleanAddresses = new ArrayList<ToCleanAddress>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        ToCleanAddress tutorial = new ToCleanAddress(
          Long.parseLong(csvRecord.get("Id")),
          csvRecord.get("Адрес")
        );

        toCleanAddresses.add(tutorial);
      }

      return toCleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтени CSV файла: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream cleanAddressToCSV(List<CleanAddress> cleanAddresses) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
         CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (CleanAddress cleanAddress : cleanAddresses) {
        List<String> data = Arrays.asList(
          String.valueOf(cleanAddress.getId()), // Идентификатор записи
          cleanAddress.getAddressType(), // Тип адреса
          cleanAddress.getArea(), // Район
          cleanAddress.getRegion(), // Область, регион
          cleanAddress.getPlace(), // Населенный пункт
          cleanAddress.getLocation(), // Микрорайон
          cleanAddress.getStreet(), // Часть адреса: Улица
          cleanAddress.getHouse(), // Часть адреса: Номер здания
          cleanAddress.getBuilding(), // Часть здания: Строение
          cleanAddress.getCorpus(), // Часть здания: Корпус
          cleanAddress.getSlash(), // Часть здания: Дробь
          cleanAddress.getLetter(), // Часть здания: Литера
          cleanAddress.getRoom(), // Часть здания: Номер помещения
          cleanAddress.getIndex(), // Почтовый индекс
          cleanAddress.getHotel(), // Название гостиницы
          cleanAddress.getNumAddressType(), // Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта
          cleanAddress.getQualityCode(), // Код качества нормализации адреса
          cleanAddress.getValidationCode(), // Код проверки нормализации адреса
          cleanAddress.getOriginalAddress() // Оригинальные адрес одной строкой
        );
        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Ошибка импорта в csv: " + e.getMessage());
    }
  }

}