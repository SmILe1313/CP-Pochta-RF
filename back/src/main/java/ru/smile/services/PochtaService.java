package ru.smile.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/** Нормализация адреса */
@Service
public class PochtaService {

  private final static String apiLink = "https://address.pochta.ru/validate/api/v7_1";
  public final static String version = "demo";
  public final static String reqId = "12204cb4-37fb-4059-91e6-c6e17e946d7f";
  public final static String token = "53fb9daa-7f06-481f-aad6-c6a7a58ec0bb";

  public List<ValidateResponse> normalizeAddressApi(List<ValidateRequest> validateRequestList) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("AuthCode", token );
//    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
//    headers.add("X-User-Authorization", "Basic " + userKey );
//    List<ValidateResponse> validateResponseList = new ArrayList<ValidateResponse>();

      UUID newResponseUUID = getResponseUUID();
      List<ValidateResponse> validateResponseList = validateRequestList.stream().map(validateRequest -> {
      try {
        HttpEntity<ValidateRequest> request = new HttpEntity<ValidateRequest>(validateRequest, headers);

        ResponseEntity<ValidateResponse> response = restTemplate.postForEntity(apiLink, request, ValidateResponse.class);
        ValidateResponse validateResponse = response.getBody();
        ValidateResponseService.setResponseUuid(newResponseUUID);
        validateResponse.setResponseUuid(newResponseUUID);
        return validateResponse;
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return new ValidateResponse();
      }
    }).collect(Collectors.toList());

    return validateResponseList;
  }

  // Для повторной нормализации построчно
  public CleanAddress normalizeAddressApi(ToCleanAddress toCleanAddress) {
    List<ToCleanAddress> toCleanAddresses = new ArrayList<>();
    if (toCleanAddress != null) {
      toCleanAddresses.add(toCleanAddress);
    }

    // Нормализуем через апи Почты РФ
//    List<CleanAddress> cleanAddresses = normalizeAddressApi(toCleanAddresses);
    List<CleanAddress> cleanAddresses = new ArrayList<>();

    CleanAddress cleanAddress;
    if (!cleanAddresses.isEmpty()) {
      cleanAddress = cleanAddresses.get(0);
    } else {
      cleanAddress = new CleanAddress(toCleanAddress.getId());
    }
    return cleanAddress;
  }

  public UUID getResponseUUID() {
    return UUID.randomUUID();
  }

  // Обратно в одну строку
  public ToCleanAddress toOneString(CleanAddress cleanAddress) {
    return new ToCleanAddress(
      cleanAddress.getId(),
      cleanAddress.getAddressType() + ' ' + // Тип адреса
      cleanAddress.getArea() + ' ' + // Район
      cleanAddress.getRegion() + ' ' + // Область, регион
      cleanAddress.getPlace() + ' ' + // Населенный пункт
      cleanAddress.getLocation() + ' ' + // Микрорайон
      cleanAddress.getStreet() + ' ' + // Часть адреса: Улица
      cleanAddress.getHouse() + ' ' + // Часть адреса: Номер здания
      cleanAddress.getBuilding() + ' ' + // Часть здания: Строение
      cleanAddress.getCorpus() + ' ' + // Часть здания: Корпус
      cleanAddress.getSlash() + ' ' + // Часть здания: Дробь
      cleanAddress.getLetter() + ' ' + // Часть здания: Литера
      cleanAddress.getRoom() + ' ' + // Часть здания: Номер помещения
      cleanAddress.getIndex() + ' ' + // Почтовый индекс
      cleanAddress.getHotel() + ' ' + // Название гостиницы
      cleanAddress.getNumAddressType() + ' ' + // Номер для а/я, войсковая часть, войсковая часть ЮЯ,
      cleanAddress.getQualityCode() + ' ' + // Код качества нормализации адреса
      cleanAddress.getValidationCode() + ' ' + // Код проверки нормализации адреса
      cleanAddress.getOriginalAddress() // Оригинальные адрес одной строкой
    );
  }

}

