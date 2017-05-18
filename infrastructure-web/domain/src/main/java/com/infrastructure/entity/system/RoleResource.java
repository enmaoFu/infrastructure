package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.common.utils.IdKeyGenerator;

import java.io.Serializable;

/**
 * RoleResource
 *
 * @author tyq
 * @date 2016/1/14
 */
public class RoleResource extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = 6580578821768511657L;

    private String roleId;

    private String resId;

    public RoleResource() {
        this.id = IdKeyGenerator.uuid();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}
