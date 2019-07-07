package com.itacademy.database.dto;

import com.itacademy.database.entity.Professor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorsPageDto {

    private Integer pageNumber;
    private List<Professor> entitiesList;
}
