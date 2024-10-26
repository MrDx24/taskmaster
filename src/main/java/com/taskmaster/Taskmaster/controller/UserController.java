package com.taskmaster.Taskmaster.controller;

import com.taskmaster.Taskmaster.entity.User;
import com.taskmaster.Taskmaster.model.LoginDto;
import com.taskmaster.Taskmaster.model.RegisterDto;
import com.taskmaster.Taskmaster.model.UserProfileDto;
import com.taskmaster.Taskmaster.service.JwtService;
import com.taskmaster.Taskmaster.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Tag(name = "User", description = "User related APIs")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;



    @Autowired
    private AuthenticationManager authenticationManager;

    //User stories (1/14)
    //Api to register i.e., create a new account
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        //return userService.save(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword(), registerDto.getProfilePicture());
        try {
            // Call the service to save the new user
            String result = userService.save(
                    registerDto.getUsername(),
                    registerDto.getEmail(),
                    registerDto.getPassword(),
                    registerDto.getProfilePicture()
            );

            // If the result is success, return a success response
            if (result.equals("Success")) {
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "User registered successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }

            // If result is Error, return an error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            // Handle any exceptions and return a generic error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //User stories (2/14)
    //Api to validate login credentials and return JWT Token
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));

            if(authentication.isAuthenticated()) {
                //return jwtService.generateToken(userService.loadUserByUsername(loginDto.getUsername()));
                String token = jwtService.generateToken(userService.loadUserByUsername(loginDto.getUsername()));

                // Create a success response with the token
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
        } catch (AuthenticationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Create an error response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Login Failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    //User stories (3.1/14)
    //Api to view profile
    @GetMapping("/profile/view")
    public ResponseEntity<Map<String, Object>> viewUserProfile() {
        UUID userId = userService.getUserIdFromJWT();
        User user = userService.getUserProfile(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePicture", user.getProfilePicture());
        response.put("createdAt", user.getCreatedAt());

        return ResponseEntity.ok(response);
    }

    //User stories (3.2/14)
    //Api to update user profile
    @PutMapping("/profile/update")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        UUID userId = userService.getUserIdFromJWT();
        String dbMessage = userService.updateUserProfile(userId, userProfileDto);
        if (dbMessage.equals("Success")) {
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to update profile"));
        }
    }


    //User stories (12/14)
    //Api for the user to logout
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);  // Assuming Bearer Token in the Authorization header
        Date expiration = jwtService.extractExpiration(token);

        // Blacklist the token
        jwtService.blacklistToken(token, new Timestamp(expiration.getTime()));

        // Return success response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
