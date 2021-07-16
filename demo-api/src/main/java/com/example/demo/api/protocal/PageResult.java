package com.example.demo.api.protocal;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private List<T> list;
    private int current;
    private int pageSize;
    private int total;

    public static<T> PageResult<T> emptyList() {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setList(Collections.emptyList());
        pageResult.setCurrent(0);
        pageResult.setPageSize(0);
        pageResult.setTotal(0);
        return pageResult;
    }

    public static<T> PageResult<T> buildPageResult(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.emptyList();
        }
        PageInfo page = new PageInfo(list);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrent(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal((int) page.getTotal());
        return pageResult;
    }

}
