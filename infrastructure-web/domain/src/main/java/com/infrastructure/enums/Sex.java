package com.infrastructure.enums;

/**
 * 性别枚举
 *
 * @author tyq
 * @data 2016/1/14
 */
public enum Sex implements IDescription {

    /**
     * 女
     */
    Female("女"),

    /**
     * 男
     */
    Male("男"),

    /**
     * 未知
     */
    Unknown("未知");

    private final String description;

    Sex(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
