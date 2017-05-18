package com.infrastructure.service.system;

import com.infrastructure.dao.system.SysUserMapper;
import com.infrastructure.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地登陆实现
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class LocalLoginService implements ILoginService {

    @Autowired
    private SysUserMapper sump;

    @Override
    public SysUser findSysUserByLogin(String login) {
        return sump.findSysUserByLogin(login);
    }
}
