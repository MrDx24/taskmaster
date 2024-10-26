package com.taskmaster.Taskmaster.repository;

import com.taskmaster.Taskmaster.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Method to create new task
    public Task createTask(Task task) {
        String sql = "INSERT INTO tasks (task_id, title, description, status, due_date, project_id, created_by, assigned_to, created_at) VALUES (?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql, task.getTask_id(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDueDate(), task.getProject_id(), task.getCreatedBy(), task.getAssignedTo(), task.getCreatedAt());
        return task;  // Return the saved task object
    }

    // Method to find what task are assigned to user
    public List<Task> findByAssignedTo(UUID assignedTo) {
        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";
        return jdbcTemplate.query(sql, new Object[]{assignedTo}, new BeanPropertyRowMapper<>(Task.class));
    }

    // Method to find task by ID
    public Task findById(UUID taskId) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, new BeanPropertyRowMapper<>(Task.class));
    }

    // Method to update task status and updatedAt
    public int updateTaskStatus(UUID taskId, String status) {
        String sql = "UPDATE tasks SET status = ?, updated_at = ? WHERE task_id = ?";
        return jdbcTemplate.update(sql, status, new java.sql.Timestamp(System.currentTimeMillis()), taskId);
    }

    // Method to update task updatedAt (for when new comments and attachments are added to the task)
    public int updateTaskUpdatedAt(UUID taskId) {
        String sql = "UPDATE tasks SET updated_at = ? WHERE task_id = ?";
        return jdbcTemplate.update(sql, new java.sql.Timestamp(System.currentTimeMillis()), taskId);
    }



}
