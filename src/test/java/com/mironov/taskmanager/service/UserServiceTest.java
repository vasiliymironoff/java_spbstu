package com.mironov.taskmanager.service;

import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.model.User;
import com.mironov.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации моков", e);
        }
    }

    private User createTestUser() {
        return User.builder()
                .username("testuser")
                .password("securepassword123")
                .email("test@example.com")
                .build();
    }

    @Test
    void testLogin_NullPassword() {
        // Подготовка
        User user = createTestUser();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));

        // Выполнение и проверка
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.login(user.getUsername(), null);
        });
    }

    @Test
    void testLogin_BothNull() {
        // Выполнение и проверка
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.login(null, null);
        });
    }

    @Test
    void testLogin_WhitespacePassword() {
        // Подготовка
        User user = createTestUser();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));

        // Выполнение и проверка
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.login(user.getUsername(), " ");
        });
    }

    @Test
    void testRegisterUser_ValidAllFields() {
        // Подготовка
        User user = createTestUser();
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Выполнение
        User registeredUser = userService.registerUser(user);

        // Проверка
        assertEquals(user, registeredUser);
        verify(userRepository).save(user);
        verify(userRepository).existsByUsername(user.getUsername());
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void testRegisterUser_ValidSpecialCharacters() {
        // Подготовка
        User user = User.builder()
                .username("test_user123")
                .password("Secure!@#123")
                .email("test.user+123@example.co.uk")
                .build();

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Выполнение
        User registeredUser = userService.registerUser(user);

        // Проверка
        assertEquals(user, registeredUser);
        verify(userRepository).save(user);
    }
}