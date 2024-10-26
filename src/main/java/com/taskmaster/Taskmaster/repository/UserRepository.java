package com.taskmaster.Taskmaster.repository;

import com.taskmaster.Taskmaster.entity.User;
import com.taskmaster.Taskmaster.helper.UserRowMapper;
import com.taskmaster.Taskmaster.model.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public User createUser(User user) {
        String sql = "INSERT INTO users (id, username, email, password, profile_picture, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getProfilePicture(), user.getCreatedAt());
        return user;  // Return the saved user object
    }


    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserRowMapper());
    }

    // Custom method to fetch the User entity using JDBC
    public User getUserEntityByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(query, new Object[]{username}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setProfilePicture(rs.getString("profile_picture"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                return user;
            }
        });
    }


    public UUID getUserIdByUsername(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, UUID.class);
    }

    // Method to get user profile details
    public User getUserProfile(UUID userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserRowMapper());
    }

    // Method to update user profile details
    public int updateUserProfile(UUID userId, UserProfileDto userProfileDto) {
        String sql = "UPDATE users SET email = ?, profile_picture = ? WHERE id = ?";
        return jdbcTemplate.update(sql, userProfileDto.getEmail(), userProfileDto.getProfilePicture(), userId);
    }
}
