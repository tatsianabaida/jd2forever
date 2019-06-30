package com.itacademy.service.service;

import com.itacademy.database.ProfessorTestDataImporter;
import com.itacademy.service.config.ServiceConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Transactional
public abstract class ServiceTest {


    @Autowired
    public ProfessorService professorService;
    @Autowired
    public ProfessorTestDataImporter professorTestDataImporter;
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
