package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class StudentTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveStudent() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        User bobckevich = new Student(
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
        Serializable bobckevichId = session.save(bobckevich);
        session.getTransaction().commit();
        assertNotNull(bobckevichId);
    }

    @Test
    public void checkGetStudent() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        Serializable bobckevichId = session.save(new Student(
                Person.builder()
                        .firstName("Artem")
                        .lastName("Bobckevich")
                        .build(),
                null,
                "bobckevich@macademy.com",
                "bobckevich",
                Role.USER,
                "unknown",
                "sysadmin"));
        session.clear();
        User bobckevich = session.get(Student.class, bobckevichId);
        session.getTransaction().commit();
        assertNotNull(bobckevich);
    }
}