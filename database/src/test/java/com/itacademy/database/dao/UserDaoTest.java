package com.itacademy.database.dao;

import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static com.itacademy.database.testdata.TestDataGenerator.createAdminUser;
import static com.itacademy.database.testdata.TestDataGenerator.createDefaultUser;
import static com.itacademy.database.testdata.TestDataGenerator.createProfessor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserDaoTest {

    private final UserDao userDao = UserDao.getInstance();

    @Before
    public void cleanTable() {
        @Cleanup Session session = SessionManager.getFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Professor ").executeUpdate();
        session.createQuery("delete from Student ").executeUpdate();
        session.createQuery("delete from User ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void checkFindById() {
        User akulov = createDefaultUser();
        userDao.save(akulov);
        assertTrue(userDao.findById(akulov.getId()).isPresent());
    }

    @Test
    public void checkFindAll() {
        userDao.save(createDefaultUser());
        userDao.save(createAdminUser());
        userDao.save(createProfessor());
        List<User> users = userDao.findAll();
        long expectedSize = 3;
        assertEquals(expectedSize, users.size());
    }

    @Test
    public void checkSave() {
        Long userId = userDao.save(createDefaultUser());
        assertNotNull(userId);
    }

    @Test
    public void checkUpdate() {
        User user = createAdminUser();
        userDao.save(user);
        user.setRole(Role.USER);
        userDao.update(user);
        Optional<User> userFromDb = userDao.findById(user.getId());
        assertEquals(Role.USER, userFromDb.map(User::getRole).orElse(null));
    }

    @Test
    public void checkDelete() {
        User user = createAdminUser();

        userDao.save(user);
        Optional<User> userFromDb = userDao.findById(user.getId());
        assertNotEquals(userFromDb, Optional.empty());

        userDao.delete(user);
        userFromDb = userDao.findById(user.getId());
        assertEquals(userFromDb, Optional.empty());

    }
}