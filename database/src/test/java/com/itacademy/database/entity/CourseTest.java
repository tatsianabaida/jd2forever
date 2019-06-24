package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;

import static com.itacademy.database.testdata.TestDataGenerator.createCourse;
import static com.itacademy.database.testdata.TestDataGenerator.createStudent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveCourse() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Course course = createCourse();
        session.save(course.getProfessor());
        Serializable courseId = session.save(course);
        session.getTransaction().commit();
        assertNotNull(courseId);
    }

    @Test
    public void checkGetCourse() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
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

        session.getTransaction().commit();
        assertNotNull(courseFromDb);
        assertEquals(2, studentAmount);
    }
}