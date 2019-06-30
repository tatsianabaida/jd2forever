package com.itacademy.service.service;

import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.entity.Professor;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(expectedAllJava, allJava.size());
    }

    @Test
    public void findAll() {
        professorTestDataImporter.importTestData();
        List<Professor> all = professorService.findAll();

        int expectedAll = 11;
        Assert.assertEquals(expectedAll, all.size());
    }
}