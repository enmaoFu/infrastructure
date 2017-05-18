package com.infrastructure.spring.bind.annotation;

import com.infrastructure.util.Constants;

import java.lang.annotation.*;

/**
 * 当前用户注解
 *
 * @author tyq
 * @data 2016/1/14
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在Session中的名字
     *
     * @return
     */
    String value() default Constants.DEFAULT_USER_INFO_SESSION;
}
