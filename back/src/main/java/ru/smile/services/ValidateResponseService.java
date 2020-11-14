package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smile.entities.ValidateResponse;
import ru.smile.repositories.AddrResponseRepository;
import ru.smile.repositories.ElementRepository;
import ru.smile.repositories.FioRepository;
import ru.smile.repositories.IndexRepository;
import ru.smile.repositories.ValidateResponseRepository;

import java.util.List;

@Service
@Transactional
public class ValidateResponseService {

  @Autowired ValidateResponseRepository repo;

  @Autowired IndexRepository indexRepository;

  @Autowired FioRepository fioRepository;

  @Autowired AddrResponseRepository addrResponseRepository;

  @Autowired ElementRepository elementRepository;

  public ValidateResponse create(ValidateResponse entity) {
    return repo.save(entity);
  }

  public boolean update(ValidateResponse entity, Long id) {
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

  public List<ValidateResponse> getAll() {
    return (List<ValidateResponse>) repo.findAll();
  }

  // Нормализицая с ошибками
  public List<ValidateResponse> getWithErrors() {
    return (List<ValidateResponse>) repo.findAll();
//    return (List<ValidateResponse>) repo.findByQualityCodeNotInOrValidationCodeNotIn(goodQuality, goodValidation);
  }

  // Нормализицая без ошибок
  public List<ValidateResponse> getWithoutErrors() {
    return (List<ValidateResponse>) repo.findAll();
//    return (List<ValidateResponse>) repo.findByQualityCodeInAndValidationCodeIn(goodQuality, goodValidation);
  }

  public ValidateResponse get(Long id) {
    return repo.findById(id).get();
  }

  public List<ValidateResponse> saveList(List<ValidateResponse> validateResponseList) {
    validateResponseList.forEach(validateResponse -> {
      if (validateResponse.getAddr() != null) {
        if (validateResponse.getAddr().getElement() != null) {
          elementRepository.saveAll(validateResponse.getAddr().getElement());
        }
        addrResponseRepository.save(validateResponse.getAddr());
      }
      if (validateResponse.getFio() != null) {
        fioRepository.save(validateResponse.getFio());
      }
      if(validateResponse.getIndex() != null) {
        indexRepository.save(validateResponse.getIndex());
      }
    });
    return repo.saveAll(validateResponseList);
  }

}
