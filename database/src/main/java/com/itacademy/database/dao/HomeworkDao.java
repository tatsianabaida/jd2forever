package com.itacademy.database.dao;

import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.HomeworkId;
import com.itacademy.database.filter.HomeworkFilter;

public class HomeworkDao implements BaseDao<HomeworkId, Homework>, Filterable<Homework, HomeworkFilter> {

    private static final HomeworkDao INSTANCE = new HomeworkDao();

    public static HomeworkDao getInstance() {
        return INSTANCE;
    }
}
