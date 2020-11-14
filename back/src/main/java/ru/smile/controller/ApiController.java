package ru.smile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smile.entities.CleanAddress;
import ru.smile.entities.TestTodos;
import ru.smile.entities.ToCleanAddress;
import ru.smile.entities.User;
import ru.smile.services.PochtaService;
import ru.smile.services.TestService;
import ru.smile.services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {

  @Autowired private UserService userService;

  @Autowired private PochtaService otpravkaService;

  @PostMapping("/normalizeByString")
  public ResponseEntity<List<CleanAddress>> normalaizeByString(@RequestBody List<String> originalAddress){

    List<ToCleanAddress> toCleanAddresses = originalAddress.stream()
      .map(oa -> new ToCleanAddress(0L, oa))
      .collect(Collectors.toList());

//    List<CleanAddress> cleanAddresses = otpravkaService.normalizeAddressApi(toCleanAddresses);
    List<CleanAddress> cleanAddresses = Collections.singletonList(new CleanAddress(0L,"sdf","asdas","eee","yyy","тратата","уцуц","бумбум","трололо","гыгы","s","s","d","уфуф","фвфы","ва","фв","ффва","ф"));

    return new ResponseEntity<List<CleanAddress>>(cleanAddresses, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<User> loginUser(@RequestBody Map<String, String> body){
    String login = body.get("login");
    String password = body.get("password");

    User user = userService.getUserByLogAndPass(login, password);
    userService.setUser(user);
    return new ResponseEntity<User>(user , HttpStatus.OK);
  }

  @GetMapping("/testGetService")
  public ResponseEntity<List<TestTodos>> testGetService(){
    List<TestTodos> testTodosList = TestService.getObject();
    return new ResponseEntity<>(testTodosList, HttpStatus.OK);
  }

  @GetMapping("/testPostService")
  public ResponseEntity<TestTodos> testPosrService(){
    TestTodos testTodos = TestService.postObject();
    return new ResponseEntity<>(testTodos, HttpStatus.OK);
  }
}
