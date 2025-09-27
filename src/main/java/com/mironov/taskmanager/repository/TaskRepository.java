package com.mironov.taskmanager.repository;

import com.mironov.taskmanager.model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);
    List<Task> getAllTasks();
    List<Task> findPendingTask(Long userId);
    void deleteById(Long taskId);
    Task updateTask(Long taskId, Task task);
}