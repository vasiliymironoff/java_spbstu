package com.example.TasksApplication.repository;

import com.example.TasksApplication.dao.InMemoryUserDAO;
import com.example.TasksApplication.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryUserRepository implements InMemoryUserDAO {
    private final List<User> users = new ArrayList<>();
    private Long currentId = 1L; // Simple ID generator

    @Override
    public User findById(Long id) {
        return users.stream()
                .filter(user -> user.getUserId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(User user) {
        user.setUserId(currentId++);
        users.add(user);
        return user;
    }
}


