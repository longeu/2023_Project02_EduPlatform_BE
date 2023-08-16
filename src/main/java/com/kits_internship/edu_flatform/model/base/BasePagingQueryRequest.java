package com.kits_internship.edu_flatform.model.base;

public class BasePagingQueryRequest {
    private Integer limit;
    private Integer page;

    public BasePagingQueryRequest() {
    }

    public int getLimit() {
        if (this.limit != null && this.limit >= 0) {
            return this.limit > 25 ? 25 : this.limit;
        } else {
            return 10;
        }
    }

    public void setLimit(int limit) {
        if (limit < 0) {
            this.limit = 10;
        } else if (limit > 25) {
            this.limit = 25;
        } else {
            this.limit = limit;
        }

    }

    public int getPage() {
        return this.page != null && this.page >= 0 ? this.page : 1;
    }

    public void setPage(int page) {
        if (page < 0) {
            this.page = 1;
        } else {
            this.page = page;
        }

    }
}