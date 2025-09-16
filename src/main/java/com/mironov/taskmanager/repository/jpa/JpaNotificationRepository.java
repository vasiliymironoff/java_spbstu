package com.mironov.taskmanager.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mironov.taskmanager.model.Notification;
import java.util.List;
import java.util.Optional;


@Repository
@Profile("h2")
public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {
    // Сохранение уведомления
    @Override
    Notification save(Notification notification);
    // Поиск по ID уведомления
    Optional<Notification> findByNotificationId(Long notificationId);
    // Поиск всех уведомлений по пользователю
    List<Notification> findByUserId(Long userId);
    // Поиск уведомлений по пользователю с фильтрацией по статусу
    List<Notification> findByUserIdAndPending(Long userId, Boolean pending);
}