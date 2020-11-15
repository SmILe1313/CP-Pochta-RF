package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.UserAuth;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;
import ru.smile.entities.ValidateResponseCounts;
import ru.smile.repositories.AddrRequestRepository;
import ru.smile.repositories.ValidateRequestRepository;
import ru.smile.utils.CsvHelper;
import ru.smile.utils.ExcelHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

//  public static String[] HEADERs = { "Идентификатор записи", "Тип адреса", "Район", "Область, регион", "Населенный пункт",
//    "Микрорайон", "Часть адреса: Улица", "Часть адреса: Номер здания", "Часть здания: Строение", "Часть здания: Корпус",
//    "Часть здания: Дробь", "Часть здания: Литера", "Часть здания: Номер помещения", "Почтовый индекс",
//    "Название гостиницы",
//    "Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта",
//    "Код качества нормализации адреса", "Код проверки нормализации адреса",
//    "Оригинальные адрес одной строкой"};
public static String[] HEADERs =  { "Первоначальный адрес одной строкой", "Найденный адрес одной строкой"};

  public ValidateResponseCounts saveAndNormalizeXlsx(MultipartFile file) throws IOException {
    return saveAndNormalizeXlsx(file.getInputStream());
  }

  public ValidateResponseCounts saveAndNormalizeXlsx(InputStream is) {
    try {
      List<ValidateRequest> validateRequestList = excelHelper.excelToValidateRequest(is, userService);
      validateRequestList.forEach(validateRequest -> addrRequestRepository.saveAll(validateRequest.getAddr()));
      validateRequestRepository.saveAll(validateRequestList);

      List<ValidateResponse> validateResponseList = pochtaService.normalizeAddressApi(validateRequestList);
      validateResponseService.saveList(validateResponseList);

      ValidateResponseCounts validateResponseCounts = validateResponseService.getCounts();
      return validateResponseCounts;
    } catch (Exception e) {
      throw new RuntimeException("Ошибка чтения xlsx-файла: " + e.getMessage());
    }
  }

  public ValidateResponseCounts saveAndNormalizeCsv(MultipartFile file) {
    try {
      List<ValidateRequest> validateRequestList = csvHelper.csvToValidateRequest(file.getInputStream(), userService);
      validateRequestList.forEach(validateRequest -> addrRequestRepository.saveAll(validateRequest.getAddr()));
      validateRequestRepository.saveAll(validateRequestList);

      List<ValidateResponse> validateResponseList = pochtaService.normalizeAddressApi(validateRequestList);
      validateResponseService.saveList(validateResponseList);

      ValidateResponseCounts validateResponseCounts = validateResponseService.getCounts();
      return validateResponseCounts;
    } catch (Exception e) {
      throw new RuntimeException("Ошибка чтения csv-файла: " + e.getMessage());
    }
  }

  // Скачать XLSX
  public ByteArrayInputStream downloadFileXlsx(int delivery) {
    List<ValidateResponse> validateResponseList;
    switch (delivery) {
      case 0:
        validateResponseList = validateResponseService.getGood();
        break;
      case 1:
        validateResponseList = validateResponseService.getMiddle();
        break;
      case 2:
        validateResponseList = validateResponseService.getBad();
        break;
      case -1:
        validateResponseList = validateResponseService.getAll();
        break;
      default:
        validateResponseList = new ArrayList<>();
        break;
    }
    ByteArrayInputStream in = ExcelHelper.validateResponseToExcel(validateResponseList);
    return in;
  }

  // Скачать CSV
  public ByteArrayInputStream downloadFileCsv() {
    List<ValidateResponse> validateResponseList = validateResponseService.getGood();

    ByteArrayInputStream in = CsvHelper.validateResponseToCSV(validateResponseList);
    return in;
  }

  public List<ValidateResponse> getAllValidateResponses() {
    return validateResponseService.getAll();
  }

  public ValidateResponseCounts getGoogleFile(String googleLink) {
    String startLink = "https://docs.google.com/spreadsheets/d/";
    String endLink = "/export?format=xlsx&gid=0";
    if (googleLink.isEmpty()) {
      return null;
    }

    String googleDoc = startLink + googleLink + endLink;
    InputStream inputStream = null;

    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(
        new ByteArrayHttpMessageConverter());

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

      HttpEntity<String> entity = new HttpEntity<String>(headers);

      ResponseEntity<byte[]> response = restTemplate.exchange(googleDoc,
        HttpMethod.GET, entity, byte[].class, "1");

      if (response.getStatusCode() == HttpStatus.OK) {
         inputStream = new ByteArrayInputStream(response.getBody());
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return saveAndNormalizeXlsx(inputStream);
  }
}

