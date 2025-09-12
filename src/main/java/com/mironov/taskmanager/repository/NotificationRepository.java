package com.mironov.taskmanager.repository;

import com.mironov.taskmanager.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Optional<Notification> findById(Long notificationId);
    void createNotification(Notification notification);
    List<Notification> findByUserId(Long userId);
    List<Notification> findPendingByUserId(Long userId);
}