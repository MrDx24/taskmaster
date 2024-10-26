package com.taskmaster.Taskmaster.helper;

import com.taskmaster.Taskmaster.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId((UUID) rs.getObject("id")); // Assuming you have a UUID column
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setProfilePicture(rs.getString("profile_picture"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}