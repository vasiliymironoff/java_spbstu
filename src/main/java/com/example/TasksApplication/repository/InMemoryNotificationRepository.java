package com.example.TasksApplication.repository;

import com.example.TasksApplication.dao.InMemoryNotificationDAO;
import com.example.TasksApplication.dao.InMemoryTaskDAO;
import com.example.TasksApplication.model.Notification;
import com.example.TasksApplication.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryNotificationRepository implements InMemoryNotificationDAO {

    private final List<Notification> notifications = new ArrayList<>();
    private Long currentId = 1L;

    @Override
    public List<Notification> getAllNotification() {
        return notifications;

    }

    @Override
    public Notification getNotificationById(Long id) {
        return notifications.stream().filter(notification -> notification.getIdNotification() == id).findAny().orElse(null);
    }
}
