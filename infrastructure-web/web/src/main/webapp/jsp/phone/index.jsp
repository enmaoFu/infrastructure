<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EMP | 首页</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=2.0"/>
    <link type="text/css" href="${pageContext.request.contextPath}/static/phone/css/common/pagination.css" rel="stylesheet" media="screen"/>
</head>
<body>
欢迎<shiro:principal/>登录，<a href="${pageContext.request.contextPath}/logout">退出</a>
<div class="wrap" style="position:absolute;bottom:5px;">
    <div class="fenye">
        <ul>
            <li id="first">首页</li>
            <li id="top" onclick="topclick()">上一页</li>
            <li class="xifenye" id="xifenye">
                <a id="xiye">1</a>/<a id="mo">66</a>
                <div class="xab" id="xab" style="display:none">
                    <ul id="uljia">

                    </ul>
                </div>
            </li>
            <li id="down" onclick="downclick()">下一页</li>
            <li id="last">末页</li>
        </ul>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/phone/js/common/pagination.js"></script>
</body>
</html>
