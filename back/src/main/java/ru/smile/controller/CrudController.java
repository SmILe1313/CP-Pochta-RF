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
import ru.smile.entities.ValidateResponse;
import ru.smile.services.PochtaService;
import ru.smile.services.UserService;
import ru.smile.services.ValidateResponseService;

import java.util.List;

@CrossOrigin
@RestController
public class CrudController {

  @Autowired private UserService userService;

//  @Autowired private CleanAddresService cleanAddresService;
//
//  @Autowired private ToCleanAddresService toCleanAddresService;

  @Autowired private ValidateResponseService validateResponseService;

  @Autowired PochtaService pochtaService;

  @GetMapping("/user/{id}") // get/
  public ResponseEntity<User> getUser(@PathVariable Long id){
    User user = userService.get(id);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping("/user") ///create
  public ResponseEntity<User> createUser(@RequestBody User user){
    userService.create(user);
    return new ResponseEntity<User>(user , HttpStatus.CREATED);
  }

  @PutMapping("/user/{id}") //update/
  public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user){
    userService.update(user, id);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @DeleteMapping("/user/{id}") //delete
  public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id) {
    userService.delete(id);
    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
  }

//  @PutMapping("/clean/{id}") // Для повторной нормализации построчно
//  public ResponseEntity<CleanAddress> updateClean(@PathVariable(value = "id") Long id, @Valid @RequestBody CleanAddress cleanAddress){
//    ToCleanAddress toCleanAddress = pochtaService.toOneString(cleanAddress);
//    CleanAddress newCleanAddress = pochtaService.normalizeAddressApi(toCleanAddress);
//    cleanAddresService.update(newCleanAddress, id);
//    return new ResponseEntity<CleanAddress>(newCleanAddress, HttpStatus.OK);
//  }

  @DeleteMapping("/clean/{id}") //delete
  public ResponseEntity<String> deleteClean(@PathVariable(value = "id") Long id) {
    validateResponseService.delete(id);
    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
  }

  // Вернуть весь список CleanAddress
  @GetMapping("/clean/all")
  public ResponseEntity<List<ValidateResponse>> getAllCleanAddresses() {
    try {
      List<ValidateResponse> validateResponseList = validateResponseService.getAll();

      if (validateResponseList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(validateResponseList, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  // Вернуть список CleanAddress с ошибками
//  @GetMapping("/clean/witherrors")
//  public ResponseEntity<List<CleanAddress>> getNotValidCleanAddresses() {
//    try {
//      List<CleanAddress> cleanAddresses = validateResponseService.getWithErrors();
//
//      if (cleanAddresses.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      return new ResponseEntity<>(cleanAddresses, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//
//  @GetMapping("/toclean/all")
//  public ResponseEntity<List<ToCleanAddress>> getAllToCleanAddresses() {
//    try {
//      List<ToCleanAddress> toCleanAddresses = toCleanAddresService.getAll();
//
//      if (toCleanAddresses.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      return new ResponseEntity<>(toCleanAddresses, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

}
