package com.mironov.taskmanager.service;

import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.repository.jpa.JpaNotificationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mironov.taskmanager.model.Notification;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final JpaNotificationRepository notificationRepository;

    @Cacheable(value = "notifications", key = "#notificationId.toString()", unless = "#result.isEmpty()")
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
    }

    @CacheEvict(value = "notifications", allEntries = true)
    public Notification createNotification(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }

    @Cacheable(value = "notifications", key = "#userId.toString()", unless = "#result.isEmpty()")
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Cacheable(value = "notifications", key = "#userId.toString()", unless = "#result.isEmpty()")
    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findByUserIdAndPending(userId, true);
    }
}