package com.example.TasksApplication.dao;

import com.example.TasksApplication.model.User;


public interface InMemoryUserDAO
{
    User findById(Long id);

    User save(User user);
}

