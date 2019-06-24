package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static com.itacademy.database.testdata.TestDataGenerator.createDefaultUser;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Professor ").executeUpdate();
        session.createQuery("delete from Student ").executeUpdate();
        session.createQuery("delete from User ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveUser() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable id = session.save(createDefaultUser());
        session.getTransaction().commit();
        assertNotNull(id);
    }

    @Test
    public void checkGetUser() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable id = session.save(createDefaultUser());
        session.clear();
        User akulov = session.get(User.class, id);
        session.getTransaction().commit();
        assertNotNull(akulov);
    }
}