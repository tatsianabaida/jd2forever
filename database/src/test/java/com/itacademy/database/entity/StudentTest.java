package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static com.itacademy.database.testdata.TestDataGenerator.createStudent;
import static org.junit.Assert.assertNotNull;

public class StudentTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Student ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveStudent() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable akulovId = session.save(createStudent());
        session.getTransaction().commit();
        assertNotNull(akulovId);
    }

    @Test
    public void checkGetStudent() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable akulovId = session.save(createStudent());
        session.clear();
        User akulov = session.get(Student.class, akulovId);
        session.getTransaction().commit();
        assertNotNull(akulov);
    }
}