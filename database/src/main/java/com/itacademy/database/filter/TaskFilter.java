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
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import org.hibernate.SessionFactory;

public final class TaskFilter extends Filter<Task> {

    private TaskFilter(CriteriaBuilder cb,
                       CriteriaQuery<Task> criteria,
                       Root<Task> root,
                       Predicate[] predicates,
                       Integer limit,
                       Integer offset) {
        super(cb, criteria, root, predicates, limit, offset);
    }

    public static TaskBuilder builder(SessionFactory sessionFactory) {
        TaskBuilder original = new TaskBuilder(sessionFactory);
        return (TaskBuilder) Enhancer.create(TaskBuilder.class, getInterceptor(original));
    }

    @NoArgsConstructor
    public static class TaskBuilder implements Builder<TaskFilter> {

        private CriteriaBuilder cb;
        private CriteriaQuery<Task> criteria;
        private Root<Task> root;
        private List<Predicate> predicates;
        private Join<Task, Course> courseJoin;
        private Join<Course, Professor> professorJoin;
        private Subquery<Task> doneByStudentSubQuery;
        private Root<Task> subQueryRoot;
        private SetJoin<Task, Homework> sqHomeworkJoin;
        private Join<Homework, HomeworkId> sqHomeworkIdJoin;
        private Join<HomeworkId, Student> sqStudentJoin;
        private Integer limit;
        private Integer offset;

        private TaskBuilder(SessionFactory sessionFactory) {
            cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
            criteria = cb.createQuery(Task.class);
            root = criteria.from(Task.class);
            predicates = new ArrayList<>();
            courseJoin = root.join(Task_.course);
            professorJoin = courseJoin.join(Course_.professor);
            doneByStudentSubQuery = criteria.subquery(Task.class);
            subQueryRoot = doneByStudentSubQuery.from(Task.class);
            sqHomeworkJoin = subQueryRoot.join(Task_.homeworks);
            sqHomeworkIdJoin = sqHomeworkJoin.join(Homework_.id);
            sqStudentJoin = sqHomeworkIdJoin.join(HomeworkId_.student);
            limit = DEFAULT_LIMIT;
            offset = DEFAULT_OFFSET;
        }

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
            return new TaskFilter(cb, criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
