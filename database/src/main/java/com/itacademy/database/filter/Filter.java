package com.itacademy.database.filter;

import com.itacademy.database.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.itacademy.database.util.SessionManager.getSession;

@Getter
public abstract class Filter<E extends BaseEntity> {

    public static final Integer DEFAULT_LIMIT = 100;

    private CriteriaBuilder cb;
    private CriteriaQuery<E> criteria;
    private Root<E> root;
    private Predicate[] predicates;
    private Integer limit;
    private Integer offset;

    public Filter(CriteriaQuery<E> criteria, Root<E> root, Predicate[] predicates, Integer limit, Integer offset) {
        this.cb = getSession().getCriteriaBuilder();
        this.criteria = criteria;
        this.root = root;
        this.predicates = predicates;
        this.limit = limit;
        this.offset = offset;
    }
}
