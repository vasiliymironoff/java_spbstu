package com.mironov.taskmanager.service;

import com.mironov.taskmanager.repository.jpa.JpaTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mironov.taskmanager.model.Task;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private final JpaTaskRepository taskRepository;

    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findAll();
    }

    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndPending(userId, true);
    }

    public Task createTask(Task task) {
        validateTask(task);
        task.setDateCreation(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
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