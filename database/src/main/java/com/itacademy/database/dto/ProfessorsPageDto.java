package com.itacademy.database.dto;

import com.itacademy.database.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorsPageDto {

    private Integer pageNumber;
    private ProfessorFilterDto filterDto;
    private List<Professor> entitiesList;
}
