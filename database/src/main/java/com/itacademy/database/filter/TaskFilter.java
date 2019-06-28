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
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;
import net.sf.cglib.proxy.Enhancer;

import static com.itacademy.database.util.SessionManager.getSession;

public final class TaskFilter extends Filter<Task> {

    private TaskFilter(CriteriaQuery<Task> criteria,
                       Root<Task> root,
                       Predicate[] predicates,
                       Integer limit,
                       Integer offset) {
        super(criteria, root, predicates, limit, offset);
    }

    public static TaskBuilder builder() {
        TaskBuilder original = new TaskBuilder();
        return (TaskBuilder) Enhancer.create(TaskBuilder.class, getInterceptor(original));
    }

    public static class TaskBuilder implements Builder<TaskFilter> {

        private CriteriaBuilder cb = getSession().getCriteriaBuilder();
        private CriteriaQuery<Task> criteria = cb.createQuery(Task.class);
        private Root<Task> root = criteria.from(Task.class);
        private List<Predicate> predicates = new ArrayList<>();
        private Join<Task, Course> courseJoin = root.join(Task_.course);
        private Join<Course, Professor> professorJoin = courseJoin.join(Course_.professor);
        private Subquery<Task> doneByStudentSubQuery = criteria.subquery(Task.class);
        private Root<Task> subQueryRoot = doneByStudentSubQuery.from(Task.class);
        private SetJoin<Task, Homework> sqHomeworkJoin = subQueryRoot.join(Task_.homeworks);
        private Join<Homework, HomeworkId> sqHomeworkIdJoin = sqHomeworkJoin.join(Homework_.id);
        private Join<HomeworkId, Student> sqStudentJoin = sqHomeworkIdJoin.join(HomeworkId_.student);
        private Integer limit = DEFAULT_LIMIT;
        private Integer offset = DEFAULT_OFFSET;

        public TaskBuilder doneByStudent(Long studentId) {
            predicates.add(cb.in(root).value(
                    doneByStudentSubQuery
                            .select(subQueryRoot).where(cb.equal(sqStudentJoin.get(Student_.id), studentId)
                    )));
            return this;
        }

        public TaskBuilder toDoForStudent(Long studentId) {
            predicates.add(cb.in(root).value(
                    doneByStudentSubQuery
                            .select(subQueryRoot).where(cb.equal(sqStudentJoin.get(Student_.id), studentId)
                    )).not());
            return this;
        }

        public TaskBuilder byCourse(Long courseId) {
            predicates.add(cb.equal(courseJoin.get(Course_.id), courseId));
            return this;
        }

        public TaskBuilder byProfessor(Long professorId) {
            predicates.add(cb.equal(professorJoin.get(Professor_.id), professorId));
            return this;
        }

        public TaskBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public TaskBuilder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public TaskFilter build() {
            return new TaskFilter(criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
