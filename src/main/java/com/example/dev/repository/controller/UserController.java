package com.example.dev.repository.controller;

import com.example.dev.model.User;
import com.example.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(Map.of("status", "UP"));
  }

  @GetMapping("/api/users")
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @PostMapping("/api/users")
  public User createUser(@RequestBody User user) {
    System.out.println("Saving user: " + user);
    return userRepository.save(user);
  }
}
