package com.example.TasksApplication.controllerjpa;

import com.example.TasksApplication.controller.NotificationController;
import com.example.TasksApplication.model.Notification;
import com.example.TasksApplication.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationControllerJPATest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetAllNotifications() {
        Notification notification1 = new Notification(1, 1, 1, "Test Notification 1", LocalDateTime.now());
        Notification notification2 = new Notification(2, 1, 2, "Test Notification 2", LocalDateTime.now());
        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotification()).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getAllNotification();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
        verify(notificationService, times(1)).getAllNotification();
    }

    @Test
    public void testGetNotificationById() {
        long notificationId = 1;
        Notification notification = new Notification(notificationId, 1, 1, "Test Notification", LocalDateTime.now());

        when(notificationService.getNotificationById(notificationId)).thenReturn(notification);

        ResponseEntity<Notification> response = notificationController.getNotificationById(notificationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService, times(1)).getNotificationById(notificationId);
    }

    @Test
    public void testCreateNotification() {
        Notification notification = new Notification(0, 1, 1, "New Notification", LocalDateTime.now());
        Notification createdNotification = new Notification(1, 1, 1, "New Notification", LocalDateTime.now());

        when(notificationService.saveNotification(any(Notification.class))).thenReturn(createdNotification);

        ResponseEntity<Notification> response = notificationController.createNotification(notification);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdNotification, response.getBody());
        verify(notificationService, times(1)).saveNotification(any(Notification.class));
    }
}
