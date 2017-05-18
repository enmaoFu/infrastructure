package com.infrastructure.util;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by suyl on 2016/5/3.
 */
public class PhoneCode implements Serializable{
    private String code;
    private String phone;
    private Date createTime;

    public PhoneCode() {
    }

    public PhoneCode(String code, String phone, Date createTime) {
        this.code = code;
        this.phone = phone;
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PhoneCode{" +
                "code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhoneCode other = (PhoneCode) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        return true;
    }
}