package com.infrastructure.common.exception;

/**
 * 异常基础实体
 *
 * <p>
 * 所有系统的异常类都应该基于此实体
 * </p>
 *
 * @author tyq
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8949571585387105952L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
