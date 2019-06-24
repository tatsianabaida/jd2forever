package com.itacademy.database.dao;

import com.itacademy.database.entity.Course;

public class CourseDao implements BaseDao<Long, Course> {

    private static final CourseDao INSTANCE = new CourseDao();

    public static CourseDao getInstance() {
        return INSTANCE;
    }
}
