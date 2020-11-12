package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.repositories.CleanAddressRepository;
import ru.smile.repositories.ToCleanAddressRepository;

import java.util.List;

import static ru.smile.entities.CleanAddress.goodQuality;
import static ru.smile.entities.CleanAddress.goodValidation;

@Service
@Transactional
public class ToCleanAddresService {

  @Autowired
  ToCleanAddressRepository repo;

  public ToCleanAddress create(ToCleanAddress entity) {
    return repo.save(entity);
  }

  public boolean update(ToCleanAddress entity, Long id) {
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

  public List<ToCleanAddress> getAll() {
    return (List<ToCleanAddress>) repo.findAll();
  }

  public ToCleanAddress get(Long id) {
    return repo.findById(id).get();
  }

}
