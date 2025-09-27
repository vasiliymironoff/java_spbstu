package com.mironov.taskmanager.service;

import com.mironov.taskmanager.model.Status;
import com.mironov.taskmanager.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskSchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerService.class);

    private final TaskService taskService;
    private final NotificationService notificationService;

    public TaskSchedulerService(TaskService taskService, NotificationService notificationService) {
        this.taskService = taskService;
        this.notificationService = notificationService;
    }


    // Every 100 seconds
    @Scheduled(fixedRate = 100000)
    public void checkOverdueTasks() {
        logger.info("Checking for overdue tasks...");
        List<Task> overdueTasks = findOverdueTasks();

        for (Task task : overdueTasks) {
            processOverdueTask(task);
        }
    }

    public List<Task> findOverdueTasks() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(10);
        return taskService.findByStatus(Status.TODO)
                .stream()
                .filter(task -> !task.getPending())
                .filter(task -> task.getDateCreation().isBefore(cutoffDate))
                .toList();
    }

    @Async
    public void processOverdueTask(Task task) {
        logger.info("Processing overdue task: {}", task.getTaskId());

        // Update task status
        task.setStatus(Status.OVERDUE);
        taskService.updateTask(task.getTaskId(), task);

        // Send notification
        notificationService.createNotificationFromMessage(
                "Task '" + task.getTitle() + "' is overdue!",
                task.getUserId()
        );
    }
}