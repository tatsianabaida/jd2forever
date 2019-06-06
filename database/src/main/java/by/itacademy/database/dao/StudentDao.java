package by.itacademy.database.dao;

import by.itacademy.database.connection.ConnectionPool;
import by.itacademy.database.entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();
    private static final String FIND_ALL = "SELECT * FROM academy_storage.student";

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                Student student = getStudentFromResultSet(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Student getStudentFromResultSet(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .build();
    }

    public static StudentDao getInstance() {
        return INSTANCE;
    }
}
