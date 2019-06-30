package com.itacademy.database.entity;

import com.itacademy.database.config.DatabaseConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@Transactional
public abstract class EntityTest {

    @Autowired
    public SessionFactory sessionFactory;

    @Before
    public void cleanTable() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Professor ").executeUpdate();
        session.createQuery("delete from Student ").executeUpdate();
        session.createQuery("delete from User ").executeUpdate();
    }
}
