package com.example.TasksApplication.service;

import com.example.TasksApplication.model.User;
import com.example.TasksApplication.repository.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InMemoryUserService {
    @Autowired
    private InMemoryUserRepository userRepository;

    public User getById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}