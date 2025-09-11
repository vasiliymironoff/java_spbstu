package com.mironov.taskmanager.repository.implementation;

import org.springframework.stereotype.Repository;
import com.mironov.taskmanager.model.Task;
import com.mironov.taskmanager.repository.TaskRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong taskIdCounter = new AtomicLong(1);


    @Override
    public Task save(Task task) {
        if (task.getTaskId() == null) {
            task.setTaskId(taskIdCounter.getAndIncrement());
        }
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> findPendingTask(Long userId) {
        List<Task> t = tasks.values().stream().filter(x -> x.getPending()).toList();
        tasks.forEach((i, x) -> x.setPending(false));
        return t;
    }

    @Override
    public void deleteTask(Long taskId) {
        for (Long i : tasks.keySet()) {
            if (tasks.get(i).getTaskId() == taskId) {
                tasks.remove(i);
                break;
            }
        }
    }
}