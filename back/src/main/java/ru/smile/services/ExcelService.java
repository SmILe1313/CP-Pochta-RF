package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.repositories.CleanAddressRepository;
import ru.smile.repositories.ToCleanAddressRepository;
import ru.smile.utils.ExcelHelper;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ExcelService {

  @Autowired ToCleanAddressRepository toCleanAddressRepository;

  @Autowired CleanAddressRepository cleanAddressRepository;

  @Autowired OtpravkaService otpravkaService;

  public List<CleanAddress> saveAndNormalize(MultipartFile file) {
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

  public List<ToCleanAddress> getAllToCleanAddresses() {
    return toCleanAddressRepository.findAll();
  }

  public List<CleanAddress> getAllCleanAddresses() {
    return cleanAddressRepository.findAll();
  }
}

