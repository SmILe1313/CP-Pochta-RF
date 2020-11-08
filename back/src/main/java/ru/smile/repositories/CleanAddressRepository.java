package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.CleanAddress;

@Repository
public interface CleanAddressRepository extends JpaRepository<CleanAddress, Long> {
}
