package com.taskmaster.Taskmaster.helper;

import com.taskmaster.Taskmaster.entity.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setTask_id((UUID) rs.getObject("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setStatus(rs.getString("status"));
            task.setDueDate(rs.getDate("due_date")); // Adjust this line based on how you handle dates
            task.setProject_id((UUID) rs.getObject("project_id"));
            task.setCreatedBy((UUID) rs.getObject("created_by"));
            task.setAssignedTo((UUID) rs.getObject("assigned_to"));
            return task;
        }
    }
