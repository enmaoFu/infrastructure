package com.infrastructure.entity.main;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by 谭永强 on 2016/5/6.
 */
public class IsView extends BaseEntity<String> implements Serializable {

    private String viewId;//已查看数据ID
    private String userId;//查看用户ID
    private String createTime;//查看时间
    private String isView;//查看状态 Y已查看

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }
}
