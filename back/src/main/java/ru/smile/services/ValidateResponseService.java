package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smile.entities.AddrRequest;
import ru.smile.entities.ValidateRequest;
import ru.smile.entities.ValidateResponse;
import ru.smile.entities.ValidateResponseCounts;
import ru.smile.entities.ValidateSimpleRequest;
import ru.smile.repositories.AddrResponseRepository;
import ru.smile.repositories.ElementRepository;
import ru.smile.repositories.FioRepository;
import ru.smile.repositories.IndexRepository;
import ru.smile.repositories.ValidateResponseRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ValidateResponseService {

  @Autowired ValidateResponseRepository repo;

  @Autowired IndexRepository indexRepository;

  @Autowired FioRepository fioRepository;

  @Autowired AddrResponseRepository addrResponseRepository;

  @Autowired ElementRepository elementRepository;

  private static UUID responseUuid;

  public static UUID getResponseUuid() {
    return responseUuid;
  }

  public static void setResponseUuid(UUID responseUuid) {
    ValidateResponseService.responseUuid = responseUuid;
  }

  public ValidateResponse create(ValidateResponse entity) {
    return repo.save(entity);
  }

  public boolean update(ValidateResponse entity, Long id) {
    if (repo.existsById(id)) {
      entity.setId(id);
      if (entity.getAddr() != null) {
        if (entity.getAddr().getElement() != null) {
          elementRepository.saveAll(entity.getAddr().getElement());
        }
        addrResponseRepository.save(entity.getAddr());
      }
      if (entity.getFio() != null) {
        fioRepository.save(entity.getFio());
      }
      if(entity.getIndex() != null) {
        indexRepository.save(entity.getIndex());
      }
      repo.save(entity);
      return true;
    }
    return false;
  }


  public void delete(Long id) {
    repo.deleteById(id);
  }

  public List<ValidateResponse> getAll() {
    return (List<ValidateResponse>) repo.findByResponseUuid(getResponseUuid());
  }

  // Доставочные
  public List<ValidateResponse> getGood() {
    return (List<ValidateResponse>) repo.findByAddrDeliveryAndResponseUuid(0, getResponseUuid());
  }
  public Long getCountGood() {
    return repo.countByAddrDeliveryAndResponseUuid(0, getResponseUuid());
  }

  // Требуют уточнения
  public List<ValidateResponse> getMiddle() {
    return (List<ValidateResponse>) repo.findByAddrDeliveryAndResponseUuid(1, getResponseUuid());
  }
  public Long getCountMiddle() {
    return repo.countByAddrDeliveryAndResponseUuid(1, getResponseUuid());
  }

  // Плохие
  public List<ValidateResponse> getBad() {
    return (List<ValidateResponse>) repo.findByAddrDeliveryAndResponseUuid(2, getResponseUuid());
  }
  public Long getCountBad() {
    return repo.countByAddrDeliveryAndResponseUuid(2, getResponseUuid());
  }

  // Количество
  public ValidateResponseCounts getCounts() {
    return new ValidateResponseCounts(
      getCountGood(),
      getCountMiddle(),
      getCountBad()
    );
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

  public ValidateRequest toStandartRequest (ValidateSimpleRequest validateSimpleRequest) {
    List<AddrRequest> addrRequestList = new ArrayList<>(Collections.singletonList(new AddrRequest(validateSimpleRequest.getAddr())));
    return new ValidateRequest(validateSimpleRequest.getId(), PochtaService.reqId, PochtaService.version, addrRequestList);
  }

}
