package com.unicorn.common.query;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;

public class QueryInfo {

    private Predicate predicate;

    private PageInfo pageInfo;

    private Sort sort;

    public QueryInfo(Predicate predicate, PageInfo pageInfo, Sort sort) {

        this.predicate = predicate;
        this.pageInfo = pageInfo;
        this.sort = sort;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public Sort getSort() {
        return sort;
    }
}
