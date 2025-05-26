package com.example.TasksApplication.service;

import com.example.TasksApplication.model.Notification;
import com.example.TasksApplication.repository.InMemoryNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryNotificationService {
    @Autowired
    private InMemoryNotificationRepository notificationRepository;

    public Notification getById(long id) {
        return notificationRepository.getNotificationById(id);
    }

    public List<Notification> getAllNotification() {
        return notificationRepository.getAllNotification();
    }
}
