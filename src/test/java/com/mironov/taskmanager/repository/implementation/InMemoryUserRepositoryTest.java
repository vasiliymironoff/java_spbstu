package com.mironov.taskmanager.repository.implementation;

import com.mironov.taskmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository repository;
    private User testUser;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();

        testUser = User.builder()
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .dateCreation(LocalDateTime.now())
                .build();
    }

    @Test
    void testSaveUser() {
        // Сохраняем пользователя
        User savedUser = repository.save(testUser);

        // Проверяем, что пользователь сохранен
        assertNotNull(savedUser.getUserId());

        // Проверяем поля
        assertEquals(testUser.getUsername(), savedUser.getUsername());
        assertEquals(testUser.getPassword(), savedUser.getPassword());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
    }

    @Test
    void testFindByUsername() {
        // Сохраняем пользователя
        repository.save(testUser);

        // Ищем по username
        Optional<User> foundUser = repository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals(testUser, foundUser.get());
    }

    @Test
    void testFindByUsername_NotFound() {
        // Ищем несуществующего пользователя
        Optional<User> result = repository.findByUsername("nonexistent");
        assertFalse(result.isPresent());
    }

    @Test
    void testExistsByUsername() {
        // Сохраняем пользователя
        repository.save(testUser);

        // Проверяем существование
        assertTrue(repository.existsByUsername("testuser"));
        assertFalse(repository.existsByUsername("nonexistent"));
    }

    @Test
    void testExistsByEmail() {
        // Сохраняем пользователя
        repository.save(testUser);

        // Проверяем существование по email
        assertTrue(repository.existsByEmail("test@example.com"));
        assertFalse(repository.existsByEmail("nonexistent@example.com"));
    }

    @Test
    void testSaveUser_WithExistingId() {
        // Создаем пользователя с заданным ID
        User userWithId = User.builder()
                .userId(100L)
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .build();

        User savedUser = repository.save(userWithId);
        assertEquals(100L, savedUser.getUserId());
    }

    @Test
    void testSaveMultipleUsers() {
        // Сохраняем несколько пользователей
        User user1 = repository.save(testUser);
        User user2 = repository.save(User.builder()
                .username("user2")
                .password("pass2")
                .email("user2@example.com")
                .build());
        
        assertEquals(1L, user1.getUserId());
        assertEquals(2L, user2.getUserId());
    }
}
