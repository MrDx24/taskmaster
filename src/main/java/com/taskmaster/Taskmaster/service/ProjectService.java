package com.taskmaster.Taskmaster.service;

import com.taskmaster.Taskmaster.entity.Project;
import com.taskmaster.Taskmaster.entity.User;
import com.taskmaster.Taskmaster.repository.ProjectRepository;
import com.taskmaster.Taskmaster.repository.UserRepository;
import com.taskmaster.Taskmaster.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;



    // Method to create a new project
    public String save(String project_title, String project_description) {

        UUID created_by = userService.getUserIdFromJWT();  // This calls the getUserIdFromJWT() in UserService

        Project project = new Project();
        project.setProject_id(UUID.randomUUID());
        project.setTitle(project_title);
        project.setDescription(project_description);
        project.setCreated_by(created_by);
        project.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));

        // Save the project in the database
        Project savedProject = projectRepository.createProject(project);

        if (savedProject != null) {
            // Now insert into the user_project table
            projectRepository.addUserToProject(created_by, savedProject.getProject_id()); // Assuming createProject returns savedProject with project_id
            return "Success";
        }

        return "Error";

    }

    public String addUserToProjectByUsername(String username, String project_title) {

        UUID userId = userRepository.getUserIdByUsername(username);
        UUID projectId = projectRepository.getProjectIdByName(project_title);

        if (userId == null || projectId == null) {
            return "Error";
        }

        projectRepository.addUserToProject(userId, projectId);
        return "Success";

    }
}
