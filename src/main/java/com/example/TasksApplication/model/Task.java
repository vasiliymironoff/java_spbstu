package com.example.TasksApplication.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Task {

    @NonNull
    private long taskId;

    private String taskTitle;
    private String taskDescription;
    private String taskEmail;
}