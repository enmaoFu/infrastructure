package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 用户角色实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class SysUserRole extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = -3832224875498991000L;

    private String userId; // 用户标识

    private String roleId; // 角色标识

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
