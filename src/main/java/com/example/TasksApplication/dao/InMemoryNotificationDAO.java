package com.example.TasksApplication.dao;

import com.example.TasksApplication.model.Notification;
import com.example.TasksApplication.model.Task;

import java.util.List;

public interface InMemoryNotificationDAO {
    List<Notification> getAllNotification();
    Notification getNotificationById(Long id);
}
