package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.ToCleanAddress;

@Repository
public interface ToCleanAddressRepository extends JpaRepository<ToCleanAddress, Long> {
}
