package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class ProfessorTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveProfessor() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        User matveyenka = new Professor(
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
        Serializable matveyenkaId = session.save(matveyenka);
        session.getTransaction().commit();
        assertNotNull(matveyenkaId);
    }

    @Test
    public void checkGetProfessor() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        Serializable matveyenkaId = session.save(new Professor(
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
                Short.valueOf("7")));
        session.clear();
        User matveyenka = session.get(Professor.class, matveyenkaId);
        session.getTransaction().commit();
        assertNotNull(matveyenka);
    }

}