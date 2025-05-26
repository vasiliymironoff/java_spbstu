package com.example.TasksApplication.service;

import com.example.TasksApplication.model.Task;
import com.example.TasksApplication.repository.InMemoryTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryTaskService {
    private final InMemoryTaskRepository taskRepository;

    @Autowired
    public InMemoryTaskService(InMemoryTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks(); // Используем репозиторий для получения всех задач
    }

    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id); // Используем репозиторий для получения задачи по ID
    }

    public Task createTask(Task task) {
        return taskRepository.createTask(task); // Используем репозиторий для сохранения задачи
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteTask(id); // Используем репозиторий для удаления задачи
            return true;
        }
        return false;
    }
}

