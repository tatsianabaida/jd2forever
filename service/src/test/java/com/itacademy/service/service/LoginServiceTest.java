package com.itacademy.service.service;

import com.itacademy.database.dto.NewStudentDto;
import com.itacademy.database.entity.Student;
import com.itacademy.service.exception.EmailExistsException;
import com.itacademy.service.testData.TestDataGeneratorService;
import java.util.Optional;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class LoginServiceTest extends ServiceTest {

    @Test(expected = EmailExistsException.class)
    public void registerNewUserAccountException() {
        NewStudentDto student = TestDataGeneratorService.createNewStudent();
        loginService.registerNewStudentAccount(student);
        loginService.registerNewStudentAccount(student);
    }

    @Test
    public void registerNewUserAccount() {
        NewStudentDto student = TestDataGeneratorService.createNewStudent();
        student.setCompany("  ");
        student.setCurrentPosition("  ");
        Long studentId = loginService.registerNewStudentAccount(student);
        sessionFactory.getCurrentSession().clear();
        Optional<Student> studentFromDb = studentService.findById(studentId);
        assertNotEquals(Optional.empty(), studentFromDb);
        assertEquals(Optional.empty(), studentService.findById(studentId).map(Student::getCompany));
        assertEquals(Optional.empty(), studentService.findById(studentId).map(Student::getCurrentPosition));
    }

    @Test
    public void loadUserByUsername() {
        NewStudentDto studentDto = TestDataGeneratorService.createNewStudent();
        loginService.registerNewStudentAccount(studentDto);
        assertNotNull(loginService.loadUserByUsername(studentDto.getEmail()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameException() {
        NewStudentDto studentDto = TestDataGeneratorService.createNewStudent();
        loginService.loadUserByUsername(studentDto.getEmail());
    }
}