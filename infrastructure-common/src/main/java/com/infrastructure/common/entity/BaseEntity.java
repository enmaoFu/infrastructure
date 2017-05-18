package com.infrastructure.common.entity;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.able.Synchronizable;

import java.io.Serializable;

/**
 * 基础实体抽象
 *
 * @param <ID> 标识类型
 * @author tyq
 */
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> implements Searchable, Synchronizable {

    /**
     * 标识
     */
    protected ID id;

    /**
     * 通用查询条件
     */
    protected String where;

    /**
     * 创建时间
     */
    protected Long createdTime;

    /**
     * 更新时间
     */
    protected Long updatedTime;

    @Override
    public ID getId() {
        return this.id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String getWhere() {
        return this.where;
    }

    @Override
    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Long getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public Long getUpdatedTime() {
        return this.updatedTime;
    }
}
