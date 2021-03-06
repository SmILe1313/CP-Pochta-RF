package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.AddrRequest;

@Repository
public interface AddrRequestRepository extends JpaRepository<AddrRequest, Long> {
}
