package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class HomeworkTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveHomework() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();

        Student student = new Student(
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
                .professor(professor)
                .name("JD2")
                .build();
        Task task = Task.builder()
                .course(course)
                .exercise("exercise")
                .subject("subject")
                .build();
        Homework homework = Homework.builder()
                .id(Homework.HomeworkId.builder()
                        .student(student)
                        .task(task)
                        .build())
                .work("work")
                .build();

        session.save(student);
        session.save(professor);
        session.save(course);
        session.save(task);
        Serializable homeworkId = session.save(homework);
        session.getTransaction().commit();
        assertNotNull(homeworkId);
    }

    @Test
    public void checkGetHomework() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();

        Student student = new Student(
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
                .professor(professor)
                .name("JD2")
                .build();
        Task task = Task.builder()
                .course(course)
                .exercise("exercise")
                .subject("subject")
                .build();
        Homework homework = Homework.builder()
                .id(Homework.HomeworkId.builder()
                        .student(student)
                        .task(task)
                        .build())
                .work("work")
                .build();

        session.save(student);
        session.save(professor);
        session.save(course);
        session.save(task);
        Serializable homeworkId = session.save(homework);
        session.getTransaction().commit();
        session.evict(homework);
        Homework homeworkFromDb = session.get(Homework.class, homeworkId);
        assertNotNull(homeworkFromDb);
    }
}