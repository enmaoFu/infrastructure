<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id='loadingDiv' style="position: absolute; z-index: 1000; top: 30px; left: 0px;width: 100%; height: 100%; background: white; text-align: center;">
    <h1 style="top: 48%; position: relative;">
        <img src="${pageContext.request.contextPath}/static/common/images/loding.gif" style="margin: -5px 5px;"><font color="#15428B">努力加载中···</font>
    </h1>
</div>
<script type="text/javascript">
    function closeLoading() {
        $("#loadingDiv").fadeOut("normal", function () {
            $(this).remove();
        });
    }
    var no;
    $.parser.onComplete = function () {
        if (no) clearTimeout(no);
        no = setTimeout(closeLoading, 300);
    }
</script>