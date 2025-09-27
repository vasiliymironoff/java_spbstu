package com.mironov.taskmanager.messaging;

import com.mironov.taskmanager.config.RabbitMQConfig;
import com.mironov.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {
    @Autowired
    private final NotificationService notificationService;

    @RabbitListener(
            queues = RabbitMQConfig.TASK_CREATED_QUEUE,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleTaskCreated(Message message) {
        try {
            log.info("Received task created message: {}", message);

            notificationService.createNotificationFromMessage(
                    "New task created: " + message.getTitle(),
                    message.getUserId()
            );
            log.info("Created notification for task: {}", message.getTaskId());
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw e;
        }
    }

}