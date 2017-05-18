package com.infrastructure.spring.poi.excel.render;

import com.infrastructure.spring.poi.excel.CellRender;

import java.lang.reflect.Field;

/**
 * 默认的值渲染器
 *
 * @author tyq
 * @date 2016/1/11
 */
public class DefaultCellRender implements CellRender {
    @Override
    public Object render(Field field, Object value) {
        return value;
    }
}
