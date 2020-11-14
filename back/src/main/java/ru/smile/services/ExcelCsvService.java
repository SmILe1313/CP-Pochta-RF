package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;
import ru.smile.repositories.AddrRequestRepository;
import ru.smile.repositories.ValidateRequestRepository;
import ru.smile.utils.CsvHelper;
import ru.smile.utils.ExcelHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ExcelCsvService {

  @Autowired ValidateRequestRepository validateRequestRepository;

  @Autowired AddrRequestRepository addrRequestRepository;

  @Autowired ValidateResponseService validateResponseService;

  @Autowired PochtaService pochtaService;

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

  public List<ValidateResponse> saveAndNormalizeXlsx(MultipartFile file) {
    try {
      List<ValidateRequest> validateRequestList = excelHelper.excelToValidateRequest(file.getInputStream(), userService);
      validateRequestList.forEach(validateRequest -> addrRequestRepository.saveAll(validateRequest.getAddr()));
      validateRequestRepository.saveAll(validateRequestList);

      List<ValidateResponse> validateResponseList = pochtaService.normalizeAddressApi(validateRequestList);
      validateResponseService.saveList(validateResponseList);

      List<ValidateResponse> validateResponseListWithErrors = validateResponseService.getWithErrors();
      return validateResponseListWithErrors;
    } catch (Exception e) {
      throw new RuntimeException("Ошибка чтения xlsx-файла: " + e.getMessage());
    }
  }

  public List<ValidateResponse> saveAndNormalizeCsv(MultipartFile file) {
    try {
      List<ValidateRequest> validateRequestList = csvHelper.csvToValidateRequest(file.getInputStream(), userService);
      validateRequestRepository.saveAll(validateRequestList);

      List<ValidateResponse> validateResponseList = pochtaService.normalizeAddressApi(validateRequestList);
      validateResponseService.saveList(validateResponseList);

      List<ValidateResponse> validateResponseListWithErrors = validateResponseService.getWithErrors();
      return validateResponseListWithErrors;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения csv-файла: " + e.getMessage());
    }
  }

  // Скачать XLSX
  public ByteArrayInputStream downloadFileXlsx() {
    List<ValidateResponse> validateResponseList = validateResponseService.getWithoutErrors();

    ByteArrayInputStream in = ExcelHelper.validateResponseToExcel(validateResponseList);
    return in;
  }

  // Скачать CSV
  public ByteArrayInputStream downloadFileCsv() {
    List<ValidateResponse> validateResponseList = validateResponseService.getWithoutErrors();

    ByteArrayInputStream in = CsvHelper.validateResponseToCSV(validateResponseList);
    return in;
  }



  public List<ValidateResponse> getAllValidateResponses() {
    return validateResponseService.getAll();
  }
}

