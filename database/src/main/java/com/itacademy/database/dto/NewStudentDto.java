package com.itacademy.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewStudentDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String company;
    private String currentPosition;
}
