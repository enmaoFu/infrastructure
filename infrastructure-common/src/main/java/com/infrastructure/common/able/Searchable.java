package com.infrastructure.common.able;

/**
 * 搜索接口
 *
 * @author tyq
 * @date 2016/1/11
 */
public interface Searchable {

    /**
     * 获取查询条件
     *
     * @return
     */
    String getWhere();

    /**
     * 设置查询条件
     *
     * @param where
     */
    void setWhere(String where);
}
