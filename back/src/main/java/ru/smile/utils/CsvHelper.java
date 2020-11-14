package ru.smile.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.AddrRequest;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;
import ru.smile.services.ExcelCsvService;
import ru.smile.services.UserService;

@Component
public class CsvHelper {

//  @Autowired UserService userService;

  public static Set<String> TYPE = new HashSet<>(Arrays.asList("text/csv", "application/vnd.ms-excel"));

  public static boolean hasCSVFormat(MultipartFile file) {
    return TYPE.contains(file.getContentType());
  }

  public List<ValidateRequest> csvToValidateRequest(InputStream is, UserService userService) {
    final CSVFormat format = CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();//.withDelimiter(';');

    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
         CSVParser csvParser = new CSVParser(fileReader,format);) {

      List<ValidateRequest> validateRequestList = new ArrayList<ValidateRequest>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        ValidateRequest validateRequest = new ValidateRequest(
          Long.parseLong(csvRecord.get("Id")),
          new ArrayList<>(Collections.singletonList(new AddrRequest(csvRecord.get("Address"))))
        );

        validateRequest.setUserId(userService.getUserId());
        validateRequestList.add(validateRequest);
      }

      return validateRequestList;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтени CSV файла: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream validateResponseToCSV(List<ValidateResponse> validateResponseList) {
    final CSVFormat format = CSVFormat.EXCEL.withQuoteMode(QuoteMode.MINIMAL).withFirstRecordAsHeader();

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
         CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8)), format);) {

      csvPrinter.printRecord((Object) ExcelCsvService.HEADERs);

      for (ValidateResponse validateResponse : validateResponseList) {
        List<String> data = Arrays.asList(
          String.valueOf(validateResponse.getId()) // Идентификатор записи
//          cleanAddress.getAddressType(), // Тип адреса
//          cleanAddress.getArea(), // Район
//          cleanAddress.getRegion(), // Область, регион
//          cleanAddress.getPlace(), // Населенный пункт
//          cleanAddress.getLocation(), // Микрорайон
//          cleanAddress.getStreet(), // Часть адреса: Улица
//          cleanAddress.getHouse(), // Часть адреса: Номер здания
//          cleanAddress.getBuilding(), // Часть здания: Строение
//          cleanAddress.getCorpus(), // Часть здания: Корпус
//          cleanAddress.getSlash(), // Часть здания: Дробь
//          cleanAddress.getLetter(), // Часть здания: Литера
//          cleanAddress.getRoom(), // Часть здания: Номер помещения
//          cleanAddress.getIndex(), // Почтовый индекс
//          cleanAddress.getHotel(), // Название гостиницы
//          cleanAddress.getNumAddressType(), // Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта
//          cleanAddress.getQualityCode(), // Код качества нормализации адреса
//          cleanAddress.getValidationCode(), // Код проверки нормализации адреса
//          cleanAddress.getOriginalAddress() // Оригинальные адрес одной строкой
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