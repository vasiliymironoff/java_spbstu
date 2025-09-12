package com.mironov.taskmanager.service;

import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.model.Notification;
import com.mironov.taskmanager.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Notification createTestNotification() {
        return Notification.builder()
                .text("Test notification")
                .taskId(1L)
                .userId(1L)
                .build();
    }

    @Test
    void testGetNotificationById_Success() {
        // Подготовка
        Notification notification = createTestNotification();
        when(notificationRepository.findById(anyLong()))
                .thenReturn(Optional.of(notification));

        // Выполнение
        Notification result = notificationService.getNotificationById(1L);

        // Проверка
        assertEquals(notification, result);
        verify(notificationRepository).findById(1L);
    }

    @Test
    void testGetNotificationById_NotFound() {
        // Подготовка
        when(notificationRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Выполнение и проверка
        assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.getNotificationById(1L);
        });
    }

    @Test
    void testCreateNotification() {
        // Подготовка
        Notification notification = createTestNotification();

        // Выполнение
        Notification result = notificationService.createNotification(notification);

        // Проверка
        assertEquals(notification, result);
        verify(notificationRepository).createNotification(notification);
    }

    @Test
    void testGetAllNotifications() {
        // Подготовка
        Notification notification1 = createTestNotification();
        Notification notification2 = Notification.builder()
                .text("Second notification")
                .taskId(2L)
                .userId(1L)
                .build();

        when(notificationRepository.findByUserId(anyLong()))
                .thenReturn(List.of(notification1, notification2));

        // Выполнение
        List<Notification> result = notificationService.getAllNotifications(1L);

        // Проверка
        assertEquals(2, result.size());
        assertTrue(result.contains(notification1));
        assertTrue(result.contains(notification2));
    }
}