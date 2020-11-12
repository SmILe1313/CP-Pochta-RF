package ru.smile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.ToCleanAddress;
import ru.smile.entities.User;
import ru.smile.services.CleanAddresService;
import ru.smile.services.ToCleanAddresService;
import ru.smile.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
public class CrudController {

  @Autowired private UserService userService;

  @Autowired private CleanAddresService cleanAddresService;

  @Autowired private ToCleanAddresService toCleanAddresService;

  @GetMapping("/user/{id}") // get/
  public ResponseEntity<User> getUser(@PathVariable Long id){
    User user = userService.get(id);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping("/user") ///create
  public ResponseEntity<User> createUser(@RequestBody User user){
//    User user = new User("adm", "adm");
    userService.create(user);
    return new ResponseEntity<User>(user , HttpStatus.CREATED);
  }

  @PutMapping("/user/{id}") //update/
  public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user){
//    User userToUpd = userService.get(id);
//    userToUpd.setLogin(user.getLogin());
//    userToUpd.setPassword(user.getPassword());
    userService.update(user, id);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @DeleteMapping("/user/{id}") //delete
  public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id) {
    userService.delete(id);
    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
  }

  @PutMapping("/clean/{id}") //update/
  public ResponseEntity<CleanAddress> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody CleanAddress cleanAddress){
    cleanAddresService.update(cleanAddress, id);
    return new ResponseEntity<CleanAddress>(cleanAddress, HttpStatus.OK);
  }

  @DeleteMapping("/clean/{id}") //delete
  public ResponseEntity<String> deleteClean(@PathVariable(value = "id") Long id) {
    cleanAddresService.delete(id);
    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
  }

  @GetMapping("/clean/all")
  public ResponseEntity<List<CleanAddress>> getAllCleanAddresses() {
    try {
      List<CleanAddress> cleanAddresses = cleanAddresService.getAll();

      if (cleanAddresses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/clean/witherrors")
  public ResponseEntity<List<CleanAddress>> getNotValidCleanAddresses() {
    try {
      List<CleanAddress> cleanAddresses = cleanAddresService.getWithErrors();

      if (cleanAddresses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/toclean/all")
  public ResponseEntity<List<ToCleanAddress>> getAllToCleanAddresses() {
    try {
      List<ToCleanAddress> toCleanAddresses = toCleanAddresService.getAll();

      if (toCleanAddresses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(toCleanAddresses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
