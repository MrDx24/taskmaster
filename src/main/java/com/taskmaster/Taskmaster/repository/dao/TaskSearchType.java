package com.taskmaster.Taskmaster.repository.dao;

import com.taskmaster.Taskmaster.entity.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class TaskSearchType implements TaskDao{

    private JdbcTemplate jdbcTemplate;

    public TaskSearchType(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> searchTasks(String title, String description) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tasks ");
        boolean whereAdded = false; // Track if WHERE has been added

        if (title != null && !title.isEmpty()) {
            sql.append("WHERE title LIKE '%" + title + "%' ");
            whereAdded = true; // Mark that WHERE has been added
            System.out.println(sql);
        }

        if (description != null && !description.isEmpty()) {
            if (whereAdded) {
                sql.append("AND "); // Only add AND if WHERE was already added
            } else {
                sql.append("WHERE "); // Add WHERE for the first condition
            }
            sql.append("description LIKE '%" + description + "%'");
            System.out.println(sql);

        }


        List<Map<String, Object>> tasksMapList = jdbcTemplate.queryForList(sql.toString());
        // Convert the result to List<Task>
        List<Task> tasks = new ArrayList<>();
        for (Map<String, Object> row : tasksMapList) {
            Task task = new Task();
            task.setTask_id((UUID) row.get("task_id"));
            task.setTitle((String) row.get("title"));
            task.setDescription((String) row.get("description"));
            task.setStatus((String) row.get("status"));
            task.setDueDate((Date) row.get("due_date"));
            task.setProject_id((UUID) row.get("project_id"));
            task.setCreatedBy((UUID) row.get("created_by"));
            task.setAssignedTo((UUID) row.get("assigned_to"));
            task.setCreatedAt((Timestamp) row.get("created_at")); // Adjust based on your table structure
            task.setUpdatedAt((Timestamp) row.get("updated_at")); // Adjust based on your table structure
            tasks.add(task);
        }

        // Print the converted result for debugging
        System.out.println("Converted result: " + tasks);
        return tasks;
    }

//    private static class TaskRowMapper implements RowMapper<Task> {
//        @Override
//        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Task task = new Task();
//            task.setTask_id((UUID) rs.getObject("id"));
//            task.setTitle(rs.getString("title"));
//            task.setDescription(rs.getString("description"));
//            task.setStatus(rs.getString("status"));
//            task.setDueDate(rs.getDate("due_date")); // Adjust this line based on how you handle dates
//            task.setProject_id((UUID) rs.getObject("project_id"));
//            task.setCreatedBy((UUID) rs.getObject("created_by"));
//            task.setAssignedTo((UUID) rs.getObject("assigned_to"));
//            return task;
//        }
//    }
}
