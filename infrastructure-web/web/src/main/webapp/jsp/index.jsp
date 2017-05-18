<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <title>Infrastructure | 首页</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="cache-control" content="no-cache">
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <%-- 通用样式--%>
    <jsp:include page="./include/common-css.jsp" flush="true"/>
    <%--通用脚本--%>
    <jsp:include page="./include/common-script.jsp" flush="true"/>
</head>
<body oncontextmenu="return false">
<div class="easyui-layout" data-options="fit:true" id="main_body">
    <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">
            <!--头部-->
            <div data-options="region:'north'" style="height:60px;background-color: rgb(1,136,204); border-bottom: 4px solid #0E779B " >
                <%-- 顶部导航信息栏 --%>
                <jsp:include page="./include/nav-top.jsp" flush="true"/>
            </div>
            <!--左侧-->
            <div data-options="region:'west',split:false,border:false" class="mian_west" style="width:196px;padding: 0px;">
                <!-- 加载菜单 -->
                <jsp:include page="./include/nav-menu.jsp" flush="true"/>
            </div>
            <!--主体-->
            <div data-options="region:'center',border:false">
                <div id="tabs" class="easyui-tabs" data-options="fit:true,border:false">
                    <div title="主页" data-options="iconCls:'icon-home'" id="home" style="background-color: rgb(1,136,204);" align="center">
                        <!-- 加载主体数据 -->
                        <jsp:include page="./include/main1.jsp" flush="true"/>
                        <div style="margin-top: 20%;">
                            <span style="font-size: 50px;color: white;">INFRASTRUCTURE-SYSTEM</span>
                        </div>
                    </div>
                </div>
                <div id="tabs_right_menu" class="easyui-menu" style="width:100px;">
                    <div id="tabs_close" data-options="iconCls:'icon-clear'">全部关闭</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/index.js"></script>
</body>
</html>