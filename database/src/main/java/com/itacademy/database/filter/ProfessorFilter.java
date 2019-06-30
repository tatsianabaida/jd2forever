package com.itacademy.database.filter;

import com.itacademy.database.entity.Person_;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.entity.Professor_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import org.hibernate.SessionFactory;

@Getter
public final class ProfessorFilter extends Filter<Professor> {

    private ProfessorFilter(CriteriaBuilder cb,
                            CriteriaQuery<Professor> criteria,
                            Root<Professor> root,
                            Predicate[] predicates,
                            Integer limit,
                            Integer offset) {
        super(cb, criteria, root, predicates, limit, offset);
    }

    public static ProfessorBuilder builder(SessionFactory sessionFactory) {
        ProfessorBuilder original = new ProfessorBuilder(sessionFactory);
        return (ProfessorBuilder) Enhancer.create(ProfessorBuilder.class, getInterceptor(original));
    }

    @NoArgsConstructor
    public static class ProfessorBuilder implements Builder<ProfessorFilter> {

        private CriteriaBuilder cb;
        private CriteriaQuery<Professor> criteria;
        private Root<Professor> root;
        private List<Predicate> predicates;
        private Integer limit;
        private Integer offset;

        private ProfessorBuilder(SessionFactory sessionFactory) {
            cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
            criteria = cb.createQuery(Professor.class);
            root = criteria.from(Professor.class);
            predicates = new ArrayList<>();
            limit = DEFAULT_LIMIT;
            offset = DEFAULT_OFFSET;
        }

        public ProfessorBuilder firstName(String firstName) {
            predicates.add(cb.like(root.get(Professor_.person).get(Person_.firstName), firstName));
            return this;
        }

        public ProfessorBuilder lastName(String lastName) {
            predicates.add(cb.like(root.get(Professor_.person).get(Person_.lastName), lastName));
            return this;
        }

        public ProfessorBuilder speciality(String speciality) {
            predicates.add(cb.equal(root.get(Professor_.speciality), speciality));
            return this;
        }

        public ProfessorBuilder email(String email) {
            predicates.add(cb.equal(root.get(Professor_.email), email));
            return this;
        }

        public ProfessorBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public ProfessorBuilder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public ProfessorFilter build() {
            return new ProfessorFilter(cb, criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
