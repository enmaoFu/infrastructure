package com.infrastructure.exception.handler;

import com.infrastructure.common.utils.Json;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 默认异常处理器
 *
 * @author tyq
 * @date 2016/1/14
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * SHIRO 没有权限异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public Json processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        Json json = new Json();
        json.setSuccess(false);
        json.setStatus("exception");
        json.setMessage("没有权限 " + e.getMessage());
        json.setObj(e.getClass().getName());

        return json;
    }
}
