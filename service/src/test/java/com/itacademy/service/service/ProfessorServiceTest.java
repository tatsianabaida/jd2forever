package com.itacademy.service.service;

import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.entity.Professor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfessorServiceTest extends ServiceTest {

    @Test
    public void getAll() {
        professorTestDataImporter.importTestData();
        List<Professor> allJava = professorService.getAll(ProfessorFilterDto.builder()
                .firstName(null)
                .lastName(null)
                .email(null)
                .speciality("Java")
                .offset(1)
                .limit(3)
                .build());

        int expectedAllJava = 3;
        assertEquals(expectedAllJava, allJava.size());
    }

    @Test
    public void findAll() {
        professorTestDataImporter.importTestData();
        List<Professor> all = professorService.findAll();

        int expectedAll = 11;
        assertEquals(expectedAll, all.size());
    }

    @Test
    public void getSpecialities() {
        professorTestDataImporter.importTestData();
        Set<String> expectedSpecialities = professorService.findAll().stream()
                .map(Professor::getSpeciality)
                .collect(Collectors.toSet());
        assertEquals(expectedSpecialities, professorService.getSpecialities());
    }
}