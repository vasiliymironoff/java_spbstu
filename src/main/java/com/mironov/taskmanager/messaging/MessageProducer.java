package com.mironov.taskmanager.messaging;

import com.mironov.taskmanager.config.RabbitMQConfig;
import com.mironov.taskmanager.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishTaskCreated(Task task) {
        Message message = Message.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUserId())
                .dateCreation(task.getDateCreation())
                .build();

        log.info("Publishing task created message: {}", message);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TASK_CREATED_EXCHANGE,
                RabbitMQConfig.TASK_CREATED_ROUTING_KEY,
                message
        );
    }
}