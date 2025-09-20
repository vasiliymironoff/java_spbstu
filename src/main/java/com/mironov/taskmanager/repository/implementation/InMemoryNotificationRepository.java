package com.mironov.taskmanager.repository.implementation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import com.mironov.taskmanager.model.Notification;
import com.mironov.taskmanager.repository.NotificationRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryNotificationRepository implements NotificationRepository {
    private final Map<Long, Notification> notifications = new HashMap<>();
    private final AtomicLong notificationIdCounter = new AtomicLong(1);

    @Override
    public Optional<Notification> findByNotificationId(Long notificationId) {
        return Optional.empty();
    }

    @Override
    public Notification save(Notification notification) {
        return notifications.put(notificationIdCounter.addAndGet(1), notification);
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return notifications.values().stream().filter(x -> x.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<Notification> findByUserIdAndPending(Long userId) {
        return notifications.values().stream().filter(
                        x -> (x.getUserId().equals(userId)) && x.getPending()
                )
                .toList();
    }

    public int size() {
        return notifications.size();
    }

    public List<Long> keySet() {
        return notifications.keySet().stream().toList();
    }

    public List<Notification> values() {
        return notifications.values().stream().toList();
    }
}