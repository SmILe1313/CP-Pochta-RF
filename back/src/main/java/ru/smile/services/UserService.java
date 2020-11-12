package ru.smile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smile.entities.User;
import ru.smile.repositories.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

  @Autowired UserRepository repo;

  private User user;

  public User create(User entity) {
    return repo.save(entity);
  }

  public boolean update(User entity, Long id) {
    if (repo.existsById(id)) {
      entity.setId(id);
      repo.save(entity);
      return true;
    }
    return false;
  }


  public void delete(Long id) {
    repo.deleteById(id);
  }

  public List<User> getAll() {
    return (List<User>) repo.findAll();
  }

  public User get(Long id) {
    return repo.findById(id).get();
  }

  public User getUser() {
    return user;
  }

  public Long getUserId() {
    return user != null ? user.getId() : 0L;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUserName() {
    return user.getFirstName() + ' ' + user.getLastName();
  }

  public User getUserByLogAndPass(String login, String password) {
    return repo.findByLoginAndPassword(login, password);
  }
}
