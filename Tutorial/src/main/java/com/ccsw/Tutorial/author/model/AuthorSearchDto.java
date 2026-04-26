package com.ccsw.Tutorial.author.model;

import com.ccsw.Tutorial.common.pagination.PageableRequest;

public class AuthorSearchDto {
    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
