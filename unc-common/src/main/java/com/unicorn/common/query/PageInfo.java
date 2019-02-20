package com.unicorn.common.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {

    private Integer page = 1;

    private Integer pageSize = Integer.MAX_VALUE;

    public PageInfo() {
    }

    public PageInfo(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
