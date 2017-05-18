package com.infrastructure.common.entity;

import java.io.Serializable;

/**
 * 基础实体抽象定义
 *
 * @param <ID>
 * @author tyq
 */
public abstract class AbstractEntity<ID extends Serializable> {

    /**
     * 获取标识
     *
     * @return
     */
    public abstract ID getId();

    /**
     * 设置标识
     *
     * @param id 标识
     */
    public abstract void setId(ID id);

    /**
     * 创建时间戳
     *
     * @param createdTime
     */
    public abstract void setCreatedTime(Long createdTime);

    /**
     * 获取创建时间戳
     *
     * @return
     */
    public abstract Long getCreatedTime();

    /**
     * 更新时间戳
     *
     * @param updatedTime
     */
    public abstract void setUpdatedTime(Long updatedTime);

    /**
     * 获取更新时间戳
     *
     * @return
     */
    public abstract Long getUpdatedTime();
}
