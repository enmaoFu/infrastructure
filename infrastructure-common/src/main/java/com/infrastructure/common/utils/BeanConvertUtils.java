package com.infrastructure.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * BeanConvertUtils
 *
 * @author tyq
 * @date 2016/1/11
 */
public abstract class BeanConvertUtils {

    /**
     * Bean 转换实现
     *
     * @param source 源实体
     * @param targetClass 目标实体类型
     * @param sourceFields 源实体字段
     * @param targetFields 目标实体字段
     *
     * @param <S> 源实体泛型标识
     * @param <T> 目标实体泛型标识
     * @return
     */
    public static final <S, T> T convert(S source, Class<T> targetClass, String[] sourceFields, String[] targetFields) {
        if (sourceFields != null && sourceFields.length > 0 && targetFields != null && targetFields.length == sourceFields.length) {
            T target = BeanUtils.instantiate(targetClass);
            for (int i = 0, len = sourceFields.length; i < len; i++) {
                String sf = sourceFields[i];
                String tf = targetFields[i];
                Field thisField = ReflectionUtils.findField(source.getClass(), sf);
                Field thatField = ReflectionUtils.findField(targetClass, tf);
                if (thisField == null || thatField == null) continue;
                ReflectionUtils.makeAccessible(thisField);
                Object value = ReflectionUtils.getField(thisField, source);
                if (thisField.getType() == thatField.getType() && value != null) {
                    ReflectionUtils.makeAccessible(thatField);
                    ReflectionUtils.setField(thatField, target, value);
                }
            }
            return target;
        }
        return null;
    }

}
