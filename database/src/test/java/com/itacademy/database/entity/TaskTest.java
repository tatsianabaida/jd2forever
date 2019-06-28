package com.itacademy.database.entity;

import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static com.itacademy.database.testdata.TestDataGenerator.createTask;
import static org.junit.Assert.assertNotNull;

public class TaskTest {

    private static SessionFactory factory = SessionManager.getFactory();

    @Before
    public void cleanTable() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkSaveTask() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Task task = createTask();
        session.save(task.getCourse().getProfessor());
        session.save(task.getCourse());
        Serializable taskId = session.save(task);
        session.getTransaction().commit();
        assertNotNull(taskId);
    }

    @Test
    public void checkGetTask() {
        @Cleanup Session session = factory.openSession();
        session.beginTransaction();
        Task task = createTask();
        session.save(task.getCourse().getProfessor());
        session.save(task.getCourse());
        Serializable taskId = session.save(task);
        session.evict(task);
        Task taskFromDb = session.get(Task.class, taskId);
        session.getTransaction().commit();
        assertNotNull(taskFromDb);
    }
}