package com.example.TasksApplication.controllerjpa;

import com.example.TasksApplication.controller.TaskController;
import com.example.TasksApplication.model.Task;
import com.example.TasksApplication.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskControllerJPATest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task(1, "Task 1", "Description 1", "email1@example.com");
        Task task2 = new Task(2, "Task 2", "Description 2", "email2@example.com");
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskById() {
        long taskId = 1;
        Task task = new Task(taskId, "Task 1", "Description 1", "email1@example.com");

        when(taskService.getTaskById(taskId)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(0, "New Task", "New Description", "newemail@example.com");
        Task createdTask = new Task(1, "New Task", "New Description", "newemail@example.com");

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);

        ResponseEntity<Task> response = taskController.createTask(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    public void testDeleteTask() {
        long taskId = 1;

        when(taskService.deleteTask(taskId)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    public void testDeleteTaskNotFound() {
        long taskId = 1;

        when(taskService.deleteTask(taskId)).thenReturn(false);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }
}
