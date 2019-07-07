package com.itacademy.database.dao;

import com.itacademy.database.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import static com.itacademy.database.entity.QUser.user;

@Repository
public class UserDao extends BaseDao<Long, User> {

    public User findByUsername(String email) {
        return new JPAQuery<User>(getSessionFactory().getCurrentSession())
                .select(user)
                .from(user)
                .where(user.email.eq(email.trim()))
                .fetch().stream()
                .findFirst()
                .orElse(null);
    }

    public boolean emailExists(String email) {
        return !new JPAQuery<String>(getSessionFactory().getCurrentSession())
                .select(user.email)
                .from(user)
                .where(user.email.eq(email.trim()))
                .fetch().isEmpty();
    }
}
