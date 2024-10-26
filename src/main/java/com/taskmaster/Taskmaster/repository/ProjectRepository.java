package com.taskmaster.Taskmaster.repository;

import com.taskmaster.Taskmaster.entity.Project;
import com.taskmaster.Taskmaster.entity.User;
import com.taskmaster.Taskmaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Repository
public class ProjectRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;


    public Project createProject(Project project) {
        String sql = "INSERT INTO projects (project_id, title, description, created_by, created_at) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, project.getProject_id(), project.getTitle(), project.getDescription(), project.getCreated_by(), project.getCreated_at());
        return project;  // Return the saved project object
    }

    public void addUserToProject(UUID userId, UUID projectId) {
        String insertUserProjectSQL = "INSERT INTO user_project (user_id, project_id) VALUES (?, ?)";
        jdbcTemplate.update(insertUserProjectSQL, userId, projectId);
    }

//    public String addUserToProjectByUsername(UUID projectId, String username) {
//        // Fetch the user by username
//        User user = userRepository.getUserEntityByUsername(username); // Ensure this method handles exceptions properly
//
//        if (user != null) {
//            addUserToProject(user.getId(), projectId);
//            return "User added to project successfully.";
//        }
//
//        return "User not found.";
//    }

    public UUID getProjectIdByName(String projectName) {
        String query = "SELECT project_id FROM projects WHERE title = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{projectName}, UUID.class);
    }

    // Method to find a project by its ID
    public Project findById(UUID projectId) {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{projectId}, new ProjectRowMapper());
    }

    // RowMapper to map the result set to Project object
    private static class ProjectRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setProject_id(UUID.fromString(rs.getString("project_id")));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setCreated_by(UUID.fromString(rs.getString("created_by")));
            project.setCreated_at(rs.getTimestamp("created_at"));
            return project;
        }
    }
    
}
