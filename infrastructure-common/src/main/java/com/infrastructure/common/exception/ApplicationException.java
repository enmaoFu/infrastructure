package com.infrastructure.common.exception;

import com.infrastructure.common.utils.MessageUtils;
import org.springframework.util.StringUtils;

/**
 * 应用异常
 *
 * @author tyq
 * @date 2016/1/11
 */
public class ApplicationException extends BaseException {

    private static final long serialVersionUID = -2590637780210404264L;

    private String module; // 模块
    private String code; // 错误码
    private Object[] params; // 参数数组
    private String defaultMessage; // 默认异常消息

    public ApplicationException(String module, String code, Object[] params, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.params = params;
        this.defaultMessage = defaultMessage;
    }

    public ApplicationException(String module, String code, Object[] params) {
        this(module, code, params, null);
    }

    public ApplicationException(String code, Object[] params) {
        this(null, code, params, null);
    }

    public ApplicationException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public ApplicationException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, params);
        }
        if (message == null)
            message = defaultMessage;
        return message;
    }

    @Override
    public String toString() {
        return this.getClass() + "{ module='" + module + "', message='" + getMessage() + "' }";
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
