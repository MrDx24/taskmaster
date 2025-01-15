package com.taskmaster.Taskmaster.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    private UUID task_id;
    private String title;
    private String description;
    private Date dueDate;
    private String status; // OPEN, IN_PROGRESS, COMPLETED
    private UUID project_id;
    private UUID createdBy; // Foreign key to User
    private UUID assignedTo; // Foreign key to User
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

