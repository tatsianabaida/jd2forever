package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static com.itacademy.database.testdata.TestDataGenerator.createProfessor;
import static org.junit.Assert.assertNotNull;

public class ProfessorTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Professor ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveProfessor() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable matveyenkaId = session.save(createProfessor());
        session.getTransaction().commit();
        assertNotNull(matveyenkaId);
    }

    @Test
    public void checkGetProfessor() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Serializable matveyenkaId = session.save(createProfessor());
        session.clear();
        User matveyenka = session.get(Professor.class, matveyenkaId);
        session.getTransaction().commit();
        assertNotNull(matveyenka);
    }

}