package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by tyq on 2015/8/26.
 */
public class Select extends BaseEntity<String> implements Serializable{
    private static final long serialVersionUID = -2761116433088437635L;

    private String s_key;
    private String s_val;
    private String s_pkey;
    private String s_mark;//标记
    private String s_remark;//描述
    private String companyId;//公司ID
    private Integer state = 1;//状态

    public String getS_key() {
        return s_key;
    }

    public void setS_key(String s_key) {
        this.s_key = s_key;
    }

    public String getS_val() {
        return s_val;
    }

    public void setS_val(String s_val) {
        this.s_val = s_val;
    }

    public String getS_pkey() {
        return s_pkey;
    }

    public void setS_pkey(String s_pkey) {
        this.s_pkey = s_pkey;
    }

    public String getS_mark() {
        return s_mark;
    }

    public void setS_mark(String s_mark) {
        this.s_mark = s_mark;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getS_remark() {
        return s_remark;
    }

    public void setS_remark(String s_remark) {
        this.s_remark = s_remark;
    }

    public String getCompanyId() {return companyId;}

    public void setCompanyId(String companyId) {this.companyId = companyId;}
}
