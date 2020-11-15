package ru.smile.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smile.entities.UserAuth;
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
  public ValidateResponse normalizeAddressApi(ValidateRequest validateRequest) {
    List<ValidateRequest> validateRequestList = new ArrayList<>();
    if (validateRequest != null) {
      validateRequestList.add(validateRequest);
    }

    // Нормализуем через апи Почты РФ
    List<ValidateResponse> validateResponseList = normalizeAddressApi(validateRequestList);
    return validateResponseList.isEmpty() ? new ValidateResponse() : validateResponseList.get(0);
  }

  public UUID getResponseUUID() {
    return UUID.randomUUID();
  }

}

