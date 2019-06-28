package com.itacademy.database.filter;

public interface Builder<T extends Filter> {

    Integer DEFAULT_LIMIT = 100;
    Integer DEFAULT_OFFSET = 0;

    T build();

    Builder<T> limit(Integer limit);

    Builder<T> offset(Integer offset);
}
