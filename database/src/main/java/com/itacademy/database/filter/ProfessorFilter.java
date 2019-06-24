package com.itacademy.database.filter;

import com.itacademy.database.entity.Person_;
import com.itacademy.database.entity.Professor;
import com.itacademy.database.entity.Professor_;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.itacademy.database.util.SessionManager.getSession;
import static com.itacademy.database.util.StringUtils.isNotEmpty;
import static java.util.Objects.isNull;

@Getter
public final class ProfessorFilter extends Filter<Professor> {

    private ProfessorFilter(CriteriaQuery<Professor> criteria,
                            Root<Professor> root,
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
        private CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
        private Root<Professor> root = criteria.from(Professor.class);
        private List<Predicate> predicates = new ArrayList<>();
        private Integer limit = DEFAULT_LIMIT;
        private Integer offset = 0;

        public Builder firstName(String firstName) {
            if (isNotEmpty(firstName)) {
                predicates.add(cb.like(root.get(Professor_.person).get(Person_.firstName), firstName));
            }
            return this;
        }

        public Builder lastName(String lastName) {
            if (isNotEmpty(lastName)) {
                predicates.add(cb.like(root.get(Professor_.person).get(Person_.lastName), lastName));
            }
            return this;
        }

        public Builder speciality(String speciality) {
            if (isNotEmpty(speciality)) {
                predicates.add(cb.equal(root.get(Professor_.speciality), speciality));
            }
            return this;
        }

        public Builder email(String email) {
            if (isNotEmpty(email)) {
                predicates.add(cb.equal(root.get(Professor_.email), email));
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

        public ProfessorFilter build() {
            return new ProfessorFilter(criteria, root, predicates.toArray(Predicate[]::new), limit, offset);
        }
    }
}
