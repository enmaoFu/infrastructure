package com.infrastructure.spring.poi.excel;

import java.lang.reflect.Field;

/**
 * 单元格值渲染接口
 *
 * @author tyq
 * @date 2016/1/11
 */
public interface CellRender {

    /**
     * 渲染
     *
     * @param field 字段
     * @param value 值
     * @return 最终值
     */
    Object render(Field field, Object value);
}
