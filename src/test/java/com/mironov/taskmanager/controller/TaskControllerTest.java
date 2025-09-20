package com.mironov.taskmanager.controller;

import com.mironov.taskmanager.model.Task;
import com.mironov.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации моков", e);
        }
    }

    private Task createTestTask() {
        return Task.builder()
                .taskId(1L)
                .title("Test Task")
                .description("This is a test task")
                .userId(1L)
                .build();
    }

    // Тесты для получения всех задач
    @Test
    void testGetAllTasks_Success() {
        // Подготовка
        List<Task> tasks = List.of(createTestTask());
        when(taskService.getAllTasks(anyLong()))
                .thenReturn(tasks);

        // Выполнение
        ResponseEntity<List<Task>> response = taskController.getAllTasks(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService).getAllTasks(1L);
    }

    @Test
    void testGetAllTasks_NoTasks() {
        // Подготовка
        when(taskService.getAllTasks(anyLong()))
                .thenReturn(List.of());

        // Выполнение
        ResponseEntity<List<Task>> response = taskController.getAllTasks(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    // Тесты для получения ожидающих задач
    @Test
    void testGetPendingTasks_Success() {
        // Подготовка
        List<Task> pendingTasks = List.of(createTestTask());
        when(taskService.getPendingTasks(anyLong()))
                .thenReturn(pendingTasks);

        // Выполнение
        ResponseEntity<List<Task>> response = taskController.getPendingTasks(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pendingTasks, response.getBody());
        verify(taskService).getPendingTasks(1L);
    }

    @Test
    void testGetPendingTasks_NoPending() {
        // Подготовка
        when(taskService.getPendingTasks(anyLong()))
                .thenReturn(List.of());

        // Выполнение
        ResponseEntity<List<Task>> response = taskController.getPendingTasks(1L);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // Тесты для создания задачи
    @Test
    void testCreateTask_Success() {
        // Подготовка
        Task task = createTestTask();
        when(taskService.createTask(any(Task.class)))
                .thenReturn(task);

        // Выполнение
        ResponseEntity<Task> response = taskController.createTask(task);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService).createTask(task);
    }
}