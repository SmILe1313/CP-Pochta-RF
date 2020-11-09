package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.repositories.CleanAddressRepository;
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

  @Autowired CleanAddressRepository cleanAddressRepository;

  @Autowired OtpravkaService otpravkaService;

  public List<CleanAddress> saveAndNormalizeXlsx(MultipartFile file) {
    try {
      List<ToCleanAddress> toCleanAddresses = ExcelHelper.excelToCleanAddress(file.getInputStream());
      toCleanAddressRepository.saveAll(toCleanAddresses);
      List<CleanAddress> cleanAddresses = otpravkaService.normalizeAddressApi(toCleanAddresses);
      cleanAddressRepository.saveAll(cleanAddresses);
      return cleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения xlsx-файла: " + e.getMessage());
    }
  }

  public List<CleanAddress> saveAndNormalizeCsv(MultipartFile file) {
    try {
      List<ToCleanAddress> toCleanAddresses = CsvHelper.csvToCleanAddress(file.getInputStream());
      toCleanAddressRepository.saveAll(toCleanAddresses);
      List<CleanAddress> cleanAddresses = otpravkaService.normalizeAddressApi(toCleanAddresses);
      cleanAddressRepository.saveAll(cleanAddresses);
      return cleanAddresses;
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения csv-файла: " + e.getMessage());
    }
  }

  public ByteArrayInputStream downloadFileXlsx() {
    List<CleanAddress> cleanAddresses = cleanAddressRepository.findAll();

    ByteArrayInputStream in = ExcelHelper.cleanAddressToExcel(cleanAddresses);
    return in;
  }

  public ByteArrayInputStream downloadFileCsv() {
    List<CleanAddress> cleanAddresses = cleanAddressRepository.findAll();

    ByteArrayInputStream in = CsvHelper.cleanAddressToCSV(cleanAddresses);
    return in;
  }

  public List<ToCleanAddress> getAllToCleanAddresses() {
    return toCleanAddressRepository.findAll();
  }

  public List<CleanAddress> getAllCleanAddresses() {
    return cleanAddressRepository.findAll();
  }
}

