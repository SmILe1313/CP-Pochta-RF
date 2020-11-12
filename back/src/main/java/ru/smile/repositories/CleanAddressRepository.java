package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.CleanAddress;

import java.util.List;
import java.util.Set;


@Repository
public interface CleanAddressRepository extends JpaRepository<CleanAddress, Long> {
  List<CleanAddress> findByQualityCodeNotInOrValidationCodeNotIn(Set<String> qualityCode, Set<String> validationCode);
}
