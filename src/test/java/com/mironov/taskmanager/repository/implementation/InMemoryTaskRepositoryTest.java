package com.mironov.taskmanager.repository.implementation;

import com.mironov.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest {
    private InMemoryTaskRepository repository;
    private Task testTask;
    final private Long testUserId = 1L;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();

        testTask = Task.builder()
                .title("Test Task")
                .description("This is a test task")
                .userId(testUserId)
                .pending(true)
                .dateCreation(LocalDateTime.now())
                .build();
    }

    @Test
    void testSaveTask() {
        // Сохраняем задачу
        Task savedTask = repository.save(testTask);

        // Проверяем, что задача сохранена
        assertNotNull(savedTask.getTaskId());
        assertEquals(1, repository.size());

        // Проверяем поля
        assertEquals(testTask.getTitle(), savedTask.getTitle());
        assertEquals(testTask.getDescription(), savedTask.getDescription());
        assertEquals(testTask.getUserId(), savedTask.getUserId());
        assertEquals(testTask.getPending(), savedTask.getPending());
        assertEquals(testTask.getDateCreation(), savedTask.getDateCreation());
    }

    @Test
    void testSaveTask_WithExistingId() {
        // Создаем задачу с заданным ID
        Task taskWithId = Task.builder()
                .taskId(100L)
                .title("Test Task")
                .description("This is a test task")
                .userId(testUserId)
                .build();

        Task savedTask = repository.save(taskWithId);
        assertEquals(100L, savedTask.getTaskId());
    }

    @Test
    void testGetAllTasks() {
        // Сохраняем несколько задач
        Task task1 = repository.save(testTask);
        Task task2 = repository.save(Task.builder()
                .title("Second Task")
                .description("Second task description")
                .userId(testUserId)
                .build());

        // Получаем все задачи
        List<Task> allTasks = repository.getAllTasks();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    void testFindPendingTask() {
        // Сохраняем задачу в статусе pending
        Task pendingTask = repository.save(testTask);

        // Получаем ожидающие задачи
        List<Task> pendingTasks = repository.findPendingTask(testUserId);
        assertEquals(1, pendingTasks.size());
        assertEquals(pendingTask, pendingTasks.get(0));

        // Проверяем, что статус изменился на false
        assertFalse(pendingTask.getPending());
    }

    @Test
    void testDeleteTask() {
        // Сохраняем задачу
        Task taskToDelete = repository.save(testTask);

        // Удаляем задачу
        repository.deleteById(taskToDelete.getTaskId());

        // Проверяем удаление
        assertEquals(0, repository.size());
    }

    @Test
    void testDeleteNonExistingTask() {
        // Пытаемся удалить несуществующую задачу
        repository.deleteById(1L);

        // Проверяем, что ничего не произошло
        assertEquals(0, repository.size());
    }

    @Test
    void testSaveMultipleTasks() {
        // Сохраняем несколько задач
        Task task1 = repository.save(testTask);
        Task task2 = repository.save(Task.builder()
                .title("Second Task")
                .description("Second task description")
                .userId(testUserId)
                .build());

        assertEquals(1L, task1.getTaskId());
        assertEquals(2L, task2.getTaskId());
    }
}