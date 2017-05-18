package com.infrastructure.enums;

/**
 * 目标类型枚举
 *
 * @author suyl
 * @data 2016/2/18
 */
public enum TargetType implements IDescription {

    /**
     * 经济目标
     */
    TYPE_ECONOMIC_GOALS("type_economic_goals"),

    /**
     * 管理目标
     */
    TYPE_MANAGEMENT_OBJECTIVES("type_management_objectives");

    private final String description;

    TargetType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
