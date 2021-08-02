package com.project.localbatter.components;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PagingUtil {

    private final EntityManager entityManager;

    private Querydsl getQuerydsl(Class clazz) {
        PathBuilder<?> builder = new PathBuilderFactory().create(clazz);
        return new Querydsl(entityManager, builder);
    }

    public <T> Page<T> getPageImpl(Pageable pageable, JPQLQuery<T> query, Class clazz) {
        long totalCount = query.fetchCount();
        List<T> results = getQuerydsl(clazz).applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public <T> Page<T> getPageImplList(Pageable pageable, List<T> items, Class clazz) {
        return new PageImpl<>(items, pageable, items.size());
    }
}
