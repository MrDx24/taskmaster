package com.taskmaster.Taskmaster.service;

import com.taskmaster.Taskmaster.entity.Comment;
import com.taskmaster.Taskmaster.entity.Task;
import com.taskmaster.Taskmaster.repository.CommentRepository;
import com.taskmaster.Taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String addComment(UUID taskId, String commentText, UUID userId) {
        // Check if task exists
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            return "Error";
        }

        // Create comment object
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setTaskId(taskId);
        comment.setUserId(userId);
        comment.setCommentText(commentText);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Save comment to the database
        commentRepository.addComment(comment);

        taskRepository.updateTaskUpdatedAt(task.getTask_id());

        return "Success";
    }

}
