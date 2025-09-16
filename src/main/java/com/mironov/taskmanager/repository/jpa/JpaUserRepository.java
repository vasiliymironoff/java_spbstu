package com.mironov.taskmanager.repository.jpa;

import com.mironov.taskmanager.model.User;
import com.mironov.taskmanager.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("h2")
public interface JpaUserRepository extends JpaRepository<User, Long> {
    User save(User user);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}