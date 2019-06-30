package com.itacademy.database.dao;

import com.itacademy.database.entity.Homework;
import com.itacademy.database.filter.HomeworkFilter;
import java.util.List;
import org.junit.Test;

import static com.itacademy.database.testdata.TestDataGenerator.createHomework;
import static org.junit.Assert.assertEquals;

public class HomeworkDaoTest extends DaoTest {

    @Test
    public void getByFilter() {
        Homework homework1 = createHomework();
        Homework homework2 = createHomework();
        Homework homework3 = createHomework();

        homework1.getId().setStudent(homework2.getId().getStudent());
        homework1.getId().getTask().getCourse().setProfessor(homework2.getId().getTask().getCourse().getProfessor());
        homework2.setMark(null);
        homework3.getId().setTask(homework2.getId().getTask());
        homework3.setMark(null);

        Long student2Id = studentDao.save(homework2.getId().getStudent());
        studentDao.save(homework3.getId().getStudent());
        Long professor2Id = professorDao.save(homework2.getId().getTask().getCourse().getProfessor());
        Long course1Id = courseDao.save(homework1.getId().getTask().getCourse());
        courseDao.save(homework2.getId().getTask().getCourse());
        taskDao.save(homework1.getId().getTask());
        Long task2Id = taskDao.save(homework2.getId().getTask());
        homeworkDao.save(homework1);
        homeworkDao.save(homework2);
        homeworkDao.save(homework3);

        List<Homework> allByTaskAndProfessorWithoutMark = homeworkDao.getAll(HomeworkFilter.builder(sessionFactory)
                .withMark(false)
                .byTask(task2Id)
                .byStudent(null)
                .byCourse(null)
                .forProfessor(professor2Id)
                .build());
        List<Homework> allByCourseAndStudentWithMark = homeworkDao.getAll(HomeworkFilter.builder(sessionFactory)
                .byCourse(course1Id)
                .byStudent(student2Id)
                .withMark(true)
                .byTask(null)
                .forProfessor(null)
                .build());
        List<Homework> allLimitAndOffset = homeworkDao.getAll(HomeworkFilter.builder(sessionFactory)
                .limit(1)
                .offset(1)
                .withMark(null)
                .build());

        int expectedByTaskAndProfessorWithoutMark = 2;
        int expectedByStudentWithMark = 1;
        int expectedLimitAndOffset = 1;

        assertEquals(expectedByTaskAndProfessorWithoutMark, allByTaskAndProfessorWithoutMark.size());
        assertEquals(expectedByStudentWithMark, allByCourseAndStudentWithMark.size());
        assertEquals(expectedLimitAndOffset, allLimitAndOffset.size());
    }

}