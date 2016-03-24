package com.unicorn.core.query;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Pageable;

public class QueryInfo {

    private Predicate predicate;

    private Pageable pageable;

    public QueryInfo() {
    }

    public QueryInfo(Predicate predicate, Pageable pageable) {
        this.predicate = predicate;
        this.pageable = pageable;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
