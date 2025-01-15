package com.taskmaster.Taskmaster.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {
    private UUID project_id;
    private String title;
    private String description;
    private UUID created_by;
    private Timestamp created_at;
}

