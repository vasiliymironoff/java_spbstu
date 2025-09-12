package com.mironov.taskmanager.controller;

import com.mironov.taskmanager.exception.DuplicateResourceException;
import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.model.User;
import com.mironov.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createTestUser() {
        return User.builder()
                .userId(1L)
                .username("testuser")
                .password("securepassword123")
                .email("test@example.com")
                .build();
    }

    // Тест для метода login
    @Test
    void testLogin_Success() {
        // Подготовка
        User user = createTestUser();
        when(userService.login(anyString(), anyString()))
                .thenReturn(user);

        // Выполнение
        ResponseEntity<User> response = userController.login(user.getUsername(), user.getPassword());

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).login(user.getUsername(), user.getPassword());
    }

    @Test
    void testLogin_UserNotFound() {
        // Подготовка
        when(userService.login(anyString(), anyString()))
                .thenReturn(null);

        // Выполнение
        ResponseEntity<User> response = userController.login("nonexistent", "password");

        // Проверка
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // Тест для метода registerUser
    @Test
    void testRegisterUser_Success() {
        // Подготовка
        User user = createTestUser();
        when(userService.registerUser(any(User.class)))
                .thenReturn(user);

        // Выполнение
        ResponseEntity<User> response = userController.registerUser(user);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).registerUser(user);
    }


    @Test
    void testRegisterUser_DuplicateUser() {
        // Подготовка
        User user = createTestUser();
        when(userService.registerUser(any(User.class)))
                .thenThrow(new DuplicateResourceException("User already exists"));

        // Выполнение
        ResponseEntity<String> response = userController.handleDuplicateResourceException(
                new DuplicateResourceException("User already exists")
        );

        // Проверка
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }
}