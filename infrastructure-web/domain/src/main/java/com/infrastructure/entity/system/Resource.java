package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 资源实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class Resource extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -7940298036156531652L;

    private String name; // 资源名称
    private ResourceType type = ResourceType.MENU; // 资源类型
    private String icon; // 图标
    private String permission; // 权限标识符
    private String url; // URL
    private String parentId; // 上级编号
    private Boolean available = Boolean.TRUE; // 是否可用
    private String sorted; // 排序标识

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    public static enum ResourceType {
        MENU("菜单"), BUTTON("按钮");

        private final String info;

        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
}
