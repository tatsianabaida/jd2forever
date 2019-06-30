package com.itacademy.database.dao;

import com.itacademy.database.entity.Professor;
import com.itacademy.database.filter.ProfessorFilter;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;

import static com.itacademy.database.entity.QProfessor.professor;

@Repository
public class ProfessorDao extends BaseDaoFilterable<Long, Professor, ProfessorFilter> {

    public Set<String> getSpecialities() {
        return new HashSet<>(new JPAQuery<String>(getSessionFactory().getCurrentSession())
                .select(professor.speciality)
                .from(professor)
                .fetch());
    }
}
