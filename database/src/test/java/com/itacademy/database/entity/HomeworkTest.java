package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static com.itacademy.database.testdata.TestDataGenerator.createHomework;
import static org.junit.Assert.assertNotNull;

public class HomeworkTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework h ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveHomework() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Homework homework = createHomework();
        Course course = homework.getId().getTask().getCourse();

        session.save(homework.getId().getStudent());
        session.save(course.getProfessor());
        session.save(course);
        session.save(homework.getId().getTask());
        Serializable homeworkId = session.save(homework);
        session.getTransaction().commit();
        assertNotNull(homeworkId);
    }

    @Test
    public void checkGetHomework() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Homework homework = createHomework();
        Course course = homework.getId().getTask().getCourse();

        session.save(homework.getId().getStudent());
        session.save(course.getProfessor());
        session.save(course);
        session.save(homework.getId().getTask());
        Serializable homeworkId = session.save(homework);
        session.getTransaction().commit();

        session.evict(homework);
        Homework homeworkFromDb = session.get(Homework.class, homeworkId);
        assertNotNull(homeworkFromDb);
    }
}