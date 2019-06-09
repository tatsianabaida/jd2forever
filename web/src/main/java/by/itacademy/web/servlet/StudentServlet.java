package by.itacademy.web.servlet;

import by.itacademy.web.util.JspPath;
import com.itacademy.database.entity.Student;
import com.itacademy.service.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/students-list")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = StudentService.getInstance();

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
