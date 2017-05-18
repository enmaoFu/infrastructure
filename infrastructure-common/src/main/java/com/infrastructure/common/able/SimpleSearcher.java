package com.infrastructure.common.able;

import java.io.Serializable;

/**
 * 简单搜索条件
 *
 * @author tyq
 * @date 2016/1/11
 */
public class SimpleSearcher implements Searchable, Serializable {

    private static final long serialVersionUID = 1016525497309907446L;

    /**
     * 查询条件
     */
    private String where;

    @Override
    public String getWhere() {
        return where;
    }

    @Override
    public void setWhere(String where) {
        this.where = where;
    }
}
