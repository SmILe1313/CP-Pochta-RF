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
}

