package com.infrastructure.common.utils;

import java.util.UUID;

/**
 * IdKeyGenerator
 *
 * @author tyq
 * @date 2016/1/11
 */
public abstract class IdKeyGenerator {

    /**
     * 获取UUID
     *
     * @return 32位uuid
     */
    public static final String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase().replaceAll("-","");
    }
}