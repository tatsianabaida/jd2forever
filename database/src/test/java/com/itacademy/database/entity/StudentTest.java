package com.itacademy.database.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGeneratorDatabase.createStudent;
import static org.junit.Assert.assertNotNull;

public class StudentTest extends EntityTest {

    @Test
    public void checkSaveStudent() {
        Session session = sessionFactory.getCurrentSession();
        Serializable akulovId = session.save(createStudent());
        assertNotNull(akulovId);
    }

    @Test
    public void checkGetStudent() {
        Session session = sessionFactory.getCurrentSession();
        Serializable akulovId = session.save(createStudent());
        session.clear();
        User akulov = session.get(Student.class, akulovId);
        assertNotNull(akulov);
    }
}