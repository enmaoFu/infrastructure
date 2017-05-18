package com.infrastructure.common.service;


import com.infrastructure.common.entity.AbstractEntity;

import java.io.Serializable;

/**
 * 通用服务接口
 *
 * @author tyq
 * @date tyq
 */
public interface IBaseService<E extends AbstractEntity<ID>, ID extends Serializable> {
    /**
     * 查询实体
     *
     * @param id 标识
     * @return
     */
    E select(ID id);

    /**
     * 插入实体
     *
     * @param e
     * @return
     */
    int insert(E e);

    /**
     * 更新实体
     *
     * @param e
     * @return
     */
    int update(E e);

    /**
     * 删除实体
     *
     * @param id
     * @return
     */
    int delete(ID id);
}
