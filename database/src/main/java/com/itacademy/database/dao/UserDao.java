package com.itacademy.database.dao;

import com.itacademy.database.entity.User;

public class UserDao implements BaseDao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
