package com.itacademy.database.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createHomework;
import static org.junit.Assert.assertNotNull;

public class HomeworkTest extends EntityTest {

    @Test
    public void checkSaveHomework() {
        Session session = sessionFactory.getCurrentSession();
        Homework homework = createHomework();
        Course course = homework.getId().getTask().getCourse();

        session.save(homework.getId().getStudent());
        session.save(course.getProfessor());
        session.save(course);
        session.save(homework.getId().getTask());
        Serializable homeworkId = session.save(homework);
        assertNotNull(homeworkId);
    }

    @Test
    public void checkGetHomework() {
        Session session = sessionFactory.getCurrentSession();
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