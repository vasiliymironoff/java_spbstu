package com.mironov.taskmanager.messaging;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private Long taskId;
    private String title;
    private String description;
    private Long userId;
    private Boolean pending;
    private LocalDateTime dateCreation;
}