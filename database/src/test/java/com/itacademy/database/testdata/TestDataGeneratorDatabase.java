package com.itacademy.database.testdata;

import com.itacademy.database.entity.Course;
import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.HomeworkId;
import com.itacademy.database.entity.Person;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.entity.Student;
import com.itacademy.database.entity.Task;
import com.itacademy.database.entity.User;
import java.util.Random;
import lombok.experimental.UtilityClass;

import static com.itacademy.database.entity.Mark.FIVE;
import static com.itacademy.database.entity.Role.ADMIN;
import static com.itacademy.database.entity.Role.USER;

@UtilityClass
public class TestDataGeneratorDatabase {

    public static User createDefaultUser() {
        return User.builder()
                .person(Person.builder()
                        .firstName("Alexander")
                        .lastName("Akulov")
                        .build())
                .email(new Random().nextInt() + "akulov@macademy.com")
                .password("akulov")
                .role(USER)
                .build();
    }

    public static User createAdminUser() {
        return User.builder()
                .person(Person.builder()
                        .firstName("Dzianis")
                        .lastName("Matveyenka")
                        .build())
                .email(new Random().nextInt() + "matveyenka@macademy.com")
                .password("matveyenka")
                .role(ADMIN)
                .build();
    }

    public static Professor createProfessor() {
        return new Professor(createAdminUser(),
                "Java",
                "Game Of Thrones, Big Data, SQL, neural networks, algorithms and data structures",
                Short.valueOf("7"));
    }

    public static Student createStudent() {
        return new Student(createDefaultUser(),
                "unknown",
                "sysadmin");
    }

    public static Course createCourse() {
        return Course.builder()
                .professor(createProfessor())
                .name("JD1")
                .build();
    }

    public static Task createTask() {
        return Task.builder()
                .course(createCourse())
                .subject("Maven")
                .exercise("Check point 1")
                .build();
    }

    public static Homework createHomework() {
        return Homework.builder()
                .id(HomeworkId.builder()
                        .task(createTask())
                        .student(createStudent())
                        .build())
                .work("work")
                .mark(FIVE)
                .build();
    }
}
