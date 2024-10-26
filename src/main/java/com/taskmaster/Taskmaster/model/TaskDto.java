package com.taskmaster.Taskmaster.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class TaskDto {
    private String title;
    private String description;
    private String dueDate;  // Format: dd-MM-yyyy
    private UUID projectId;
    private String assignedTo;
}
