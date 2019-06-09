package com.itacademy.database.dao;

import com.itacademy.database.entity.Student;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();

    public List<Student> findAll() {
        @Cleanup SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        Query<Student> query = session.createQuery("select e from Student e", Student.class);
        return query.list();
    }

    public static StudentDao getInstance() {
        return INSTANCE;
    }
}
