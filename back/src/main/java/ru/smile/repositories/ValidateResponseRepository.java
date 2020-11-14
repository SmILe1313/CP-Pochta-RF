package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ValidateResponse;

import java.util.List;
import java.util.Set;


@Repository
public interface ValidateResponseRepository extends JpaRepository<ValidateResponse, Long> {
  // Ошибки
//  List<ValidateResponse> findByQualityCodeNotInOrValidationCodeNotIn(Set<String> qualityCode, Set<String> validationCode);
//
//  // Без ошибок
//  List<ValidateResponse> findByQualityCodeInAndValidationCodeIn(Set<String> qualityCode, Set<String> validationCode);
}
