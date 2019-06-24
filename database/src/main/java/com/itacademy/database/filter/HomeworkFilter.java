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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.itacademy.database.util.SessionManager.getSession;
import static java.util.Objects.isNull;

public final class HomeworkFilter extends Filter<Homework> {

    private HomeworkFilter(CriteriaQuery<Homework> criteria,
                           Root<Homework> root,
                           Predicate[] predicates,
                           Integer limit,
                           Integer offset) {
        super(criteria, root, predicates, limit, offset);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

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
        private Integer offset = 0;

        public Builder byStudent(Long studentId) {
            if (!isNull(studentId)) {
                predicates.add(cb.equal(studentJoin.get(Student_.id), studentId));
            }
            return this;
        }

        public Builder forProfessor(Long professorId) {
            if (!isNull(professorId)) {
                predicates.add(cb.equal(professorJoin.get(Professor_.id), professorId));
            }
            return this;
        }

        public Builder byTask(Long taskId) {
            if (!isNull(taskId)) {
                predicates.add(cb.equal(taskJoin.get(Task_.id), taskId));
            }
            return this;
        }

        public Builder byCourse(Long courseId) {
            if (!isNull(courseId)) {
                predicates.add(cb.equal(courseJoin.get(Course_.id), courseId));
            }
            return this;
        }

        public Builder withMark(Boolean withMark) {
            if (!isNull(withMark) && withMark) {
                predicates.add(cb.isNotNull(root.get(Homework_.mark)));
            } else if (!isNull(withMark)) {
                predicates.add(cb.isNull(root.get(Homework_.mark)));
            }
            return this;
        }

        public Builder limit(Integer limit) {
            if (!isNull(limit)) {
                this.limit = limit;
            }
            return this;
        }

        public Builder offset(Integer offset) {
            if (!isNull(offset)) {
                this.offset = offset;
            }
            return this;
        }

        public HomeworkFilter build() {
            return new HomeworkFilter(criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
