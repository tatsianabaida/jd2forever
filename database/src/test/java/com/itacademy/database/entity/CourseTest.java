package com.itacademy.database.entity;

import java.io.Serializable;
import java.util.Arrays;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGeneratorDatabase.createCourse;
import static com.itacademy.database.testdata.TestDataGeneratorDatabase.createStudent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseTest extends EntityTest {

    @Test
    public void checkSaveCourse() {
        Session session = sessionFactory.getCurrentSession();
        Course course = createCourse();
        session.save(course.getProfessor());
        Serializable courseId = session.save(course);
        assertNotNull(courseId);
    }

    @Test
    public void checkGetCourse() {
        Session session = sessionFactory.getCurrentSession();
        Course course = createCourse();
        Student student1 = createStudent();
        Student student2 = createStudent();

        session.save(course.getProfessor());
        session.save(student1);
        session.save(student2);
        course.getStudents().addAll(Arrays.asList(student1, student2));
        Serializable courseId = session.save(course);

        session.flush();
        session.clear();
        Course courseFromDb = session.get(Course.class, courseId);
        int studentAmount = courseFromDb.getStudents().size();

        assertNotNull(courseFromDb);
        assertEquals(2, studentAmount);
    }
}