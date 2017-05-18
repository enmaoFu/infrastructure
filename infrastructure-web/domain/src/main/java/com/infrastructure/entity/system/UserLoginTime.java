package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 记录用户登录/模块点击时间
 * Created by suyl on 2016/5/23.
 */
public class UserLoginTime extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 模块ID
     */
    private String model;
    /**
     * 登录时间/模块点击时间
     */
    private String lcTime;

    public UserLoginTime() {
        super();
    }

    public UserLoginTime(String userId, String model, String lcTime) {
        this.userId = userId;
        this.model = model;
        this.lcTime = lcTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLcTime() {
        return lcTime;
    }

    public void setLcTime(String lcTime) {
        this.lcTime = lcTime;
    }

    @Override
    public String toString() {
        return "UserLoginTime{" +
                "userId='" + userId + '\'' +
                ", model='" + model + '\'' +
                ", lcTime='" + lcTime + '\'' +
                '}';
    }
}
