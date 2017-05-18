package com.infrastructure.enums;

/**
 * 用户类型
 *
 * @author tyq
 * @data 2016/1/14
 */
public enum CustomerKinds implements IDescription {

    Parent("家长"),

    Student("学生"),

    Teacher("教职工"),

    Administrator("管理员"),

    Informer("通知账户");

    private final String description;

    CustomerKinds(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static CustomerKinds get(String str) {
        for (CustomerKinds ck : CustomerKinds.values()) {
            if (ck.getDescription().equals(str))
                return ck;
        }
        return null;
    }
}
