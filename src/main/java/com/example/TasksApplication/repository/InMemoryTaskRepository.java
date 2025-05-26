package com.example.TasksApplication.repository;

import com.example.TasksApplication.dao.InMemoryTaskDAO;
import com.example.TasksApplication.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class InMemoryTaskRepository implements InMemoryTaskDAO {
    private final List<Task> tasks = new ArrayList<>();
    private Long currentId = 1L;

    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public Task getTaskById(Long id) {
        return tasks.stream().filter(task -> task.getTaskId() == id).findFirst().orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        task.setTaskId(currentId);
        currentId++;
        tasks.add(task);
        return task;
    }

    @Override
    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getTaskId() == id);
    }

    public boolean existsById(Long id) {
        return tasks.stream().filter(task -> task.getTaskId() == id).count() > 0;
    }
}
