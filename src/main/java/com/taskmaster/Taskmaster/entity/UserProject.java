package com.taskmaster.Taskmaster.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class UserProject {
    private UUID teamId; // Foreign key to Team
    private UUID userId; // Foreign key to User
    private Timestamp joinedAt;
}
