package com.infrastructure.common.enums;

/**
 * 性别枚举
 *
 * @author tyq
 * @date 2016/1/11
 */
public enum Gender {

    Male("男"), Female("女");

    private final String info;

    Gender(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
