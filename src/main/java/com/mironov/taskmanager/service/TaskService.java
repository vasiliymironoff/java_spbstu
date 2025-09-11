package com.mironov.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mironov.taskmanager.exception.ResourceNotFoundException;
import com.mironov.taskmanager.model.Task;
import com.mironov.taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(Long userId) {
        return taskRepository.getAllTasks();
    }

    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findPendingTask(userId);
    }

    public Task createTask(Task task) {
        validateTask(task);
        task.setDateCreation(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteTask(taskId);
    }

    private void validateTask(Task task) {
        if (task.getUserId() == null) {
            throw new IllegalArgumentException("Task userId cannot be null");
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
    }
}