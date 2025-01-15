package com.taskmaster.Taskmaster.filter;

import com.taskmaster.Taskmaster.entity.Task;

import java.util.List;

public class TaskFilterContext {

    private TaskFilterStrategy strategy;

    public void setStrategy(TaskFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Task> filterTasks(List<Task> tasks) {
        return strategy.filter(tasks);
    }

}
