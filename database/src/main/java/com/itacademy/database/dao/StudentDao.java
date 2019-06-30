package com.itacademy.database.dao;

import com.itacademy.database.entity.Mark;
import com.itacademy.database.entity.Student;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.stereotype.Repository;

import static com.itacademy.database.entity.QHomework.homework;
import static com.itacademy.database.entity.QStudent.student;

@Repository
public class StudentDao extends BaseDao<Long, Student> {

    public Double getAverageMark(Long studentId) {
        List<Mark> marks = new JPAQuery<Mark>(getSessionFactory().getCurrentSession())
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
}
