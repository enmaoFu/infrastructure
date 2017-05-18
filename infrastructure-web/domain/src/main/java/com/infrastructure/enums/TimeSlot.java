package com.infrastructure.enums;

/**
 * 目标时间段枚举
 *
 * @author suyl
 * @data 2016/2/18
 */
public enum TimeSlot implements IDescription {

    /**
     * 年
     */
    SLOT_YEAR("slot_year"),

    /**
     * 半年
     */
    SLOT_HALF_YEAR("slot_half_year");

    private final String description;

    TimeSlot(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
