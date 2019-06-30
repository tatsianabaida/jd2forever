package com.itacademy.database.filter;

import com.itacademy.database.entity.Course;
import com.itacademy.database.entity.Course_;
import com.itacademy.database.entity.Homework;
import com.itacademy.database.entity.HomeworkId;
import com.itacademy.database.entity.HomeworkId_;
import com.itacademy.database.entity.Homework_;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.entity.Professor_;
import com.itacademy.database.entity.Student;
import com.itacademy.database.entity.Student_;
import com.itacademy.database.entity.Task;
import com.itacademy.database.entity.Task_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import org.hibernate.SessionFactory;

public final class HomeworkFilter extends Filter<Homework> {

    private HomeworkFilter(CriteriaBuilder cb,
                           CriteriaQuery<Homework> criteria,
                           Root<Homework> root,
                           Predicate[] predicates,
                           Integer limit,
                           Integer offset) {
        super(cb, criteria, root, predicates, limit, offset);
    }


    public static HomeworkBuilder builder(SessionFactory sessionFactory) {
        HomeworkBuilder original = new HomeworkBuilder(sessionFactory);
        return (HomeworkBuilder) Enhancer.create(HomeworkBuilder.class, getInterceptor(original));
    }

    @NoArgsConstructor
    public static class HomeworkBuilder implements Builder<HomeworkFilter> {

        private CriteriaBuilder cb;
        private CriteriaQuery<Homework> criteria;
        private Root<Homework> root;
        private List<Predicate> predicates;
        private Join<Homework, HomeworkId> homeworkIdJoin;
        private Join<HomeworkId, Student> studentJoin;
        private Join<HomeworkId, Task> taskJoin;
        private Join<Task, Course> courseJoin;
        private Join<Course, Professor> professorJoin;
        private Integer limit;
        private Integer offset;


        private HomeworkBuilder(SessionFactory sessionFactory) {
            cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
            criteria = cb.createQuery(Homework.class);
            root = criteria.from(Homework.class);
            predicates = new ArrayList<>();
            homeworkIdJoin = root.join(Homework_.id);
            studentJoin = homeworkIdJoin.join(HomeworkId_.student);
            taskJoin = homeworkIdJoin.join(HomeworkId_.task);
            courseJoin = taskJoin.join(Task_.course);
            professorJoin = courseJoin.join(Course_.professor);
            limit = DEFAULT_LIMIT;
            offset = DEFAULT_OFFSET;
        }

        public HomeworkBuilder byStudent(Long studentId) {
            predicates.add(cb.equal(studentJoin.get(Student_.id), studentId));
            return this;
        }

        public HomeworkBuilder forProfessor(Long professorId) {
            predicates.add(cb.equal(professorJoin.get(Professor_.id), professorId));
            return this;
        }

        public HomeworkBuilder byTask(Long taskId) {
            predicates.add(cb.equal(taskJoin.get(Task_.id), taskId));
            return this;
        }

        public HomeworkBuilder byCourse(Long courseId) {
            predicates.add(cb.equal(courseJoin.get(Course_.id), courseId));
            return this;
        }

        public HomeworkBuilder withMark(Boolean withMark) {
            if (withMark) {
                predicates.add(cb.isNotNull(root.get(Homework_.mark)));
            } else {
                predicates.add(cb.isNull(root.get(Homework_.mark)));
            }
            return this;
        }

        public HomeworkBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public HomeworkBuilder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public HomeworkFilter build() {
            return new HomeworkFilter(cb, criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
