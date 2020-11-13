package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.repositories.ToCleanAddressRepository;
import ru.smile.utils.CsvHelper;
import ru.smile.utils.ExcelHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ExcelCsvService {

  @Autowired ToCleanAddressRepository toCleanAddressRepository;

  @Autowired CleanAddresService cleanAddresessService;

  @Autowired OtpravkaService otpravkaService;

  @Autowired ExcelHelper excelHelper;

  @Autowired CsvHelper csvHelper;

  @Autowired UserService userService;

  public static String[] HEADERs = { "Идентификатор записи", "Тип адреса", "Район", "Область, регион", "Населенный пункт",
    "Микрорайон", "Часть адреса: Улица", "Часть адреса: Номер здания", "Часть здания: Строение", "Часть здания: Корпус",
    "Часть здания: Дробь", "Часть здания: Литера", "Часть здания: Номер помещения", "Почтовый индекс",
    "Название гостиницы",
    "Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта",
    "Код качества нормализации адреса", "Код проверки нормализации адреса",
    "Оригинальные адрес одной строкой"};

  public List<CleanAddress> saveAndNormalizeXlsx(MultipartFile file) {
    try {
      List<ToCleanAddress> toCleanAddresses = excelHelper.excelToCleanAddress(file.getInputStream(), userService);
      toCleanAddressRepository.saveAll(toCleanAddresses);
//      List<CleanAddress> cleanAddresses = otpravkaService.normalizeAddressApi(toCleanAddresses);
//      cleanAddresessService.saveList(cleanAddresses);
      List<CleanAddress> cleanAddresses = cleanAddresessService.getWithErrors();
      return cleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения xlsx-файла: " + e.getMessage());
    }
  }

  public List<CleanAddress> saveAndNormalizeCsv(MultipartFile file) {
    try {
      List<ToCleanAddress> toCleanAddresses = csvHelper.csvToCleanAddress(file.getInputStream(), userService);
      toCleanAddressRepository.saveAll(toCleanAddresses);
//      List<CleanAddress> cleanAddresses = otpravkaService.normalizeAddressApi(toCleanAddresses);
//      cleanAddresessService.saveList(cleanAddresses);
      List<CleanAddress> cleanAddresses = cleanAddresessService.getWithErrors();
      return cleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения csv-файла: " + e.getMessage());
    }
  }

  // Скачать XLSX
  public ByteArrayInputStream downloadFileXlsx() {
    List<CleanAddress> cleanAddresses = cleanAddresessService.getWithoutErrors();

    ByteArrayInputStream in = ExcelHelper.cleanAddressToExcel(cleanAddresses);
    return in;
  }

  // Скачать CSV
  public ByteArrayInputStream downloadFileCsv() {
    List<CleanAddress> cleanAddresses = cleanAddresessService.getWithoutErrors();

    ByteArrayInputStream in = CsvHelper.cleanAddressToCSV(cleanAddresses);
    return in;
  }



  public List<CleanAddress> getAllCleanAddresses() {
    return cleanAddresessService.getAll();
  }
}

