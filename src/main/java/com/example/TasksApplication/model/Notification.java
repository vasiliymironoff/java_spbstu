package com.example.TasksApplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idNotification;

    @NonNull
    private long userIdNotification;

    private long TaskIdNotification;

    @NonNull
    private String textNotification;
    private LocalDateTime dateNotification;



}
