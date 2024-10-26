package com.taskmaster.Taskmaster.service;

import com.taskmaster.Taskmaster.entity.User;
import com.taskmaster.Taskmaster.model.UserProfileDto;
import com.taskmaster.Taskmaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // Method to create a new user with a hashed password
    public String save(String username, String email, String password, String profilePicture) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // Hashing the password
        user.setProfilePicture(profilePicture);
        user.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        // Save the new user in the database
        User savedUser = userRepository.createUser(user);
        return (savedUser!=null)? "Success" : "Error";
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER") // Set your authorities
                .build();
    }

    public UUID getUserIdFromJWT() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Use the custom JDBC method to get the User entity by username
        com.taskmaster.Taskmaster.entity.User user = userRepository.getUserEntityByUsername(username);
        return user.getId();
    }

    public User getUserProfile(UUID userId) {
        User user = userRepository.getUserProfile(userId);
        return user;
    }

    public String updateUserProfile(UUID userId, UserProfileDto userProfileDto) {
        int result = userRepository.updateUserProfile(userId, userProfileDto);
        return (result==1) ? "Success":"Error";
    }
}
