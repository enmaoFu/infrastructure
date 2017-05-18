<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.infrastructure.entity.system.SysUser,com.infrastructure.util.Constants" %>
<%@ page import="javax.mail.Session" %>
<%
    // 不缓存
    //request.setAttribute("decorator", "none");
    //response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    //response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    //response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
    // 如果用户已经登录直接跳转主页
    if (user != null) {
        response.sendRedirect(request.getContextPath());
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh" class="no-js">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="cache-control" content="no-cache">
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <title>Infrastructure | 登录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/js/browser.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/index/js/demo.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/index/js/jquery.particleground.min.js"></script>
</head>
<body oncontextmenu="return false">
<div style='background-color: #FFFEEC;width: 100%;height:30px;display: none;' id="prompt">
    <div style="float: left;margin-left: 30%;margin-top: 2px;">
        <span style="color:gray;font-size: 12px;">监测到您不是使用的火狐浏览器,为提高体验效果，建议使用火狐浏览器!点击这里</span>
        <a href='http://6dx1.pc6.com/ee3/Firefox-setup.exe' target='_self' style="text-decoration: none;font-size: 14px;font-weight: bolder;">执行安装</a>
    </div>
    <div style="float: right;margin-top: 5px;margin-right: 30%;">
        <img src="${pageContext.request.contextPath}/static/easyui/css/icons/clear.png" alt="关闭" id="close_prompt" style="cursor:pointer;"/>
    </div>
</div>
<div id="particles">
    <div class="intro">
        <div class="main_intro">
            <div class="dlck">
                <div class="main_logo">
                    <%--<img src="${pageContext.request.contextPath}/static/index/images/logo111.png" width="243">--%>
                    <span style="font-size: 20px;">INFRASTRUCTURE</span>
                </div>
                <div class="login_wrap">
                    <form method="post" action="${pageContext.request.contextPath}/login" class="form">
                        <input type="text" placeholder="请输入您的用户名" value="${login}" class="placeholder" id="userName" name="login" style="color:#fff;" />
                        <input type="password" placeholder="请输入您的密码" class="placeholder" id="password" name="password" style="color:#fff;" />
                        <label>
                            <input type="checkbox" id="rememberMe" name="rememberMe" value="true" tabindex="4" checked/>
                            <span>记住我</span>
                        </label>
                        <button id="login-button" type="button">登录</button>
                        <br/>
                        <span style="color: red;" id="error">${error}</span>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <div class="foot">
            <p>CopyRight@2015 ALL Rights Reserved | 版权所有：重庆朕尔科技集团益朕软件有限公司 渝ICP备:13004295号-8</p>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/common/js/jquery.base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login.js"></script>
</body>
</html>
