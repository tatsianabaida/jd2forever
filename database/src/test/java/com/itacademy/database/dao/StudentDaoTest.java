package com.itacademy.database.dao;

import com.itacademy.database.entity.Course;
import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.Mark;
import com.itacademy.database.entity.Student;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createHomework;
import static org.junit.Assert.assertEquals;

public class StudentDaoTest extends DaoTest {

    @Test
    public void getAverageMark() {
        Homework homework1 = createHomework();
        Student student = homework1.getId().getStudent();
        Course course = homework1.getId().getTask().getCourse();
        Homework homework2 = createHomework().setMark(Mark.ONE);
        Homework homework3 = createHomework().setMark(Mark.THREE);
        Homework homework4 = createHomework().setMark(Mark.FOUR);
        Homework homework5 = createHomework().setMark(null);

        homework2.getId().setStudent(student);
        homework3.getId().setStudent(student);
        homework4.getId().setStudent(student);
        homework5.getId().setStudent(student);
        homework2.getId().getTask().setCourse(course);
        homework3.getId().getTask().setCourse(course);
        homework4.getId().getTask().setCourse(course);
        homework5.getId().getTask().setCourse(course);

        studentDao.save(student);
        professorDao.save(course.getProfessor());
        courseDao.save(course);
        taskDao.save(homework1.getId().getTask());
        taskDao.save(homework2.getId().getTask());
        taskDao.save(homework3.getId().getTask());
        taskDao.save(homework4.getId().getTask());
        taskDao.save(homework5.getId().getTask());
        homeworkDao.save(homework1);
        homeworkDao.save(homework2);
        homeworkDao.save(homework3);
        homeworkDao.save(homework4);
        homeworkDao.save(homework5);

        Double averageMark = studentDao.getAverageMark(student.getId());
        assertEquals(3.25, averageMark, 0);
    }
}