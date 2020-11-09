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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.services.ExcelCsvService;
import ru.smile.utils.CsvHelper;
import ru.smile.utils.ExcelHelper;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/excel")
public class ExcelCsvController {

  @Autowired
  ExcelCsvService excelService;

  @PostMapping("/clean/address")
  public ResponseEntity<List<CleanAddress>> cleanFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    // Если файл xlsx
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        List<CleanAddress> cleanAddresses = excelService.saveAndNormalizeXlsx(file);

        message = "Файл успешно обработан: " + file.getOriginalFilename();
        return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Невозможно загрузить xlsx-файл: " + file.getOriginalFilename() + "!";
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    // Если файл csv
    if (CsvHelper.hasCSVFormat(file)) {
      try {
        List<CleanAddress> cleanAddresses = excelService.saveAndNormalizeCsv(file);

        message = "Файл успешно обработан: " + file.getOriginalFilename();
        return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
      } catch (Exception e) {
        message = "Невозможно загрузить csv-файл: " + file.getOriginalFilename() + "!";
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
      }
    }

    message = "Пожалуйста, выберите xlsx или csv файл!";
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/download/xlsx")
  public ResponseEntity<Resource> getFileXlsx() {
    String filename = "Нормализованные адреса.xlsx";
    InputStreamResource file = new InputStreamResource(excelService.downloadFileXlsx());

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
      .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
      .body(file);
  }

  @GetMapping("/download/csv")
  public ResponseEntity<Resource> getFileCsv() {
    String filename = "Нормализованные адреса.csv";
    InputStreamResource file = new InputStreamResource(excelService.downloadFileCsv());

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
      .contentType(MediaType.parseMediaType("application/csv"))
      .body(file);
  }

  @GetMapping("/get/clean")
  public ResponseEntity<List<CleanAddress>> getAllCleanAddresses() {
    try {
      List<CleanAddress> cleanAddresses = excelService.getAllCleanAddresses();

      if (cleanAddresses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/toclean")
  public ResponseEntity<List<ToCleanAddress>> getAllToCleanAddresses() {
    try {
      List<ToCleanAddress> toCleanAddresses = excelService.getAllToCleanAddresses();

      if (toCleanAddresses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(toCleanAddresses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

