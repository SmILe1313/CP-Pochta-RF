package ru.smile.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Нормализация адреса */
@Service
public class OtpravkaService {

  private final static String apiLink = "https://otpravka-api.pochta.ru/1.0/clean/address";
  private final static String accessToken = "";
  private final static String userKey = "";

  public List<CleanAddress> normalizeAddressApi(List<ToCleanAddress> toCleanAddresses) {
    final RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
    headers.add("Authorization", "AccessToken " + accessToken );
    headers.add("X-User-Authorization", "Basic " + userKey );

    final HttpEntity<List<ToCleanAddress>> request = new HttpEntity<List<ToCleanAddress>>(toCleanAddresses,headers);

    final ResponseEntity<CleanAddress[]> response = restTemplate.postForEntity(apiLink, request, CleanAddress[].class);
    CleanAddress[] cleanAddressesMassive = response.getBody();
    List<CleanAddress> cleanAddresses = new ArrayList<CleanAddress>();
    if (cleanAddressesMassive != null) {
      cleanAddresses = Arrays.asList(cleanAddressesMassive);
    }
    return cleanAddresses;
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

