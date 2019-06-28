package com.itacademy.database.dao;

import com.itacademy.database.entity.BaseEntity;
import com.itacademy.database.filter.Filter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.itacademy.database.util.SessionManager.getSession;

public interface Filterable<E extends BaseEntity, F extends Filter<E>> {

    default List<E> getAll(F filter) {
        Session session = getSession();
        CriteriaBuilder cb = filter.getCb();
        CriteriaQuery<E> criteria = filter.getCriteria();
        Root<E> root = filter.getRoot();
        criteria.select(root).where(
                filter.getPredicates()
        );
        return session
                .createQuery(criteria)
                .setFirstResult(filter.getOffset())
                .setMaxResults(filter.getLimit())
                .list();
    }
}
