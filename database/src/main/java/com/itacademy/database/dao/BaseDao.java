package com.itacademy.database.dao;

import com.itacademy.database.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

@Getter
@Transactional(readOnly = true)
public abstract class BaseDao<T extends Serializable, E extends BaseEntity<T>> {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<E> findById(T id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(getClazz(), id));
    }

    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Class<E> clazz = getClazz();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        criteria.select(root);
        return session.createQuery(criteria).list();
    }

    @Transactional
    public T save(E entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity.getId();
    }

    @Transactional
    public void update(E entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Transactional
    public void delete(E entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    private Class<E> getClazz() {
        return (Class<E>) requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), BaseDao.class))[1];
    }
}
