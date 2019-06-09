package com.itacademy.database.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Test
    public void checkSaveUser() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        User akulov = User.builder()
                .person(Person.builder()
                        .firstName("Alexander")
                        .lastName("Akulov")
                        .build())
                .imaginaryPerson(Person.builder()
                        .firstName(null)
                        .lastName("Bond")
                        .build())
                .email("akulov@macademy.com")
                .password("akulov")
                .role(Role.USER)
                .build();
        Serializable id = session.save(akulov);
        session.getTransaction().commit();
        assertNotNull(id);
    }

    @Test
    public void checkGetUser() {
        @Cleanup Session session = FACTORY.openSession();
        session.beginTransaction();
        Serializable id = session.save(User.builder()
                .person(Person.builder()
                        .firstName("Alexander")
                        .lastName("Akulov")
                        .build())
                .imaginaryPerson(Person.builder()
                        .firstName("James")
                        .lastName(null)
                        .build())
                .email("akulov@macademy.com")
                .password("akulov")
                .role(Role.USER)
                .build());
        session.clear();
        User akulov = session.get(User.class, id);
        session.getTransaction().commit();
        assertNotNull(akulov);
    }
}