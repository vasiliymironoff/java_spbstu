package com.mironov.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false)
    @NonNull
    private String description;

    @Column(nullable = false)
    @NonNull
    private Long userId;

    @Column(nullable = false)
    @NonNull
    @Builder.Default
    private Boolean pending = false;

    @Column(nullable = false)
    @NonNull
    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.TODO;
} 