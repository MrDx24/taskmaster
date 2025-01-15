package com.taskmaster.Taskmaster.controller;


import com.taskmaster.Taskmaster.entity.Attachment;
import com.taskmaster.Taskmaster.model.AttachmentDto;
import com.taskmaster.Taskmaster.model.CommentDto;
import com.taskmaster.Taskmaster.service.AttachmentService;
import com.taskmaster.Taskmaster.service.CommentService;
import com.taskmaster.Taskmaster.service.UserService;
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
@Tag(name = "Attachment", description = "Attachment related APIs")
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    //User stories (10.2/14)
    //Api to add attachment to task
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addComment(@RequestBody AttachmentDto attachmentDto) {
        Map<String, Object> response = new HashMap<>();
        UUID userId = userService.getUserIdFromJWT();  // Retrieve logged-in user's ID

        try {
            // Call service to add the comment
            attachmentService.addAttachment(attachmentDto.getTaskId(), attachmentDto.getFileUrl(), userId);

            response.put("message", "Attachment added successfully");
            response.put("status", HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error while adding attachment: " + e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
