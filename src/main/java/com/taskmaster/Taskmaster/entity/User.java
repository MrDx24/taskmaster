package com.taskmaster.Taskmaster.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private Timestamp createdAt;
}
