package com.example.TasksApplication.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {

    @NonNull
    private long userId;

    private String firstName;
    private String lastName;
    private String email;

}