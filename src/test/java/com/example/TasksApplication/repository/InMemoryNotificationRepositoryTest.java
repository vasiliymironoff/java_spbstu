package com.example.TasksApplication.repository;

import com.example.TasksApplication.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryNotificationRepositoryTest {

    private InMemoryNotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        notificationRepository = new InMemoryNotificationRepository();
    }

    @Test
    public void testGetAllNotification() {
        // Arrange
        Notification notification1 = new Notification(1L, 123L, 456L, "Notification 1", LocalDateTime.now());
        Notification notification2 = new Notification(2L, 124L, 457L, "Notification 2", LocalDateTime.now());
        notificationRepository.getAllNotification().add(notification1);
        notificationRepository.getAllNotification().add(notification2);

        // Act
        List<Notification> notifications = notificationRepository.getAllNotification();

        // Assert
        assertThat(notifications).hasSize(2);
        assertThat(notifications).containsExactlyInAnyOrder(notification1, notification2);
    }

    @Test
    public void testGetNotificationByIdFound() {
        // Arrange
        Notification notification = new Notification(1L, 123L, 456L, "Notification 1", LocalDateTime.now());
        notificationRepository.getAllNotification().add(notification);

        // Act
        Notification foundNotification = notificationRepository.getNotificationById(1L);

        // Assert
        assertThat(foundNotification).isNotNull();
        assertThat(foundNotification).isEqualTo(notification);
    }

    @Test
    public void testGetNotificationByIdNotFound() {
        // Act
        Notification foundNotification = notificationRepository.getNotificationById(1L);

        // Assert
        assertThat(foundNotification).isNull();
    }
}
