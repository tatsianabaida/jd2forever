package com.itacademy.service.service;

import com.itacademy.database.ProfessorTestDataImporter;
import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.util.SessionManager;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProfessorServiceTest {

    private final ProfessorService professorService = ProfessorService.getInstance();
    private final ProfessorTestDataImporter professorTestDataImporter = ProfessorTestDataImporter
            .getInstance();

    @Before
    public void cleanTable() {
        @Cleanup Session session = SessionManager.getFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.createQuery("delete from Course ").executeUpdate();
        session.createQuery("delete from Professor ").executeUpdate();
        session.getTransaction().commit();
    }

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