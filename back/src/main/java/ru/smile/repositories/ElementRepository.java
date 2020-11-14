package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.Element;
import ru.smile.entities.Fio;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
}
