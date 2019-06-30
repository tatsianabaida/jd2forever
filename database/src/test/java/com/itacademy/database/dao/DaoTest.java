package com.itacademy.database.dao;

import com.itacademy.database.entity.EntityTest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DaoTest extends EntityTest {

    @Autowired
    public UserDao userDao;
    @Autowired
    public StudentDao studentDao;
    @Autowired
    public ProfessorDao professorDao;
    @Autowired
    public CourseDao courseDao;
    @Autowired
    public TaskDao taskDao;
    @Autowired
    public HomeworkDao homeworkDao;
}
