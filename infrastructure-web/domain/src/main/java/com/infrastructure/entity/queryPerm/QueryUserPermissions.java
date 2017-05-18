package com.infrastructure.entity.queryPerm;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询权限实体
 * Created by suyl on 2016/2/25.
 */
public class QueryUserPermissions extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    /**
     * 权限查询编号
     */
    private String permId;

    /**
     * 用户编号
     */
    private String userId;

    public QueryUserPermissions() {
    }

    public QueryUserPermissions(String permId, String userId) {
        this.permId = permId;
        this.userId = userId;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "queryUserPermissions{" +
                "permId='" + permId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
