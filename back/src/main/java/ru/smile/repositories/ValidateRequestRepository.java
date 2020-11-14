package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.ValidateRequest;

@Repository
public interface ValidateRequestRepository extends JpaRepository<ValidateRequest, Long> {
}
