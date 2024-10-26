package com.taskmaster.Taskmaster.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class Notification {
    private UUID id;
    private UUID userId; // Foreign key to User
    private String message;
    private boolean readStatus;
    private Timestamp createdAt;
}

