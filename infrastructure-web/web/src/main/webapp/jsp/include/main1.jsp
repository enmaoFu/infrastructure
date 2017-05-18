<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link type="text/css" href="${ctx}/static/index/css/index.css" rel="stylesheet" media="screen"/>
<link type="text/css" href="${ctx}/static/index/css/documentation.css" rel="stylesheet" media="screen"/>
<link type="text/css" href="${ctx}/static/index/css/jalendar.css" rel="stylesheet" media="screen"/>

<jsp:include page="./main_div.jsp" flush="true"></jsp:include>

<script type="text/javascript">
    var strategy = '${strategy}';
</script>
<script type="text/javascript" src="${ctx}/static/index/js/jalendar.js"></script>
<script type="text/javascript" src="${ctx}/static/index/js/main.js"></script>