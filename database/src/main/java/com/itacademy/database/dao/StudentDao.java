package com.itacademy.database.dao;

import com.itacademy.database.entity.Mark;
import com.itacademy.database.entity.Student;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

import static com.itacademy.database.entity.QHomework.homework;
import static com.itacademy.database.entity.QStudent.student;
import static com.itacademy.database.util.SessionManager.getSession;

public class StudentDao implements BaseDao<Long, Student> {

    private static final StudentDao INSTANCE = new StudentDao();

    public Double getAverageMark(Long studentId) {
        List<Mark> marks = new JPAQuery<Mark>(getSession())
                .select(homework.mark)
                .from(homework)
                .join(homework.id.student, student)
                .where(student.id.eq(studentId).and(homework.mark.isNotNull()))
                .fetch();

        Integer marksSum = marks.stream()
                .map(Mark::getDescription)
                .reduce(0, (acc, next) -> acc + next);
        return (double) Math.round((double) marksSum * 100 / marks.size()) / 100;
    }

    public static StudentDao getInstance() {
        return INSTANCE;
    }
}
