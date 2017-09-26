package com.unicorn.core.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageInfo {

    private Integer pageNo = 1;

    private Integer pageSize = Integer.MAX_VALUE;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
