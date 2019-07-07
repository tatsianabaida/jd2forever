package com.itacademy.database;

import com.itacademy.database.dao.ProfessorDao;
import com.itacademy.database.entity.Person;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.itacademy.database.entity.Role.ADMIN;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessorTestDataImporter {

    private final PasswordEncoder passwordEncoder;
    private final ProfessorDao professorDao;

    public void importTestData() {
        Professor professor1 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Dzianis")
                        .lastName("Matveyenka")
                        .build())
                .email("matveyenka@macademy.com")
                .password(passwordEncoder.encode("matveyenka"))
                .role(ADMIN)
                .build(),
                "Java", "Game Of Thrones", Short.valueOf("7"));
        Professor professor2 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Bill")
                        .lastName("Gates")
                        .build())
                .email("gates@macademy.com")
                .password(passwordEncoder.encode("gates"))
                .role(ADMIN)
                .build(),
                "C++", "Big Data", Short.valueOf("15"));
        Professor professor3 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Steve")
                        .lastName("Jobs")
                        .build())
                .email("jobs@macademy.com")
                .password(passwordEncoder.encode("jobs"))
                .role(ADMIN)
                .build(),
                "Java", "Neural networks", Short.valueOf("12"));
        Professor professor4 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Sergey")
                        .lastName("Brin")
                        .build())
                .email("brin@macademy.com")
                .password(passwordEncoder.encode("brin"))
                .role(ADMIN)
                .build(),
                "C", "Algorithms", Short.valueOf("20"));
        Professor professor5 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Tim")
                        .lastName("Cook")
                        .build())
                .email("cook@macademy.com")
                .password(passwordEncoder.encode("cook"))
                .role(ADMIN)
                .build(),
                "PHP", "Game Of Thrones", Short.valueOf("3"));
        Professor professor6 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Diane")
                        .lastName("Greene")
                        .build())
                .email("greene@macademy.com")
                .password(passwordEncoder.encode("greene"))
                .role(ADMIN)
                .build(),
                "Java", "Big Data", Short.valueOf("5"));
        Professor professor7 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Dzianis")
                        .lastName("Greene")
                        .build())
                .email("mgreene@macademy.com")
                .password(passwordEncoder.encode("mgreene"))
                .role(ADMIN)
                .build(),
                "C++", "Game Of Thrones", Short.valueOf("7"));
        Professor professor8 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Bill")
                        .lastName("Jobs")
                        .build())
                .email("bjobs@macademy.com")
                .password(passwordEncoder.encode("jobs"))
                .role(ADMIN)
                .build(),
                "Groovy", "Big Data", Short.valueOf("15"));
        Professor professor9 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Steve")
                        .lastName("Gates")
                        .build())
                .email("sgates@macademy.com")
                .password(passwordEncoder.encode("sgates"))
                .role(ADMIN)
                .build(),
                "Java", "Neural networks", Short.valueOf("12"));
        Professor professor10 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Sergey")
                        .lastName("Jobs")
                        .build())
                .email("sjobs@macademy.com")
                .password(passwordEncoder.encode("sjobs"))
                .role(ADMIN)
                .build(),
                "C", "Algorithms", Short.valueOf("20"));
        Professor professor11 = new Professor(User.builder()
                .person(Person.builder()
                        .firstName("Tim")
                        .lastName("Brin")
                        .build())
                .email("tbrin@macademy.com")
                .password(passwordEncoder.encode("tbrin"))
                .role(ADMIN)
                .build(),
                "JavaScript", "Game Of Thrones", Short.valueOf("3"));

        List<Professor> professors = new ArrayList<>() {{
            add(professor1);
            add(professor2);
            add(professor3);
            add(professor4);
            add(professor5);
            add(professor6);
            add(professor7);
            add(professor8);
            add(professor9);
            add(professor10);
            add(professor11);
        }};

        professors.forEach(professorDao::save);
    }
}
