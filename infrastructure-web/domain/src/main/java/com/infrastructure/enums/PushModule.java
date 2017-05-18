package com.infrastructure.enums;

/**
 * 推送模块枚举
 *
 * @author tyq
 * @date 2016/1/14
 */
public enum PushModule implements IDescription {

    REPORT("信息报送"),

    NOTICE("通知"),

    DOCUMENT("公文"),

    VERIFY("验证");

    private final String description;

    PushModule(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
