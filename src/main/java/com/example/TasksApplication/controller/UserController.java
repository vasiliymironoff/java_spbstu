package com.example.TasksApplication.controller;

import com.example.TasksApplication.model.User;
import com.example.TasksApplication.service.InMemoryUserService;
import com.example.TasksApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable("userId") long id) {
        User user = userService.getUserById(id);
        List<User> users = new ArrayList<>();
        users.add(user);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }
}
