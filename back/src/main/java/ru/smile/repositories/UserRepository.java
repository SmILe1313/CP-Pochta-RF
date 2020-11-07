package ru.smile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smile.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
