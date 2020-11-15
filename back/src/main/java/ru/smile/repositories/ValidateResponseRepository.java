package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.ValidateResponse;

import java.util.List;
import java.util.UUID;


@Repository
public interface ValidateResponseRepository extends JpaRepository<ValidateResponse, Long> {

  List<ValidateResponse> findByAddrDeliveryAndResponseUuid(Integer i, UUID uuid);
  List<ValidateResponse> findByResponseUuid(UUID uuid);
  Long countByAddrDeliveryAndResponseUuid(Integer i,UUID uuid);
}
