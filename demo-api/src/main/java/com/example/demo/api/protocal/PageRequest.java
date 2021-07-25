package com.example.demo.api.protocal;


import java.io.Serializable;

public class PageRequest implements Serializable {

    private final Integer DEFAULT_CURRENT = 1;
    private final Integer DEFAULT_PAGE_SIZE = 8;

    private Integer current;
    private Integer pageSize;

    public Integer getCurrent() {
        return current == null ? DEFAULT_CURRENT : current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
