package com.mironov.taskmanager.repository;

import com.mironov.taskmanager.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
} 