package com.infrastructure.shiro.realm;

import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.ILoginService;
import com.infrastructure.service.system.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;

/**
 * 自定义Realm授权与验证实现
 *
 * @author tyq 2016/1/14
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    @Qualifier("localLoginService")
    private ILoginService lsv;
    @Autowired
    private ISysUserService usv;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String login = (String) principals.getPrimaryPrincipal();
        // 授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = usv.getRoles(login);
        Set<String> permissions = usv.getPermissions(login);
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String login = (String) token.getPrincipal();
        SysUser user = lsv.findSysUserByLogin(login);
        //TODO 异常消息处理机制完善
        if (user == null)
            throw new UnknownAccountException("未知用户");
        if (!user.getAvailable())
            throw new LockedAccountException("该用户已经被锁定");
        // 认证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(login, user.getPassword(), getName());
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}

