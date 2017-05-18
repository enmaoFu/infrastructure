package com.infrastructure.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.entity.system.Resource;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.system.UserLoginTime;
import com.infrastructure.service.system.IResourceService;
import com.infrastructure.service.system.ISysUserService;
import com.infrastructure.service.system.IUserLoginTimeService;
import com.infrastructure.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 系统用户PATH过滤器
 *
 * <p/>
 * 用于在URL地址访问前判断SESSION中的用户信息是否存在
 *
 * @author tyq
 * @data 2016/1/14
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private ISysUserService usv;
    @Autowired
    private IResourceService resv;
    @Autowired
    private IUserLoginTimeService userLoginTimeService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 获取当前登录用户名
        String login = (String) SecurityUtils.getSubject().getPrincipal();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Cookie cookie = new Cookie("cookie_login",login);
        resp.addCookie(cookie);
        // 获取 Session 的过期时间
        Long expired = (Long) session.getAttribute(Constants.DEFAULT_EXPIRED_TIME_SESSION);
        // 初次登录 session过期 (默认过期时间30分钟)
        if (expired == null || (expired != null && System.currentTimeMillis() - expired > Constants.DEFAULT_EXPIRED_TIME)) {
            //根据登录名查询用户信息
            SysUser user = usv.getUser(login);
            if(user != null){
                Set<String> permissions = usv.getPermissions(login);
                List<Resource> menus = resv.getMenus(permissions);
                //保存当前用户登录时间
                UserLoginTime userLoginTime = new UserLoginTime();
                userLoginTime.setId(IdKeyGenerator.uuid());
                userLoginTime.setUserId(user.getId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userLoginTime.setLcTime(sdf.format(Calendar.getInstance().getTime()));
                userLoginTimeService.addUserLoginTime(userLoginTime);
                session.setAttribute(Constants.DEFAULT_USER_MENU_SESSION, JSON.toJSON(menus));
                session.setAttribute(Constants.DEFAULT_USER_INFO_SESSION, user);
                // 查询时间
                session.setAttribute(Constants.DEFAULT_EXPIRED_TIME_SESSION, System.currentTimeMillis());
            }
        }
        return true;
    }

}
