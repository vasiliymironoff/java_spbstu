package com.example.TasksApplication.service;

import com.example.TasksApplication.model.Task;
import com.example.TasksApplication.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class InMemoryTaskServiceTest {

    @InjectMocks
    private InMemoryTaskService taskService;

    @Mock
    private InMemoryTaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        Task task1 = Task.builder().taskId(1).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        Task task2 = Task.builder().taskId(2).taskTitle("Task 2").taskDescription("Description 2").taskEmail("email2@example.com").build();
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.getAllTasks()).thenReturn(tasks);

        // Act
        List<Task> foundTasks = taskService.getAllTasks();

        // Assert
        assertThat(foundTasks).hasSize(2);
        assertThat(foundTasks).containsExactlyInAnyOrder(task1, task2);
        verify(taskRepository, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskByIdFound() {
        // Arrange
        long id = 1L;
        Task task = Task.builder().taskId(id).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        when(taskRepository.getTaskById(id)).thenReturn(task);

        // Act
        Task foundTask = taskService.getTaskById(id);

        // Assert
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getTaskId()).isEqualTo(id);
        assertThat(foundTask.getTaskTitle()).isEqualTo("Task 1");
        verify(taskRepository, times(1)).getTaskById(id);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        // Arrange
        long id = 1L;
        when(taskRepository.getTaskById(id)).thenReturn(null);

        // Act
        Task foundTask = taskService.getTaskById(id);

        // Assert
        assertThat(foundTask).isNull();
        verify(taskRepository, times(1)).getTaskById(id);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        Task task = Task.builder().taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        when(taskRepository.createTask(task)).thenReturn(task);

        // Act
        Task createdTask = taskService.createTask(task);

        // Assert
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTaskTitle()).isEqualTo("Task 1");
        verify(taskRepository, times(1)).createTask(task);
    }

    @Test
    public void testDeleteTaskFound() {
        // Arrange
        long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(true);
        when(taskRepository.deleteTask(id)).thenReturn(true);

        // Act
        boolean isDeleted = taskService.deleteTask(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(taskRepository, times(1)).deleteTask(id);
    }

    @Test
    public void testDeleteTaskNotFound() {
        // Arrange
        long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(false);

        // Act
        boolean isDeleted = taskService.deleteTask(id);

        // Assert
        assertThat(isDeleted).isFalse();
        verify(taskRepository, never()).deleteTask(id); // Удаление не должно вызываться
    }
}
