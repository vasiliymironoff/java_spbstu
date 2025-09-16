package com.mironov.taskmanager.repository;

import com.mironov.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    List<Task> getAllTasks();
    List<Task> findPendingTask(Long userId);
    void deleteById(Long taskId);
}