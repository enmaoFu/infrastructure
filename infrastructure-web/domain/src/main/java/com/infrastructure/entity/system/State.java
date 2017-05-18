package com.infrastructure.entity.system;

/**
 *
 * 状态
 * Created by tyq 2016/1/14
 */
public enum State {


    UNCONFIRMED("未启用", 0),
    USABLE("启用", 1),
    DISABLE("禁用", 2),
    REMOVE("删除", 3),
    UNSET("未设置", 4);

    private String name;
    private int value;

    State(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public int getValue() {
        return this.value;
    }


}
