package com.infrastructure.common.service;

import com.infrastructure.common.entity.AbstractEntity;
import com.infrastructure.common.repository.BaseMapper;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 通用业务抽象
 *
 * @param <E> 实体类型
 * @param <ID> 标识类型
 * @author tyq
 */
public abstract class BaseService<E extends AbstractEntity<ID>, ID extends Serializable> implements IBaseService<E, ID> {

    /**
     * 获取MAPPER
     *
     * @return
     */
    public abstract BaseMapper<E, ID> getMapper();

    /**
     * 查询实体
     *
     * @param id 标识
     * @return
     */
    public E select(ID id) {
        return getMapper().select(id);
    }

    /**
     * 插入实体
     *
     * @param e
     * @return
     */
    public int insert(E e) {
        // 增加创建时间戳
        e.setCreatedTime(Calendar.getInstance().getTimeInMillis());
        return getMapper().insert(e);
    }

    /**
     * 更新实体
     *
     * @param e
     * @return
     */
    public int update(E e) {
        // 增加更新时间戳
        e.setUpdatedTime(Calendar.getInstance().getTimeInMillis());
        return getMapper().update(e);
    }

    /**
     * 删除实体
     *
     * @param id
     * @return
     */
    public int delete(ID id) {
        return getMapper().delete(id);
    }
}
