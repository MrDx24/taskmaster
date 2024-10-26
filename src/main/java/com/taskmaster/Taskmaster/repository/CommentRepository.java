package com.taskmaster.Taskmaster.repository;

import com.taskmaster.Taskmaster.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addComment(Comment comment) {
        String sql = "INSERT INTO comments (comment_id, task_id, user_id, comment_text, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                comment.getId(),
                comment.getTaskId(),
                comment.getUserId(),
                comment.getCommentText(),
                comment.getCreatedAt());
    }

}
