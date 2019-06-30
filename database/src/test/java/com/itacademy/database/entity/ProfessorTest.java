package com.itacademy.database.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createProfessor;
import static org.junit.Assert.assertNotNull;

public class ProfessorTest extends EntityTest {

    @Test
    public void checkSaveProfessor() {
        Session session = sessionFactory.getCurrentSession();
        Serializable matveyenkaId = session.save(createProfessor());
        assertNotNull(matveyenkaId);
    }

    @Test
    public void checkGetProfessor() {
        Session session = sessionFactory.getCurrentSession();
        Serializable matveyenkaId = session.save(createProfessor());
        session.clear();
        User matveyenka = session.get(Professor.class, matveyenkaId);
        assertNotNull(matveyenka);
    }

}