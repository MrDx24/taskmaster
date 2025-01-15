package com.taskmaster.Taskmaster.service;

import com.taskmaster.Taskmaster.entity.Project;
import com.taskmaster.Taskmaster.entity.Task;
import com.taskmaster.Taskmaster.repository.ProjectRepository;
import com.taskmaster.Taskmaster.repository.TaskRepository;
import com.taskmaster.Taskmaster.repository.UserRepository;
import com.taskmaster.Taskmaster.repository.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;



    public String createTask(String title, String description, String dueDate, UUID projectId, String assignedToUsername) {
        // Validate project exists
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Validate assigned user exists
        UUID assignedTo = userRepository.getUserIdByUsername(assignedToUsername);
        if (assignedTo == null) {
            throw new IllegalArgumentException("Assigned user not found");
        }

        UUID createdBy = userService.getUserIdFromJWT();  // Current logged-in user

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dueDate, formatter);

        // Create the task object
        Task task = new Task();
        task.setTask_id(UUID.randomUUID());
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus("OPEN");  // Default status
        task.setDueDate(Date.valueOf(localDate));
        task.setProject_id(projectId);
        task.setCreatedBy(createdBy);
        task.setAssignedTo(assignedTo);
        task.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        // Save the task in the database
        Task savedTask = taskRepository.createTask(task);

        return (savedTask!=null) ? "Success":"Error";
    }


    public List<Task> getTasksAssignedToUser() {
        UUID userId = userService.getUserIdFromJWT();  // Get the user ID from JWT
        return taskRepository.findByAssignedTo(userId);
    }

    public List<Task> searchTasks(String title, String description) {

        List<Task> tasks = taskDao.searchTasks(title, description);
        System.out.println("List of tasks by search : " + tasks);
        return tasks;
    }

    public boolean updateTaskStatus(UUID taskId, String status) {
        Task task = taskRepository.findById(taskId);

        if (task != null) {
            task.setStatus(status);
            task.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis())); // Update the updatedAt column with the current date/time
            int result = taskRepository.updateTaskStatus(task.getTask_id(), task.getStatus());; // Save the updated task
            return result == 1;
        }

        return false; // Return false if the task was not found

    }

}
