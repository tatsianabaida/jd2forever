package com.itacademy.database.dao;

import com.itacademy.database.entity.Professor;
import com.itacademy.database.filter.ProfessorFilter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGeneratorDatabase.createProfessor;
import static org.junit.Assert.assertEquals;

public class ProfessorDaoTest extends DaoTest {

    @Test
    public void checkGetAllByFilter() {
        Professor matveyenka1 = createProfessor();
        Professor matveyenka2 = createProfessor();
        Professor other = createProfessor();
        other.setSpeciality("other");
        professorDao.save(matveyenka1);
        professorDao.save(matveyenka2);
        professorDao.save(other);
        List<Professor> all = professorDao.findAll();
        List<Professor> allMatveyenka = professorDao.getAll(ProfessorFilter.builder(sessionFactory)
                .lastName("Matveyenka")
                .firstName(null)
                .offset(null)
                .limit(null)
                .email(null)
                .speciality("Java")
                .build());
        int expectedAll = 3;
        int expectedMatveyenka = 2;
        assertEquals(expectedAll, all.size());
        assertEquals(expectedMatveyenka, allMatveyenka.size());
    }

    @Test
    public void byFirstNameAndEmail() {
        Professor professor1 = createProfessor();
        Professor professor2 = createProfessor();

        professor1.getPerson().setFirstName("NewFN");
        professor2.getPerson().setFirstName("NewFN");
        professor1.setEmail("NewEmail");

        professorDao.save(professor1);
        professorDao.save(professor2);

        List<Professor> allByFirstName = professorDao.getAll(ProfessorFilter.builder(sessionFactory)
                .firstName("NewFN")
                .speciality(null)
                .limit(null)
                .build());

        List<Professor> allByFirstNameAndEmail = professorDao.getAll(ProfessorFilter.builder(sessionFactory)
                .firstName("NewFN")
                .lastName(null)
                .speciality(null)
                .email("NewEmail")
                .limit(5)
                .offset(0)
                .build());

        int expectedByFirstName = 2;
        int expectedByFirstNameAndEmail = 1;

        assertEquals(expectedByFirstName, allByFirstName.size());
        assertEquals(expectedByFirstNameAndEmail, allByFirstNameAndEmail.size());
    }

    @Test
    public void getSpecialities() {
        professorTestDataImporter.importTestData();
        Set<String> expectedSpecialities = professorDao.findAll().stream()
                .map(Professor::getSpeciality)
                .collect(Collectors.toSet());
        assertEquals(expectedSpecialities, professorDao.getSpecialities());
    }
}