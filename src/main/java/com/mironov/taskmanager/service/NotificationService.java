package com.mironov.taskmanager.service;

import com.mironov.taskmanager.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mironov.taskmanager.model.Notification;
import com.mironov.taskmanager.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
    }

    public Notification createNotification(Notification notification) {
        notificationRepository.createNotification(notification);
        return notification;
    }

    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findPendingByUserId(userId);
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