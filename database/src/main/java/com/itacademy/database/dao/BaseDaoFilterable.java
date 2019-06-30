package com.itacademy.database.dao;

import com.itacademy.database.entity.BaseEntity;
import com.itacademy.database.filter.Filter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

public abstract class BaseDaoFilterable<T extends Serializable, E extends BaseEntity<T>, F extends Filter<E>>
        extends BaseDao<T, E> {

    public List<E> getAll(F filter) {
        Session session = getSessionFactory().getCurrentSession();
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
