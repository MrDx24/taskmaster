package com.taskmaster.Taskmaster.service;

import com.taskmaster.Taskmaster.entity.Attachment;
import com.taskmaster.Taskmaster.entity.Comment;
import com.taskmaster.Taskmaster.entity.Task;
import com.taskmaster.Taskmaster.repository.AttachmentRepository;
import com.taskmaster.Taskmaster.repository.CommentRepository;
import com.taskmaster.Taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;


    public String addAttachment(UUID taskId, String fileUrl, UUID userId) {
        // Check if task exists
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            return "Error";
        }

        // Create comment object
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(UUID.randomUUID());
        attachment.setTaskId(taskId);
        attachment.setUserId(userId);
        attachment.setFileUrl(fileUrl);
        attachment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Save comment to the database
        attachmentRepository.addAttachment(attachment);

        taskRepository.updateTaskUpdatedAt(task.getTask_id());

        return "Success";
    }
}
