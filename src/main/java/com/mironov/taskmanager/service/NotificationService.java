package com.mironov.taskmanager.service;

import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.repository.jpa.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mironov.taskmanager.model.Notification;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JpaNotificationRepository notificationRepository;

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
    }

    public Notification createNotification(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findByUserIdAndPending(userId, true);
    }

    private void validateNotification(Notification notification) {
        if (notification.getText() == null
                || notification.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification text cannot be empty");
        }
        if (notification.getTaskId() == null) {
            throw new IllegalArgumentException("TaskID cannot be null");
        }
        if (notification.getUserId() == null) {
            throw new IllegalArgumentException("UserID cannot be null");
        }
    }
}