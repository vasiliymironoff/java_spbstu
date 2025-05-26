package com.example.TasksApplication.controller;

import com.example.TasksApplication.model.Task;
import com.example.TasksApplication.service.InMemoryTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private InMemoryTaskService taskService;

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
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act
        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskByIdFound() {
        // Arrange
        long id = 1;
        Task task = Task.builder().taskId(id).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        when(taskService.getTaskById(id)).thenReturn(task);

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(id);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        // Arrange
        long id = 1;
        when(taskService.getTaskById(id)).thenReturn(null);

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).getTaskById(id);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        Task task = Task.builder().taskId(1).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        // Act
        ResponseEntity<Task> response = taskController.createTask(task);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    public void testDeleteTaskFound() {
        // Arrange
        long id = 1;
        when(taskService.deleteTask(id)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(id);
    }

    @Test
    public void testDeleteTaskNotFound() {
        // Arrange
        long id = 1;
        when(taskService.deleteTask(id)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(id);
    }
}
