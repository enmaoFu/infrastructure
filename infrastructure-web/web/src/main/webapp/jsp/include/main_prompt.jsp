<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<div style='background-color: #FFFEEC;width: 100%;height: 100%;'>
    <div style="float: left;margin-left: 30%;margin-top: 2px;">
        <span style="color:gray;font-size: 12px;">监测到您不是使用的火狐浏览器,为提高体验效果，建议使用火狐浏览器!点击这里</span>
        <a href='http://6dx1.pc6.com/ee3/Firefox-setup.exe' target='_self' style="text-decoration: none;font-size: 14px;font-weight: bolder;">执行安装</a>
    </div>
    <div style="float: right;margin-top: 5px;margin-right: 30%;">
        <img src="${pageContext.request.contextPath}/static/easyui/css/icons/clear.png" alt="关闭" onclick="main_close_prompt();" style="cursor:pointer;"/>
    </div>
</div>