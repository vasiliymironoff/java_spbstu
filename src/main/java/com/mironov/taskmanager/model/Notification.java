package com.mironov.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    @NonNull
    private String text;

    @Column(nullable = false)
    @NonNull
    private Long taskId;

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

}