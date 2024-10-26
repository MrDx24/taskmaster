package com.taskmaster.Taskmaster.filter;

import com.taskmaster.Taskmaster.entity.Task;

import java.util.List;


//To implement strategy pattern for user stories (8/14)
public interface TaskFilterStrategy {
    List<Task> filter(List<Task> tasks);
}
