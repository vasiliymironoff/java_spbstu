package com.mironov.taskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private Long notificationId;

    @NonNull
    private String text;

    @NonNull
    private Long taskId;

    @NonNull
    private Long userId;

    @NonNull
    @Builder.Default
    private Boolean pending = false;

    @NonNull
    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();

} 