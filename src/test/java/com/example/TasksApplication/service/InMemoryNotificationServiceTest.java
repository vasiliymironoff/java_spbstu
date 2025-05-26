package com.example.TasksApplication.service;

import com.example.TasksApplication.model.Notification;
import com.example.TasksApplication.repository.InMemoryNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class InMemoryNotificationServiceTest {

    @InjectMocks
    private InMemoryNotificationService notificationService;

    @Mock
    private InMemoryNotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByIdFound() {
        // Arrange
        long id = 1L;
        Notification notification = new Notification(id, 123L, 456L, "Notification 1", LocalDateTime.now());
        when(notificationRepository.getNotificationById(id)).thenReturn(notification);

        // Act
        Notification foundNotification = notificationService.getById(id);

        // Assert
        assertThat(foundNotification).isNotNull();
        assertThat(foundNotification.getIdNotification()).isEqualTo(id);
        assertThat(foundNotification.getTextNotification()).isEqualTo("Notification 1");
        verify(notificationRepository, times(1)).getNotificationById(id);
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        long id = 1L;
        when(notificationRepository.getNotificationById(id)).thenReturn(null);

        // Act
        Notification foundNotification = notificationService.getById(id);

        // Assert
        assertThat(foundNotification).isNull();
        verify(notificationRepository, times(1)).getNotificationById(id);
    }

    @Test
    public void testGetAllNotification() {
        // Arrange
        Notification notification1 = new Notification(1L, 123L, 456L, "Notification 1", LocalDateTime.now());
        Notification notification2 = new Notification(2L, 124L, 457L, "Notification 2", LocalDateTime.now());
        List<Notification> notifications = Arrays.asList(notification1, notification2);
        when(notificationRepository.getAllNotification()).thenReturn(notifications);

        // Act
        List<Notification> foundNotifications = notificationService.getAllNotification();

        // Assert
        assertThat(foundNotifications).hasSize(2);
        assertThat(foundNotifications).containsExactlyInAnyOrder(notification1, notification2);
        verify(notificationRepository, times(1)).getAllNotification();
    }
}
