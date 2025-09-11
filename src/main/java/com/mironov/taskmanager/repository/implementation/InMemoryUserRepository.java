package com.mironov.taskmanager.repository.implementation;

import org.springframework.stereotype.Repository;
import com.mironov.taskmanager.model.User;
import com.mironov.taskmanager.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong(1);

    @Override
    public User save(User user) {
        if (user.getUserId() == null) {
            user.setUserId(userIdCounter.getAndIncrement());
        }
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }
} 