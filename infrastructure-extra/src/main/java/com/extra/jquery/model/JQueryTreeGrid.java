package com.extra.jquery.model;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by tyq on 16/3/20.
 */
public class JQueryTreeGrid<T> implements Serializable {

    private Long total;//总记录数
    private List<T> rows = Lists.newArrayList(); // 数据结果集
    private List<T> footer = Lists.newArrayList();

    public JQueryTreeGrid(Long total, List<T> rows, List<T> footer) {
        this.total = total;
        this.rows = rows;
        this.footer = footer;
    }

    public JQueryTreeGrid(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<T> getFooter() {
        return footer;
    }

    public void setFooter(List<T> footer) {
        this.footer = footer;
    }
}
