package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveCourse() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        Professor professor = new Professor(
                Person.builder()
                        .firstName("Dzianis")
                        .lastName("Matveyenka")
                        .build(),
                Person.builder()
                        .firstName("John")
                        .lastName("Snow")
                        .build(),
                "matveyenka@macademy.com",
                "matveyenka",
                Role.ADMIN, "Industrial software development on Java",
                "Game Of Thrones, Big Data, SQL, neural networks, algorithms and data structures",
                Short.valueOf("7"));
        Course course = Course.builder()
                .name("JD1")
                .professor(professor)
                .build();
        session.save(professor);
        Serializable courseId = session.save(course);
        session.getTransaction().commit();
        assertNotNull(courseId);
    }

    @Test
    public void checkGetCourse() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();

        Professor professor = new Professor(
                Person.builder()
                        .firstName("Dzianis")
                        .lastName("Matveyenka")
                        .build(),
                Person.builder()
                        .firstName("John")
                        .lastName("Snow")
                        .build(),
                "matveyenka@macademy.com",
                "matveyenka",
                Role.ADMIN, "Industrial software development on Java",
                "Game Of Thrones, Big Data, SQL, neural networks, algorithms and data structures",
                Short.valueOf("7"));
        Student student1 = new Student(
                Person.builder()
                        .firstName("Artem")
                        .lastName("Bobckevich")
                        .build(),
                null,
                "bobckevich@macademy.com",
                "bobckevich",
                Role.USER,
                "unknown",
                "sysadmin");
        Student student2 = new Student(
                Person.builder()
                        .firstName("Artem2")
                        .lastName("Bobckevich")
                        .build(),
                null,
                "bobckevich@macademy.com",
                "bobckevich",
                Role.USER,
                "unknown",
                "sysadmin");
        session.save(student1);
        session.save(student2);

        Course course = Course.builder()
                .name("JD1")
                .professor(professor)
                .students(new HashSet<>(Arrays.asList(student1, student2)))
                .build();
        session.save(professor);
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