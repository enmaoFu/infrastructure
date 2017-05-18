package com.infrastructure.service.system;

import com.infrastructure.entity.system.SysUser;

/**
 * 登录业务接口
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface ILoginService {

    SysUser findSysUserByLogin(String login);
}
