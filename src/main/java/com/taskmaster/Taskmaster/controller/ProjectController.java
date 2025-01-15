package com.taskmaster.Taskmaster.controller;

import com.taskmaster.Taskmaster.model.ProjectDto;
import com.taskmaster.Taskmaster.model.UserProjectDto;
import com.taskmaster.Taskmaster.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@Tag(name = "Project", description = "Project related APIs")
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    //User stories (11.1/14)
    //Api to create new project
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> register(@RequestBody ProjectDto projectDto) {
        //return projectService.save(projectDto.getTitle(), projectDto.getDescription());

        try {
            String dbMessage = projectService.save(projectDto.getTitle(), projectDto.getDescription());
            if(dbMessage.equals("Success")) {

                // If dbMessage is Success, return a success response
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "Project created successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }

            // If dbMessage is Error, return an error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Project creation failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            // Handle any exceptions and return a generic error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Project creation error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //User stories (11.2/14)
    //Api to add new members(users) to existing project
    @PostMapping("/addUser")
    public ResponseEntity<Map<String, String>> addToProject(@RequestBody UserProjectDto userProjectDto) {
        try {
            String dbMessage = projectService.addUserToProjectByUsername(userProjectDto.getUsername(), userProjectDto.getProject_title());
            //return result;
            if(dbMessage.equals("Success")) {
                // If dbMessage is Success, return a success response
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "User added to project successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }

            // If dbMessage is Error, return an error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error in adding user to project");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            // Handle any exceptions and return a generic error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }


    }

}
