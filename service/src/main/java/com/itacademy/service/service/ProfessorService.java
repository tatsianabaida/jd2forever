package com.itacademy.service.service;

import com.itacademy.database.dao.ProfessorDao;
import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.filter.ProfessorFilter;

import java.util.List;

public class ProfessorService {

    private static final ProfessorService INSTANCE = new ProfessorService();
    private final ProfessorDao professorDao = ProfessorDao.getInstance();

    public List<Professor> getAll(ProfessorFilterDto professorFilterDto) {
        return professorDao.getAll(ProfessorFilter.builder()
                .firstName(professorFilterDto.getFirstName())
                .lastName(professorFilterDto.getLastName())
                .speciality(professorFilterDto.getSpeciality())
                .email(professorFilterDto.getEmail())
                .limit(professorFilterDto.getLimit())
                .offset(professorFilterDto.getOffset())
                .build());
    }

    public List<Professor> findAll() {
        return professorDao.findAll();
    }

    public static ProfessorService getInstance() {
        return INSTANCE;
    }
}
