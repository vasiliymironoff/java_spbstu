package com.example.TasksApplication.controller;

import com.example.TasksApplication.model.User;
import com.example.TasksApplication.service.InMemoryUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private InMemoryUserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsersFound() {
        // Arrange
        long id = 1;
        User user = User.builder()
                .userId(id)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        when(userService.getById(id)).thenReturn(user);

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(user), response.getBody());
        verify(userService, times(1)).getById(id);
    }

    @Test
    public void testGetAllUsersNotFound() {
        // Arrange
        long id = 1;
        when(userService.getById(id)).thenReturn(null);

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(null), response.getBody());
        verify(userService, times(1)).getById(id);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User user = User.builder()
                .userId(1)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .build();
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).createUser(user);
    }
}
