package ru.smile.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smile.entities.TestTodos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestService {

  private final static String apiLink = "http://jsonplaceholder.typicode.com/posts?_limit=10";

  public static List<TestTodos> getObject() {
    final RestTemplate restTemplate = new RestTemplate();

    final TestTodos[] todos = restTemplate.getForObject(apiLink, TestTodos[].class);
    List<TestTodos> listTestTodos = new ArrayList<>();
    if (todos != null) {
      listTestTodos = Arrays.asList(todos);
    }

    return listTestTodos;
  }

  public static TestTodos postObject() {
    final RestTemplate restTemplate = new RestTemplate();

    final TestTodos todosToInsert = new TestTodos();
    todosToInsert.setUserId(1);
    todosToInsert.setTitle("Test todos");
    todosToInsert.setCompleted(true);

    final TestTodos insertedPost = restTemplate.postForObject(apiLink, todosToInsert, TestTodos.class);
    return insertedPost;
  }
}
