package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class TaskTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveTask() {
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
                .professor(professor)
                .name("JD2")
                .build();
        Task task = Task.builder()
                .course(course)
                .subject("Maven")
                .exercise("Check point 1")
                .build();

        session.save(professor);
        session.save(course);
        Serializable taskId = session.save(task);
        session.getTransaction().commit();
        assertNotNull(taskId);
    }

    @Test
    public void checkGetTask() {
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
                .professor(professor)
                .name("JD2")
                .build();
        Task task = Task.builder()
                .course(course)
                .subject("Maven")
                .exercise("Check point 1")
                .build();

        session.save(professor);
        session.save(course);
        Serializable taskId = session.save(task);
        session.evict(task);
        Task taskFromDb = session.get(Task.class, taskId);
        session.getTransaction().commit();
        assertNotNull(taskFromDb);
    }
}