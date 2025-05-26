package com.example.TasksApplication.service;

import com.example.TasksApplication.model.User;
import com.example.TasksApplication.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InMemoryUserServiceTest {

    @InjectMocks
    private InMemoryUserService userService;

    @Mock
    private InMemoryUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        // Arrange
        long userId = 1L;
        User user = new User(userId, "John", "Doe", "john@example.com");
        when(userRepository.findById(userId)).thenReturn(user);

        // Act
        User result = userService.getById(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User user = new User(1L, "Jane", "Doe", "jane@example.com");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }
}
