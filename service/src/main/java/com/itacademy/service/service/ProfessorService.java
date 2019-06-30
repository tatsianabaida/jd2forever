package com.itacademy.service.service;

import com.itacademy.database.dao.ProfessorDao;
import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.filter.ProfessorFilter;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessorService {

    private final ProfessorDao professorDao;
    private final SessionFactory sessionFactory;

    public List<Professor> getAll(ProfessorFilterDto professorFilterDto) {
        return professorDao.getAll(ProfessorFilter.builder(sessionFactory)
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

    public Set<String> getSpecialities() {
        return professorDao.getSpecialities();
    }
}
