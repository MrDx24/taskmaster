package com.taskmaster.Taskmaster.repository;


import com.taskmaster.Taskmaster.entity.Attachment;
import com.taskmaster.Taskmaster.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addAttachment(Attachment attachment) {
        String sql = "INSERT INTO attachments (attachment_id, task_id, user_id, file_url, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                attachment.getAttachmentId(),
                attachment.getTaskId(),
                attachment.getUserId(),
                attachment.getFileUrl(),
                attachment.getCreatedAt());
    }
}
