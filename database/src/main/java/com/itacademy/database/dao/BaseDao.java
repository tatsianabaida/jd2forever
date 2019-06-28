package com.itacademy.database.dao;

import com.itacademy.database.entity.BaseEntity;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import static com.itacademy.database.util.SessionManager.getSession;

public interface BaseDao<T extends Serializable, E extends BaseEntity<T>> {

    default Optional<E> findById(T id) {
        return Optional.ofNullable(getSession().get(getClazz(), id));
    }

    default List<E> findAll() {
        Session session = getSession();
        Class<E> clazz = getClazz();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        criteria.select(root);
        return session.createQuery(criteria).list();
    }

    default T save(E entity) {
        Session session = getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return entity.getId();
    }

    default void update(E entity) {
        Session session = getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    default void delete(E entity) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    default Class<E> getClazz() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
    }
}
