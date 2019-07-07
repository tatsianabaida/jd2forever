package com.itacademy.service.testData;

import com.itacademy.database.dto.NewStudentDto;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataGeneratorService {

    public NewStudentDto createNewStudent() {
        return NewStudentDto.builder()
                .firstName("Bobckevich")
                .lastName("Artem")
                .email(new Random().nextInt() + "bobckevich@macademy.com")
                .password("bobckevich")
                .company("unknown")
                .currentPosition("System administrator")
                .build();
    }
}
