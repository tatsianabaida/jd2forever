package com.itacademy.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorFilterDto {

    private String firstName;
    private String lastName;
    private String speciality;
    private String email;
    private Integer limit;
    private Integer offset;
}
