<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link href="${ctx}/static/index/css/jquery-accordion-menu.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/index/css/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/index/css/nav-menu.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/index/js/jquery-accordion-menu.js" type="text/javascript"></script>
<div class="content">
    <div id="jquery-accordion-menu" class="jquery-accordion-menu blue">
        <div id="menu_time" style="background: url('${ctx}/static/index/images/main_time.png') repeat-x top center; height: 29px; line-height: 29px; text-align: center; color: #1c5ca4; font-size: 12px;"></div>
        <ul id="demo-list"></ul>
    </div>
</div>
<script type="text/javascript">
    var PATH = '${ctx}';
    var menus = '${menus}';
</script>
<script type="text/javascript" src="${ctx}/static/index/js/nav-menu.js"></script>