package com.itacademy.web.servlet;

import com.itacademy.database.entity.Student;
import com.itacademy.service.service.StudentService;
import com.itacademy.web.util.JspPath;
import com.itacademy.web.util.UrlPath;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(UrlPath.STUDENTS_LIST)
public class StudentServlet extends HttpServlet {

    private StudentService studentService;

    @Override
    public void init() {
        studentService = ProfessorFilterServlet.getContext().getBean(StudentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> studentsList = studentService.getAll();
        studentsList.sort(Comparator.comparing(student -> student.getPerson().getFirstName()));
        req.setAttribute("studentsList", studentsList);

        getServletContext()
                .getRequestDispatcher(JspPath.get("students-list"))
                .forward(req, resp);
    }
}
