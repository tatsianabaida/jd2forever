package com.itacademy.web.controller;

import com.itacademy.database.entity.Student;
import com.itacademy.service.service.StudentService;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itacademy.web.util.UrlPath.API;
import static com.itacademy.web.util.UrlPath.STUDENTS_LIST_PATH;
import static com.itacademy.web.util.ViewName.STUDENTS_LIST_VIEW;

@Controller
@RequestMapping(value = API)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentService studentService;

    @GetMapping(STUDENTS_LIST_PATH)
    public String studentsView(Model model) {
        List<Student> studentsList = studentService.getAll();
        studentsList.sort(Comparator.comparing(student -> student.getPerson().getLastName()));
        model.addAttribute("studentsList", studentsList);
        return STUDENTS_LIST_VIEW;
    }
}
