package com.mironov.taskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long taskId;
    
    @NonNull
    private String title;
    
    @NonNull
    private String description;
    
    @NonNull
    private Long userId;

    @NonNull
    @Builder.Default
    private Boolean pending = false;

    @NonNull
    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();
} 