package ru.smile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ru.smile.services.ExcelService;
import ru.smile.utils.ExcelHelper;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@Controller
@RequestMapping("/excel")
public class ExcelCsvController {

  @Autowired ExcelService excelService;

  @PostMapping("/clean/address")
  public ResponseEntity<List<CleanAddress>> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        List<CleanAddress> cleanAddresses = excelService.saveAndNormalize(file);

        message = "Файл успешно загружен: " + file.getOriginalFilename();
        return new ResponseEntity<List<CleanAddress>>(cleanAddresses, HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Невозможно загрузить xlsx-файл" + file.getOriginalFilename() + "!";
        return new ResponseEntity<List<CleanAddress>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    message = "Пожалуйста, выберите xlsx или csv файл!";
    return new ResponseEntity<List<CleanAddress>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
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

