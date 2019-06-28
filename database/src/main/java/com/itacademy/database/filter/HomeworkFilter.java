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
import net.sf.cglib.proxy.Enhancer;

import static com.itacademy.database.util.SessionManager.getSession;

public final class HomeworkFilter extends Filter<Homework> {

    private HomeworkFilter(CriteriaQuery<Homework> criteria,
                           Root<Homework> root,
                           Predicate[] predicates,
                           Integer limit,
                           Integer offset) {
        super(criteria, root, predicates, limit, offset);
    }

    public static HomeworkBuilder builder() {
        HomeworkBuilder original = new HomeworkBuilder();
        return (HomeworkBuilder) Enhancer.create(HomeworkBuilder.class, getInterceptor(original));
    }

    public static class HomeworkBuilder implements Builder<HomeworkFilter> {

        private CriteriaBuilder cb = getSession().getCriteriaBuilder();
        private CriteriaQuery<Homework> criteria = cb.createQuery(Homework.class);
        private Root<Homework> root = criteria.from(Homework.class);
        private List<Predicate> predicates = new ArrayList<>();
        private Join<Homework, HomeworkId> homeworkIdJoin = root.join(Homework_.id);
        private Join<HomeworkId, Student> studentJoin = homeworkIdJoin.join(HomeworkId_.student);
        private Join<HomeworkId, Task> taskJoin = homeworkIdJoin.join(HomeworkId_.task);
        private Join<Task, Course> courseJoin = taskJoin.join(Task_.course);
        private Join<Course, Professor> professorJoin = courseJoin.join(Course_.professor);
        private Integer limit = DEFAULT_LIMIT;
        private Integer offset = DEFAULT_OFFSET;

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
            return new HomeworkFilter(criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
