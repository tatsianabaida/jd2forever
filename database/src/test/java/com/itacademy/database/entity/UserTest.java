package com.itacademy.database.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createDefaultUser;
import static org.junit.Assert.assertNotNull;

public class UserTest extends EntityTest {

    @Test
    public void checkSaveUser() {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(createDefaultUser());
        assertNotNull(id);
    }

    @Test
    public void checkGetUser() {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(createDefaultUser());
        session.clear();
        User akulov = session.get(User.class, id);
        assertNotNull(akulov);
    }
}