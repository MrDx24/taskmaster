package com.taskmaster.Taskmaster.controller;

import com.taskmaster.Taskmaster.entity.Task;
import com.taskmaster.Taskmaster.filter.CompletedTasksFilter;
import com.taskmaster.Taskmaster.filter.InProgressTasksFilter;
import com.taskmaster.Taskmaster.filter.OpenTasksFilter;
import com.taskmaster.Taskmaster.filter.TaskFilterContext;
import com.taskmaster.Taskmaster.model.TaskDto;
import com.taskmaster.Taskmaster.model.TaskStatusDto;
import com.taskmaster.Taskmaster.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Task", description = "Task related APIs")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //User stories (4/14) and (7/14)
    //Api to create tasks and assigned it to other user
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createTask(@RequestBody TaskDto taskDto) {
        try {
            String dbMessage = taskService.createTask(
                    taskDto.getTitle(),
                    taskDto.getDescription(),
                    taskDto.getDueDate(),
                    taskDto.getProjectId(),
                    taskDto.getAssignedTo()
            );

            if (dbMessage.equals("Success")) {
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "Task created successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Task creation failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Task creation error" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    //User stories (5/14)
    //Api to view all assigned tasks
    @GetMapping("/assigned")
    public ResponseEntity<Map<String, Object>> getAssignedTasks() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Task> tasks = taskService.getTasksAssignedToUser();

            if (!tasks.isEmpty()) {
                response.put("tasks", tasks);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "No tasks assigned");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred while fetching tasks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    //User stories (6/14)
    //Api to update tasks status
    @PutMapping("/status")
    public ResponseEntity<Map<String, Object>> updateTaskStatus(@RequestBody TaskStatusDto taskStatusDto) {
        Map<String, Object> response = new HashMap<>();
        try {

            boolean isUpdated = taskService.updateTaskStatus(taskStatusDto.getTaskId(), taskStatusDto.getStatus());
            if(isUpdated) {
                response.put("message", "Task status updated successfully");
                return ResponseEntity.ok(response);
            }

            response.put("error", "Task update failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            response.put("error", "Task updation failed" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //User stories (8/14)
    //Api to filter tasks based on status
    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterTasks(@RequestParam String status) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Task> allTasks = taskService.getTasksAssignedToUser();
            TaskFilterContext context = new TaskFilterContext();

            // Set the appropriate strategy based on the status
            switch (status.toUpperCase()) {
                case "OPEN":
                    context.setStrategy(new OpenTasksFilter());
                    break;
                case "IN_PROGRESS":
                    context.setStrategy(new InProgressTasksFilter());
                    break;
                case "COMPLETED":
                    context.setStrategy(new CompletedTasksFilter());
                    break;
                default:
                    response.put("message", "Invalid status filter. Use 'OPEN' or 'COMPLETED'.");
                    return ResponseEntity.badRequest().body(response);
            }

            List<Task> filteredTasks = context.filterTasks(allTasks);
            response.put("tasks", filteredTasks);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "An error occurred while filtering tasks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    //User stories (9/14)
    //Api to search tasks based on title or description
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTasks(@RequestParam(required = false) String title, @RequestParam(required = false) String description) {

        Map<String, Object> response = new HashMap<>();
        try {
            List<Task> tasks = taskService.searchTasks(title, description);
            if (tasks.isEmpty()) {
                response.put("message", "No tasks found");
            } else {
                response.put("tasks", tasks);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", "Search failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }


    }



}