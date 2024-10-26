package com.taskmaster.Taskmaster.repository.dao;

import com.taskmaster.Taskmaster.entity.Task;

import java.util.List;

public interface TaskDao {
    List<Task> searchTasks(String title, String description);
}
