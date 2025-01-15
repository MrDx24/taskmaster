package com.taskmaster.Taskmaster.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private UUID id;
    private UUID taskId; // Foreign key to Task
    private UUID userId; // Foreign key to User
    private String commentText;
    private Timestamp createdAt;
}

