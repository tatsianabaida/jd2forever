package com.itacademy.database.dao;

import com.itacademy.database.entity.Professor;
import com.itacademy.database.filter.ProfessorFilter;

public class ProfessorDao implements BaseDao<Long, Professor>, Filterable<Professor, ProfessorFilter> {

    private static final ProfessorDao INSTANCE = new ProfessorDao();

    public static ProfessorDao getInstance() {
        return INSTANCE;
    }
}
