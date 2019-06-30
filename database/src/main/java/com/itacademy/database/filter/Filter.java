package com.itacademy.database.filter;

import com.itacademy.database.entity.BaseEntity;
import com.itacademy.database.util.StringUtils;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;

@Getter
@NoArgsConstructor
public abstract class Filter<E extends BaseEntity> {

    private CriteriaBuilder cb;
    private CriteriaQuery<E> criteria;
    private Root<E> root;
    private Predicate[] predicates;
    private Integer limit;
    private Integer offset;

    public Filter(CriteriaBuilder cb, CriteriaQuery<E> criteria, Root<E> root, Predicate[] predicates, Integer limit, Integer offset) {
        this.cb = cb;
        this.criteria = criteria;
        this.root = root;
        this.predicates = predicates;
        this.limit = limit;
        this.offset = offset;
    }

    public static MethodInterceptor getInterceptor(Builder original) {
        return (proxy, method, args, methodProxy) -> {
            if (Arrays.stream(args).anyMatch(arg -> Objects.isNull(arg) || StringUtils.isEmpty(arg.toString()))) {
                return proxy;
            } else if (!method.getName().equals("build")) {
                method.invoke(original, args);
                return proxy;
            }
            return method.invoke(original, args);
        };
    }
}
