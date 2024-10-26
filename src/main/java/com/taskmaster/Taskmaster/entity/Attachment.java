package com.taskmaster.Taskmaster.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private UUID attachmentId;
    private UUID taskId; // Foreign key to Task
    private UUID userId; // Foreign key to Task
    private String fileUrl; // URL of the file stored
    private Timestamp createdAt;
}
