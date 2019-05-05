package by.itacademy.service.service;

import by.itacademy.database.dao.StudentDao;
import by.itacademy.database.entity.Student;

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
