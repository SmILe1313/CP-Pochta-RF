package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.AddrRequest;
import ru.smile.entities.AddrResponse;

@Repository
public interface AddrResponseRepository extends JpaRepository<AddrResponse, Long> {
}
