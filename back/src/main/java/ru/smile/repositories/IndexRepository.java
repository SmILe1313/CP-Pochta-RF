package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.Fio;
import ru.smile.entities.Index;

@Repository
public interface IndexRepository extends JpaRepository<Index, Long> {
}
