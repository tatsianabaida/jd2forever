package com.itacademy.web.controller;

import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.dto.ProfessorsPageDto;
import com.itacademy.database.entity.Professor;
import com.itacademy.service.service.ProfessorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static com.itacademy.database.filter.Builder.DEFAULT_LIMIT;
import static com.itacademy.database.filter.Builder.DEFAULT_OFFSET;
import static com.itacademy.web.util.UrlPath.API;
import static com.itacademy.web.util.UrlPath.PROFESSOR_FILTER_PATH;
import static com.itacademy.web.util.ViewName.PROFESSOR_FILTER_VIEW;

@Controller
@RequestMapping(value = API + PROFESSOR_FILTER_PATH)
@SessionAttributes("pages")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessorFilterController {

    private final ProfessorService professorService;

    @GetMapping
    public String getProfessorFilterPage(Model model) {
        Set<String> specialities = professorService.getSpecialities();
        model.addAttribute("specialities", specialities);
        return PROFESSOR_FILTER_VIEW;
    }

    @PostMapping
    public String filter(Model model, ProfessorFilterDto filterDto) {
        filterDto.setLimit(Objects.nonNull(filterDto.getLimit()) && filterDto.getLimit() > 0
                ? filterDto.getLimit()
                : DEFAULT_LIMIT);
        filterDto.setOffset(DEFAULT_OFFSET);
        Integer limit = filterDto.getLimit();
        List<Professor> professorsForPage = professorService.getAll(filterDto);
        List<ProfessorsPageDto> pages = new ArrayList<>();
        while (professorsForPage.size() > 0) {
            pages.add(ProfessorsPageDto.builder()
                    .pageNumber(pages.size() + 1)
                    .entitiesList(professorsForPage)
                    .build());
            filterDto.setOffset(filterDto.getOffset() + limit);
            professorsForPage = professorService.getAll(filterDto);
        }
        if (pages.size() > 0) {
            model.addAttribute("pages", pages);
            return String.format("redirect:%s%s?page=1", API, PROFESSOR_FILTER_PATH);
        } else {
            return String.format("redirect:%s%s?not_found_error=true", API, PROFESSOR_FILTER_PATH);
        }
    }
}