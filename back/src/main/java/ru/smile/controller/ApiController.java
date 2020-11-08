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
import ru.smile.entities.TestTodos;
import ru.smile.entities.User;
import ru.smile.services.TestService;
import ru.smile.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
public class ApiController {

//  @Autowired private UserService userService;
//
//  @GetMapping("/user/{id}") // get/
//  public ResponseEntity<User> getUser(@PathVariable Long id){
//    User user = userService.get(id);
//    return new ResponseEntity<User>(user, HttpStatus.OK);
//  }
//
//  @PostMapping("/user") ///create
//  public ResponseEntity<User> createUser(@RequestBody User user){
////    User user = new User("adm", "adm");
//    userService.create(user);
//    return new ResponseEntity<User>(user , HttpStatus.CREATED);
//  }
//
//  @PutMapping("/user/{id}") //update/
//  public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user){
////    User userToUpd = userService.get(id);
////    userToUpd.setLogin(user.getLogin());
////    userToUpd.setPassword(user.getPassword());
//    userService.update(user, id);
//    return new ResponseEntity<User>(user, HttpStatus.OK);
//  }
//
//  @DeleteMapping("/user/{id}") //delete
//  public ResponseEntity<String> deleteBook(@PathVariable(value = "id") Long id) {
//    userService.delete(id);
//    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
//  }

  @GetMapping("/testGetService")
  public ResponseEntity<List<TestTodos>> testGetService(){
    List<TestTodos> testTodosList = TestService.getObject();
    return new ResponseEntity<List<TestTodos>>(testTodosList, HttpStatus.OK);
  }

  @GetMapping("/testPostService")
  public ResponseEntity<TestTodos> testPosrService(){
    TestTodos testTodos = TestService.postObject();
    return new ResponseEntity<TestTodos>(testTodos, HttpStatus.OK);
  }

}
