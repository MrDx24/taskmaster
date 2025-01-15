package com.taskmaster.Taskmaster.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class JwtBlackList {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean isTokenBlacklisted(String token) {
        String sql = "SELECT COUNT(*) FROM token_blacklist WHERE token = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{token}, Integer.class);
        return count != null && count > 0;
    }

    public void blacklistToken(String token, Timestamp expirationTime) {
        String sql = "INSERT INTO token_blacklist (token, expiration_time) VALUES (?, ?)";
        jdbcTemplate.update(sql, token, expirationTime);
    }
}
