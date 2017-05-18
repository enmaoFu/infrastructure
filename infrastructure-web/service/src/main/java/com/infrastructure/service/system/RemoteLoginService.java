package com.infrastructure.service.system;

import com.infrastructure.entity.system.SysUser;
import org.springframework.stereotype.Service;

/**
 * 远端登陆实现
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class RemoteLoginService implements ILoginService {

    @Override
    public SysUser findSysUserByLogin(String login) {
        return null;
    }
}
