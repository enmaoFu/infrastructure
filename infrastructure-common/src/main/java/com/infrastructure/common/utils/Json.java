package com.infrastructure.common.utils;

import java.io.Serializable;

/**
 * Json 返回模型
 */
public class Json implements Serializable {

    private static final long serialVersionUID = -507061077155757745L;

    private Boolean success = Boolean.FALSE;
    private String message;
    private String status;
    private Object obj;

    public Json() {
        this.message = "操作失败";
    }

    public Json(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
