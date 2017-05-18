package com.infrastructure.web;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author tyq
 * @date 2016/1/14
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping
    public String login(HttpServletRequest req, Model model) throws Exception{
        // 一些登录的异常处理信息
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("cookie_login".equals(cookie.getName())){
                    model.addAttribute("login",cookie.getValue());
                    break;
                }
            }
        }
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名或密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名或密码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "重复尝试次数过多，请稍后再试";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)){
            error = "用户被锁定，无法登录";
        }else if (exceptionClassName != null) {
            //error = "其他错误：" + exceptionClassName;
            error = "系统繁忙，请稍后再试";
        }
        model.addAttribute("error", error);
        return "login";
    }
}
