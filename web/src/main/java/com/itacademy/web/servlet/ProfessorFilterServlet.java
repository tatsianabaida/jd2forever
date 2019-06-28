package com.itacademy.web.servlet;

import com.itacademy.database.dto.ProfessorFilterDto;
import com.itacademy.database.dto.ProfessorsPageDto;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.util.StringUtils;
import com.itacademy.service.service.ProfessorService;
import com.itacademy.web.util.JspPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itacademy.database.filter.Filter.DEFAULT_LIMIT;
import static com.itacademy.web.util.UrlPath.PROFESSOR_FILTER;

@WebServlet(PROFESSOR_FILTER)
public class ProfessorFilterServlet extends HttpServlet {

    private final ProfessorService professorService = ProfessorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<String> speciatities = professorService.findAll()
                .stream()
                .map(Professor::getSpeciality)
                .collect(Collectors.toSet());
        req.setAttribute("specialities", speciatities);
        getServletContext()
                .getRequestDispatcher(JspPath.get("professor-filter"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer limit = StringUtils.isNotEmpty(req.getParameter("limit"))
                ? Integer.valueOf(req.getParameter("limit"))
                : DEFAULT_LIMIT;
        ProfessorFilterDto professorFilterDto = ProfessorFilterDto.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .speciality(req.getParameter("speciality"))
                .email(req.getParameter("email"))
                .limit(limit)
                .offset(0)
                .build();
        List<Professor> professorsForPage = professorService.getAll(professorFilterDto);
        List<ProfessorsPageDto> pages = new ArrayList<>();
        while (professorsForPage.size() > 0) {
            pages.add(ProfessorsPageDto.builder()
                    .pageNumber(pages.size() + 1)
                    .entitiesList(professorsForPage)
                    .build());
            professorFilterDto.setOffset(professorFilterDto.getOffset() + limit);
            professorsForPage = professorService.getAll(professorFilterDto);
        }
        if (pages.size() > 0) {
            req.getSession().setAttribute("pages", pages);
            resp.sendRedirect(String.format("%s?page=1", PROFESSOR_FILTER));
        } else {
            resp.sendRedirect(String.format("%s?not_found_error=true", PROFESSOR_FILTER));
        }
    }
}
