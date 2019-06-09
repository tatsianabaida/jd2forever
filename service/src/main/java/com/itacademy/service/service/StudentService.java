package com.itacademy.service.service;

import com.itacademy.database.dao.StudentDao;
import com.itacademy.database.entity.Student;

import java.util.List;

public class StudentService {

    private static final StudentService INSTANCE = new StudentService();
    private final StudentDao studentDao = StudentDao.getInstance();

    public List<Student> getAll() {
        return studentDao.findAll();
    }

    public static StudentService getInstance() {
        return INSTANCE;
    }
}
