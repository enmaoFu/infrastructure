package com.infrastructure.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 验证结果辅助类
 *
 * @author tyq
 * @date 2016/1/14
 */
public abstract class BindingResultHelper {

    /**
     * 遍历拼接错误消息
     *
     * @param result
     * @return
     */
    public static String getErrorMessages(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                sb.append(error.getDefaultMessage()).append("\n");
            }
            return sb.toString();
        }
        return null;
    }
}
