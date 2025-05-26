package com.example.TasksApplication.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Notification {

    @NonNull
    private long idNotification;

    @NonNull
    private long userIdNotification;

    private long TaskIdNotification;

    @NonNull
    private String textNotification;
    private LocalDateTime dateNotification;



}
