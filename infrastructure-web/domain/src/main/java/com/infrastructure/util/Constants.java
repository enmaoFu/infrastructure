package com.infrastructure.util;

/**
 * 系统常量定义
 */
public interface Constants {

    /**
     * 默认的用户信息
     */
    String DEFAULT_USER_INFO_SESSION = "userinfo_session";

    /**
     * 默认的用户菜单
     */
    String DEFAULT_USER_MENU_SESSION = "menus";

    /**
     * 默认过期时间 Session 存储键
     */
    String DEFAULT_EXPIRED_TIME_SESSION = "expired_time_session";

    /**
     * 默认过期时间为 30分钟
     */
    Long DEFAULT_EXPIRED_TIME = 1000 * 60 * 60 * 1L;

}
