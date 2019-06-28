package com.itacademy.database.dao;

import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.Student;
import com.itacademy.database.entity.Task;
import com.itacademy.database.filter.TaskFilter;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createHomework;
import static com.itacademy.database.testdata.TestDataGenerator.createStudent;
import static com.itacademy.database.testdata.TestDataGenerator.createTask;
import static com.itacademy.database.util.SessionManager.getSession;
import static org.junit.Assert.assertEquals;

public class TaskDaoTest {

    private final StudentDao studentDao = StudentDao.getInstance();
    private final ProfessorDao professorDao = ProfessorDao.getInstance();
    private final CourseDao courseDao = CourseDao.getInstance();
    private final TaskDao taskDao = TaskDao.getInstance();
    private final HomeworkDao homeworkDao = HomeworkDao.getInstance();

    @Before
    public void cleanTable() {
        @Cleanup Session session = getSession().getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete from Homework ").executeUpdate();
        session.createQuery("delete from Task ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void getAllDoneAndToDoByFilter() {
        Task task1 = createTask();
        Task task2 = createTask();
        Task task3 = createTask();
        Student student1 = createStudent();
        Homework homework1 = createHomework();
        Homework homework2 = createHomework();

        homework1.getId().setStudent(student1);
        homework2.getId().setStudent(student1);
        homework1.getId().setTask(task1);
        homework2.getId().setTask(task2);

        professorDao.save(task1.getCourse().getProfessor());
        professorDao.save(task2.getCourse().getProfessor());
        professorDao.save(task3.getCourse().getProfessor());
        Long student1Id = studentDao.save(student1);
        courseDao.save(task1.getCourse());
        courseDao.save(task2.getCourse());
        courseDao.save(task3.getCourse());
        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);
        homeworkDao.save(homework1);
        homeworkDao.save(homework2);

        List<Task> all = taskDao.findAll();
        List<Task> allDoneByStudent = taskDao.getAll(TaskFilter.builder()
                .doneByStudent(student1Id)
                .build());
        List<Task> allToDoForStudent = taskDao.getAll(TaskFilter.builder()
                .toDoForStudent(student1Id)
                .build());
        List<Task> limitAndOffset = taskDao.getAll(TaskFilter.builder()
                .offset(1)
                .limit(1)
                .build());

        int expectedAll = 3;
        int expectedDoneByStudent = 2;
        int expectedToDoForStudent = 1;
        int expectedLimitAndOffset = 1;

        assertEquals(expectedAll, all.size());
        assertEquals(expectedDoneByStudent, allDoneByStudent.size());
        assertEquals(expectedToDoForStudent, allToDoForStudent.size());
        assertEquals(expectedLimitAndOffset, limitAndOffset.size());
    }

    @Test
    public void getAllFilteredByCourseAndProfessor() {
        Task task1 = createTask();
        Task task2 = createTask();
        Task task3 = createTask();

        task2.setCourse(task3.getCourse());
        task1.getCourse().setProfessor(task3.getCourse().getProfessor());

        Long professor3Id = professorDao.save(task3.getCourse().getProfessor());
        Long course1Id = courseDao.save(task1.getCourse());
        Long course3Id = courseDao.save(task3.getCourse());
        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);

        List<Task> all = taskDao.findAll();
        List<Task> allByProfessor = taskDao.getAll(TaskFilter.builder()
                .byProfessor(professor3Id)
                .limit(null)
                .offset(null)
                .byCourse(null)
                .doneByStudent(null)
                .toDoForStudent(null)
                .build());
        List<Task> allByCourse = taskDao.getAll(TaskFilter.builder()
                .byCourse(course3Id)
                .byProfessor(null)
                .build());
        List<Task> allByCourseAndProfessor = taskDao.getAll(TaskFilter.builder()
                .byCourse(course1Id)
                .toDoForStudent(null)
                .byProfessor(professor3Id)
                .limit(null)
                .build());

        int expectedAll = 3;
        int expectedByProfessor = 3;
        int expectedByCourse = 2;
        int expectedByCourseAndProfessor = 1;

        assertEquals(expectedAll, all.size());
        assertEquals(expectedByProfessor, allByProfessor.size());
        assertEquals(expectedByCourse, allByCourse.size());
        assertEquals(expectedByCourseAndProfessor, allByCourseAndProfessor.size());
    }
}