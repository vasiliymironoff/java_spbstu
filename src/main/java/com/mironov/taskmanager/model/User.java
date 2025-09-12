package com.mironov.taskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    
    @NonNull
    private String username;
    
    @NonNull
    private String password;
    
    @NonNull
    private String email;

    @NonNull
    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();
} 