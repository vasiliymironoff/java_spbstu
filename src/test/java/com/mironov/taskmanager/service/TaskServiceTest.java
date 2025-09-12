package com.mironov.taskmanager.service;

import com.mironov.taskmanager.model.Task;
import com.mironov.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Task createTestTask() {
        return Task.builder()
                .title("Test Task")
                .description("Test description")
                .userId(1L)
                .pending(true)
                .build();
    }

    @Test
    void testGetPendingTasks() {
        // Подготовка
        Task pendingTask = createTestTask();
        Task nonPendingTask = Task.builder()
                .title("Non-pending Task")
                .description("Non-pending description")
                .userId(1L)
                .pending(false)
                .build();

        when(taskRepository.findPendingTask(anyLong()))
                .thenReturn(List.of(pendingTask));

        // Выполнение
        List<Task> pendingTasks = taskService.getPendingTasks(1L);

        // Проверка
        assertEquals(1, pendingTasks.size());
        assertEquals(pendingTask, pendingTasks.get(0));
        assertTrue(pendingTasks.get(0).getPending());
    }

    @Test
    void testGetPendingTasks_NoPending() {
        // Подготовка
        when(taskRepository.findPendingTask(anyLong()))
                .thenReturn(List.of());

        // Выполнение
        List<Task> pendingTasks = taskService.getPendingTasks(1L);

        // Проверка
        assertTrue(pendingTasks.isEmpty());
    }

    @Test
    void testGetPendingTasks_MultiplePending() {
        // Подготовка
        Task pendingTask1 = Task.builder()
                .title("Pending Task 1")
                .description("Description 1")
                .userId(1L)
                .pending(true)
                .build();

        Task pendingTask2 = Task.builder()
                .title("Pending Task 2")
                .description("Description 2")
                .userId(1L)
                .pending(true)
                .build();

        when(taskRepository.findPendingTask(anyLong()))
                .thenReturn(List.of(pendingTask1, pendingTask2));

        // Выполнение
        List<Task> pendingTasks = taskService.getPendingTasks(1L);

        // Проверка
        assertEquals(2, pendingTasks.size());
        assertTrue(pendingTasks.contains(pendingTask1));
        assertTrue(pendingTasks.contains(pendingTask2));
    }

    @Test
    void testDeleteTask() {
        // Подготовка
        Long taskId = 1L;

        // Выполнение
        taskService.deleteTask(taskId);

        // Проверка
        verify(taskRepository).deleteTask(taskId);
    }
}