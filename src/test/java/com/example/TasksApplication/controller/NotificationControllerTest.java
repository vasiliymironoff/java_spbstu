package com.example.TasksApplication.controller;

import com.example.TasksApplication.dao.InMemoryNotificationDAO;
import com.example.TasksApplication.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private InMemoryNotificationDAO notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllNotification() {
        // Arrange
        Notification notification1 = Notification.builder()
                .idNotification(1L)
                .userIdNotification(101L)
                .TaskIdNotification(201L)
                .textNotification("Notification 1")
                .dateNotification(LocalDateTime.now())
                .build();

        Notification notification2 = Notification.builder()
                .idNotification(2L)
                .userIdNotification(102L)
                .TaskIdNotification(202L)
                .textNotification("Notification 2")
                .dateNotification(LocalDateTime.now())
                .build();

        List<Notification> notifications = Arrays.asList(notification1, notification2);
        when(notificationService.getAllNotification()).thenReturn(notifications);

        // Act
        ResponseEntity<List<Notification>> response = notificationController.getAllNotification();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
        verify(notificationService, times(1)).getAllNotification();
    }

    @Test
    public void testGetNotificationById_Found() {
        // Arrange
        long notificationId = 1L;
        Notification notification = Notification.builder()
                .idNotification(notificationId)
                .userIdNotification(101L)
                .TaskIdNotification(201L)
                .textNotification("Notification 1")
                .dateNotification(LocalDateTime.now())
                .build();

        when(notificationService.getNotificationById(notificationId)).thenReturn(notification);

        // Act
        ResponseEntity<Notification> response = notificationController.getNotificationById(notificationId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService, times(1)).getNotificationById(notificationId);
    }

    @Test
    public void testGetNotificationById_NotFound() {
        // Arrange
        long notificationId = 1L;
        when(notificationService.getNotificationById(notificationId)).thenReturn(null);

        // Act
        ResponseEntity<Notification> response = notificationController.getNotificationById(notificationId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(notificationService, times(1)).getNotificationById(notificationId);
    }
}
