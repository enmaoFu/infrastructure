package com.infrastructure.common.utils;

import org.springframework.context.MessageSource;

/**
 * MessageUtils
 *
 * @author tyq
 * @date 2016/1/11
 */
public class MessageUtils {

    private static MessageSource messageSource;

    /**
     * 根据消息代码和参数获取格式化后的消息
     *
     * @param code 消息代码
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        if (messageSource == null)
            messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, null);
    }
}
