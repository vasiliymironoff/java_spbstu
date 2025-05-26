package com.example.TasksApplication.repository;

import com.example.TasksApplication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryUserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    public void testFindByIdFound() {
        // Arrange
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
        userRepository.save(user);

        // Act
        User foundUser = userRepository.findById(1L);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(1L);
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
        assertThat(foundUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testFindByIdNotFound() {
        // Act
        User foundUser = userRepository.findById(1L);

        // Assert
        assertThat(foundUser).isNull(); // Пользователь не найден
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .build();

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(1L); // Проверяем, что ID установлен
        assertThat(savedUser.getFirstName()).isEqualTo("Jane");
        assertThat(savedUser.getLastName()).isEqualTo("Doe");
        assertThat(savedUser.getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    public void testSaveMultipleUsers() {
        // Arrange
        User user1 = User.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .build();
        User user2 = User.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .email("bob.johnson@example.com")
                .build();

        // Act
        userRepository.save(user1);
        userRepository.save(user2);

        // Assert
        assertThat(user1.getUserId()).isEqualTo(1L);
        assertThat(user2.getUserId()).isEqualTo(2L);
    }
}
