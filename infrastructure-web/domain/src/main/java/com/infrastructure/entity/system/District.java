package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 地区实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class District extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 3152070409618478822L;

    private String code; // 行政区域代码

    private String district; // 地区名称

    private Short level; // 地区层级

    private String parent; // 上级地区代码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
