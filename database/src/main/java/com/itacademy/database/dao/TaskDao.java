package com.itacademy.database.dao;

import com.itacademy.database.entity.Task;
import com.itacademy.database.filter.TaskFilter;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao extends BaseDaoFilterable<Long, Task, TaskFilter> {
}
