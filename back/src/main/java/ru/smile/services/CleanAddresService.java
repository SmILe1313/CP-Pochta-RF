package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smile.entities.CleanAddress;
import ru.smile.repositories.CleanAddressRepository;

import java.util.List;

import static ru.smile.entities.CleanAddress.goodQuality;
import static ru.smile.entities.CleanAddress.goodValidation;

@Service
@Transactional
public class CleanAddresService {

  @Autowired
  CleanAddressRepository repo;

  public CleanAddress create(CleanAddress entity) {
    return repo.save(entity);
  }

  public boolean update(CleanAddress entity, Long id) {
    if (repo.existsById(id)) {
      entity.setId(id);
      repo.save(entity);
      return true;
    }
    return false;
  }


  public void delete(Long id) {
    repo.deleteById(id);
  }

  public List<CleanAddress> getAll() {
    return (List<CleanAddress>) repo.findAll();
  }

  // Нормализицая с ошибками
  public List<CleanAddress> getNotCleaned() {
    return (List<CleanAddress>) repo.findByQualityCodeNotInOrValidationCodeNotIn(goodQuality, goodValidation);
  }

  public CleanAddress get(Long id) {
    return repo.findById(id).get();
  }

}
