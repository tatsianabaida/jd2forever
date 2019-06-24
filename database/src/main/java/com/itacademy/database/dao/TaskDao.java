package com.itacademy.database.dao;

import com.itacademy.database.entity.Task;
import com.itacademy.database.filter.TaskFilter;

public class TaskDao implements BaseDao<Long, Task>, Filterable<Task, TaskFilter> {

    private static final TaskDao INSTANCE = new TaskDao();

    public static TaskDao getInstance() {
        return INSTANCE;
    }
}
