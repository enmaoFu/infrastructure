package com.infrastructure.spring.bind.annotation;

import java.lang.annotation.*;

/**
 * 搜索注解
 *
 * @author tyq
 * @date 2016/1/14
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SearchParam {

    /**
     * 实体类型
     *
     * @return
     */
     Class<?> value() default Object.class;
}
