package com.itacademy.database.filter;

import com.itacademy.database.entity.BaseEntity;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;

import static com.itacademy.database.util.SessionManager.getSession;

@Getter
public abstract class Filter<E extends BaseEntity> {

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

    public static MethodInterceptor getInterceptor(Builder original) {
        return (proxy, method, args, methodProxy) -> {
            if (Arrays.stream(args).anyMatch(Objects::isNull)) {
                return proxy;
            } else if (!method.getName().equals("build")) {
                method.invoke(original, args);
                return proxy;
            }
            return method.invoke(original, args);
        };
    }
}
