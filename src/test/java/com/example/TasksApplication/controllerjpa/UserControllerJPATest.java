package com.example.TasksApplication.controllerjpa;

import com.example.TasksApplication.controller.UserController;
import com.example.TasksApplication.model.User;
import com.example.TasksApplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerJPATest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserById() {
        long userId = 1;
        User user = new User(userId, "John", "Doe", "john.doe@example.com");

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<List<User>> response = userController.getAllUsers(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(user), response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testCreateUser() {
        User user = new User(0, "Jane", "Doe", "jane.doe@example.com");
        User createdUser = new User(1, "Jane", "Doe", "jane.doe@example.com");

        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());
        verify(userService, times(1)).createUser(any(User.class));
    }
}
