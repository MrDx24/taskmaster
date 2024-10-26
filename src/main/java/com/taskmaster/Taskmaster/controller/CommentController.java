package com.taskmaster.Taskmaster.controller;


import com.taskmaster.Taskmaster.model.CommentDto;
import com.taskmaster.Taskmaster.service.CommentService;
import com.taskmaster.Taskmaster.service.TaskService;
import com.taskmaster.Taskmaster.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.UUID;

@RestController
@Tag(name = "Comment", description = "Comment APIs")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    //User stories (10.1/14)
    //Api to add comment to task
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addComment(@RequestBody CommentDto commentDTO) {
        Map<String, Object> response = new HashMap<>();
        UUID userId = userService.getUserIdFromJWT();  // Retrieve logged-in user's ID

        try {
            // Call service to add the comment
            commentService.addComment(commentDTO.getTaskId(), commentDTO.getCommentText(), userId);

            response.put("message", "Comment added successfully");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error while adding comment: " + e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
