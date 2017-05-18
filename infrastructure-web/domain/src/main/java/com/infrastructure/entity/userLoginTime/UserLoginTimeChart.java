package com.infrastructure.entity.userLoginTime;


import com.infrastructure.common.entity.BaseEntity;

/**
 * 用户登录统计图表
 * Created by tyq on 2016/6/7.
 */
public class UserLoginTimeChart extends BaseEntity<String> {

    private String lcTime;//时间
    private Integer yAxis;//纵坐标
    private Integer value;//值

    public String getLcTime() {
        return lcTime;
    }

    public void setLcTime(String lcTime) {
        this.lcTime = lcTime;
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
