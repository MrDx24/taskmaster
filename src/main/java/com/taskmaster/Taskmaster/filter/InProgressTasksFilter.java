package com.taskmaster.Taskmaster.filter;

import com.taskmaster.Taskmaster.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class InProgressTasksFilter implements TaskFilterStrategy {
    @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> "IN_PROGRESS".equals(task.getStatus()))
                .collect(Collectors.toList());
    }
}
