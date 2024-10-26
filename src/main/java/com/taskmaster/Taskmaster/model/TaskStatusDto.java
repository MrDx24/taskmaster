package com.taskmaster.Taskmaster.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class TaskStatusDto {
    private UUID taskId;
    private String status;
}
