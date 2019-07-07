package com.itacademy.service.service;

import com.itacademy.database.dto.NewStudentDto;
import com.itacademy.service.testData.TestDataGeneratorService;
import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StudentServiceTest extends ServiceTest {

    @Test
    public void getAll() {
        NewStudentDto student1 = TestDataGeneratorService.createNewStudent();
        NewStudentDto student2 = TestDataGeneratorService.createNewStudent();
        NewStudentDto student3 = TestDataGeneratorService.createNewStudent();

        loginService.registerNewStudentAccount(student1);
        loginService.registerNewStudentAccount(student2);
        loginService.registerNewStudentAccount(student3);

        int expectedSize = 3;
        assertEquals(expectedSize, studentService.getAll().size());
    }

    @Test
    public void findById() {
        NewStudentDto student = TestDataGeneratorService.createNewStudent();
        Long studentId = loginService.registerNewStudentAccount(student);
        sessionFactory.getCurrentSession().clear();
        assertNotEquals(Optional.empty(), studentService.findById(studentId));
    }
}