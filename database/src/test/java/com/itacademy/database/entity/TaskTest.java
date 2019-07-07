package com.itacademy.database.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGeneratorDatabase.createTask;
import static org.junit.Assert.assertNotNull;

public class TaskTest extends EntityTest {

    @Test
    public void checkSaveTask() {
        Session session = sessionFactory.getCurrentSession();
        Task task = createTask();
        session.save(task.getCourse().getProfessor());
        session.save(task.getCourse());
        Serializable taskId = session.save(task);
        assertNotNull(taskId);
    }

    @Test
    public void checkGetTask() {
        Session session = sessionFactory.getCurrentSession();
        Task task = createTask();
        session.save(task.getCourse().getProfessor());
        session.save(task.getCourse());
        Serializable taskId = session.save(task);
        session.evict(task);
        Task taskFromDb = session.get(Task.class, taskId);
        assertNotNull(taskFromDb);
    }
}