package com.extra.jquery.model;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tyq on 16/1/10.
 */
public class JQueryDataGrid<T> implements Serializable {

    private Long total;//总记录数
    private List<T> rows = Lists.newArrayList(); // 数据结果集

    public JQueryDataGrid(Long total,List<T> rows){
        this.total = total;
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
