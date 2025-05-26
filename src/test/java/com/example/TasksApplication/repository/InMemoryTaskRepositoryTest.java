package com.example.TasksApplication.repository;

import com.example.TasksApplication.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTaskRepositoryTest {

    private InMemoryTaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository = new InMemoryTaskRepository();
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        Task task1 = Task.builder().taskId(1).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        Task task2 = Task.builder().taskId(2).taskTitle("Task 2").taskDescription("Description 2").taskEmail("email2@example.com").build();
        taskRepository.createTask(task1);
        taskRepository.createTask(task2);

        // Act
        List<Task> tasks = taskRepository.getAllTasks();

        // Assert
        assertThat(tasks).hasSize(2);
        assertThat(tasks).containsExactlyInAnyOrder(task1, task2);
    }

    @Test
    public void testGetTaskByIdFound() {
        // Arrange
        Task task = Task.builder().taskId(1).taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        taskRepository.createTask(task);

        // Act
        Task foundTask = taskRepository.getTaskById(1L);

        // Assert
        assertThat(foundTask).isNotNull();
        assertThat(foundTask).isEqualTo(task);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        // Act
        Task foundTask = taskRepository.getTaskById(1L);

        // Assert
        assertThat(foundTask).isNull();
    }

    @Test
    public void testCreateTask() {
        // Arrange
        Task task = Task.builder().taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();

        // Act
        Task createdTask = taskRepository.createTask(task);

        // Assert
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTaskId()).isEqualTo(1L); // Проверяем, что ID установлен
        assertThat(createdTask.getTaskTitle()).isEqualTo("Task 1");
    }

    @Test
    public void testDeleteTaskFound() {
        // Arrange
        Task task = Task.builder().taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        taskRepository.createTask(task);

        // Act
        boolean isDeleted = taskRepository.deleteTask(1L);

        // Assert
        assertThat(isDeleted).isTrue();
        assertThat(taskRepository.getTaskById(1L)).isNull(); // Проверяем, что задача удалена
    }

    @Test
    public void testDeleteTaskNotFound() {
        // Act
        boolean isDeleted = taskRepository.deleteTask(1L);

        // Assert
        assertThat(isDeleted).isFalse(); // Задача не найдена, поэтому удаление должно вернуть false
    }

    @Test
    public void testExistsByIdTrue() {
        // Arrange
        Task task = Task.builder().taskTitle("Task 1").taskDescription("Description 1").taskEmail("email1@example.com").build();
        taskRepository.createTask(task);

        // Act
        boolean exists = taskRepository.existsById(1L);

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByIdFalse() {
        // Act
        boolean exists = taskRepository.existsById(1L);

        // Assert
        assertThat(exists).isFalse();
    }
}
