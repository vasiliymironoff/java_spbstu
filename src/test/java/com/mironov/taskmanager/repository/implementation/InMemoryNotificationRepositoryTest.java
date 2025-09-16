package com.mironov.taskmanager.repository.implementation;

import com.mironov.taskmanager.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryNotificationRepositoryTest {
    private InMemoryNotificationRepository repository;
    private Notification testNotification;
    final private Long testUserId = 1L;
    final private Long testTaskId = 1L;

    @BeforeEach
    void setUp() {
        repository = new InMemoryNotificationRepository();

        testNotification = Notification.builder()
                .text("Test notification")
                .taskId(testTaskId)
                .userId(testUserId)
                .pending(false)
                .dateCreation(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateNotification() {
        // Создаем уведомление
        repository.save(testNotification);

        // Проверяем, что уведомление было создано
        assertEquals(1, repository.size());

        // Проверяем поля уведомления
        Notification createdNotification = repository.values().iterator().next();
        assertEquals(testNotification.getText(), createdNotification.getText());
        assertEquals(testNotification.getTaskId(), createdNotification.getTaskId());
        assertEquals(testNotification.getUserId(), createdNotification.getUserId());
        assertEquals(testNotification.getPending(), createdNotification.getPending());
        assertEquals(testNotification.getDateCreation(), createdNotification.getDateCreation());
    }

    @Test
    void testFindByUserId() {
        // Создаем несколько уведомлений для разных пользователей
        Notification user1Notification = Notification.builder()
                .text("User 1 notification")
                .taskId(testTaskId)
                .userId(1L)
                .build();

        Notification user2Notification = Notification.builder()
                .text("User 2 notification")
                .taskId(testTaskId)
                .userId(2L)
                .build();

        repository.save(user1Notification);
        repository.save(user2Notification);

        // Ищем уведомления для пользователя 1
        List<Notification> user1Notifications = repository.findByUserId(1L);
        assertEquals(1, user1Notifications.size());
        assertEquals(user1Notification.getText(), user1Notifications.get(0).getText());

        // Ищем уведомления для пользователя 2
        List<Notification> user2Notifications = repository.findByUserId(2L);
        assertEquals(1, user2Notifications.size());
        assertEquals(user2Notification.getText(), user2Notifications.get(0).getText());
    }

    @Test
    void testFindPendingByUserId() {
        // Создаем уведомления с разными статусами
        Notification pendingNotification = Notification.builder()
                .text("Pending notification")
                .taskId(testTaskId)
                .userId(testUserId)
                .pending(true)
                .build();

        Notification nonPendingNotification = Notification.builder()
                .text("Non-pending notification")
                .taskId(testTaskId)
                .userId(testUserId)
                .pending(false)
                .build();

        repository.save(pendingNotification);
        repository.save(nonPendingNotification);

        // Ищем ожидающие уведомления
        List<Notification> pendingNotifications = repository.findByUserIdAndPending(testUserId);
        assertEquals(1, pendingNotifications.size());
        assertTrue(pendingNotifications.get(0).getPending());
    }

    @Test
    void testFindById_ExistingId() {
        // Создаем уведомление и сохраняем его
        repository.save(testNotification);

        // Получаем ID созданного уведомления
        Long notificationId = repository.keySet().iterator().next();

        // Пытаемся найти уведомление по ID
        Optional<Notification> foundNotification = repository.findByNotificationId(notificationId);

        // Проверяем, что метод возвращает пустой Optional
        assertFalse(foundNotification.isPresent());
    }
}