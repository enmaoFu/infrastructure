<%@ page import="com.infrastructure.entity.system.SysUser" %>
<%@ page import="com.infrastructure.util.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
    // 如果用户已经登录直接跳转手机端主页
    if (user != null) {
        response.sendRedirect(request.getContextPath()+"/phone");
    }
    request.getSession().invalidate();
%>
<html>
<head>
    <title>EMP | 登录</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=2.0"/>
</head>
<body>
<div align="center">
    手机端登录页面:
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="text" placeholder="请输入您的用户名" value="${login}" id="userName" name="login" /><br>
        <input type="text" placeholder="请输入您的密码" class="placeholder" name="password" /><br>
        <label>
            <input type="checkbox" id="rememberMe" name="rememberMe" value="true" tabindex="4" checked/>
            <span>记住我</span>
        </label><br>
        <button id="login-button" type="submit">登录</button>
        <br/>
        <span style="color: red;" id="error">${error}</span>
    </form>
</div>
</body>
</html>
