package ru.smile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ValidateResponse;
import ru.smile.entities.ValidateResponseCounts;
import ru.smile.services.ExcelCsvService;
import ru.smile.utils.CsvHelper;
import ru.smile.utils.ExcelHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/excel")
public class ExcelCsvController {

  @Autowired ExcelCsvService excelCsvService;

  @PostMapping("/clean/address")
  public ResponseEntity<ValidateResponseCounts> cleanFile(@RequestParam("files") MultipartFile file)  throws IOException {
    String message = "";

    // Если файл xlsx
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        ValidateResponseCounts validateResponseCounts = excelCsvService.saveAndNormalizeXlsx(file);

        message = "Файл успешно обработан: " + file.getOriginalFilename();
        return new ResponseEntity<>(validateResponseCounts, HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Невозможно загрузить xlsx-файл: " + file.getOriginalFilename() + "!";
        return new ResponseEntity<ValidateResponseCounts>(new ValidateResponseCounts(), HttpStatus.BAD_REQUEST);
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    // Если файл csv
    if (CsvHelper.hasCSVFormat(file)) {
      try {
        ValidateResponseCounts validateResponseCounts = excelCsvService.saveAndNormalizeCsv(file);

        message = "Файл успешно обработан: " + file.getOriginalFilename();
        return new ResponseEntity<>(validateResponseCounts, HttpStatus.OK);
      } catch (Exception e) {
        message = "Невозможно загрузить csv-файл: " + file.getOriginalFilename() + "!";
        return new ResponseEntity<ValidateResponseCounts>(new ValidateResponseCounts(), HttpStatus.BAD_REQUEST);
      }
    }

    message = "Пожалуйста, выберите xlsx или csv файл!";
    return new ResponseEntity<ValidateResponseCounts>(new ValidateResponseCounts(), HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/clean/google/{googleLink}")
  public ResponseEntity<ValidateResponseCounts> getGoogleSpreadsheet(@PathVariable(value = "googleLink") String googleLink) {
    ValidateResponseCounts validateResponseCounts = excelCsvService.getGoogleFile(googleLink);
    return new ResponseEntity<>(validateResponseCounts, HttpStatus.OK);
  }

  @GetMapping("/download/xlsx/{delivery}")
  public ResponseEntity<InputStreamResource> getFileXlsx(@PathVariable(value = "delivery") int delivery) {
    String filename = "The_normalized_addresses.xlsx";
    InputStreamResource file = new InputStreamResource(excelCsvService.downloadFileXlsx(delivery));

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

    return ResponseEntity.ok()
      .headers(headers)
      .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
      .body(file);
  }

  @GetMapping("/download/csv")
  public ResponseEntity<Resource> getFileCsv() {
    String filename = "Нормализованные адреса.csv";
    InputStreamResource file = new InputStreamResource(excelCsvService.downloadFileCsv());

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
      .contentType(MediaType.parseMediaType("application/csv"))
      .body(file);
  }
}

