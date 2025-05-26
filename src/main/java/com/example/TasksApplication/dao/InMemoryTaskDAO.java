package com.example.TasksApplication.dao;

import com.example.TasksApplication.model.Task;

import java.util.List;

public interface InMemoryTaskDAO {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    boolean deleteTask(Long id);
}