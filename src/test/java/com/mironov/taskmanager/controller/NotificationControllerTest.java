package com.mironov.taskmanager.controller;

import com.mironov.taskmanager.model.Notification;
import com.mironov.taskmanager.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Notification createTestNotification() {
        return Notification.builder()
                .notificationId(1L)
                .userId(1L)
                .taskId(1L)
                .text("Test notification")
                .pending(false)
                .build();
    }

    // Тесты для получения всех уведомлений
    @Test
    void testGetAllNotifications_Success() {
        // Подготовка
        List<Notification> notifications = List.of(createTestNotification());
        when(notificationService.getAllNotifications(anyLong()))
                .thenReturn(notifications);

        // Выполнение
        ResponseEntity<List<Notification>> response =
                notificationController.getAllNotifications(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
        verify(notificationService).getAllNotifications(1L);
    }

    @Test
    void testGetAllNotifications_NoNotifications() {
        // Подготовка
        when(notificationService.getAllNotifications(anyLong()))
                .thenReturn(List.of());

        // Выполнение
        ResponseEntity<List<Notification>> response =
                notificationController.getAllNotifications(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // Тесты для получения ожидающих уведомлений
    @Test
    void testGetPendingNotifications_Success() {
        // Подготовка
        List<Notification> pendingNotifications = List.of(createTestNotification());
        when(notificationService.getPendingNotifications(anyLong()))
                .thenReturn(pendingNotifications);

        // Выполнение
        ResponseEntity<List<Notification>> response =
                notificationController.getPendingNotifications(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pendingNotifications, response.getBody());
        verify(notificationService).getPendingNotifications(1L);
    }

    @Test
    void testGetPendingNotifications_NoPending() {
        // Подготовка
        when(notificationService.getPendingNotifications(anyLong()))
                .thenReturn(List.of());

        // Выполнение
        ResponseEntity<List<Notification>> response =
                notificationController.getPendingNotifications(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // Тесты для создания уведомления
    @Test
    void testCreateNotification_Success() {
        // Подготовка
        Notification notification = createTestNotification();
        when(notificationService.createNotification(any(Notification.class)))
                .thenReturn(notification);

        // Выполнение
        ResponseEntity<Notification> response =
                notificationController.createNotification(notification);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService).createNotification(notification);
    }
}