package com.mironov.taskmanager.repository;

import com.mironov.taskmanager.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findByNotificationId(Long notificationId);
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndPending(Long userId);
}