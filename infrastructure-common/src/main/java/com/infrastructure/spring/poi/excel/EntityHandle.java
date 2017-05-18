package com.infrastructure.spring.poi.excel;

/**
 * EntityHandle
 *
 * @author tyq
 * @date 2016/1/11
 */
public interface EntityHandle<E> {

    /**
     * 实体处理接口
     *
     * @param e 实体对象
     * @return 实体对象
     */
    E handle(E e);
}
