package com.infrastructure.utils;

/**
 * @ClassName(类名) : ReturnData
 * @Description(描述) : 信息返回
 * @author(作者) ：suyuanliu
 */
public class ReturnAppData<T> {
    private String msg;
    private String code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
