<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<%--企业管理--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false">
        <table id="company_grid"></table>
    </div>
    <div data-options="region:'east',split:true,border:false" title="制度/通知公告" style="width:200px;">
        <span style="color: #ea8557">制度：</span>
        <div>
            ${regime.content}
            <br>
            <a href='${ctx}/regime/download/${regime.id}'>${regime.sysFile.originalFileName}</a>
        </div>
        <p style="border-bottom: 1px solid #27CCE4;"></p>
        <span style="color: #ea8557">通知通告：</span>
        <div>
            ${annou.content}
            <br>
            <a href='${ctx}/announcement/download/${annou.id}'>${annou.sysFile.originalFileName}</a>
        </div>
    </div>
</div>

<div id="tb_company" style="padding: 8px 10px;">
    <shiro:hasPermission name="company:add">
        <a id="add_company" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="company:addparent">
        <a id="add_company_parent" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加上级</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="company:addChildren">
        <a id="add_company_children" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加下级</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="company:update">
        <a id="edit_company" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="company:delete">
        <a id="remove_company" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
</div>

<div id="win_company" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:370px;height:300px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_company" method="post">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <input type="hidden" id="id" name="id">
                <input type="hidden" id="parent" name="parent">
                <input type="hidden" id="parents" name="parents">
                <table cellpadding="2">
                    <tr>
                        <td>企业名称:</td>
                        <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,width:200" /></td>
                    </tr>
                    <tr>
                        <td>简称:</td>
                        <td><input class="easyui-textbox" type="text" name="abbreviation" data-options="width:200" /></td>
                    </tr>
                    <tr>
                        <td>地址:</td>
                        <td><input class="easyui-textbox" type="text" name="address" data-options="width:200" /></td>
                    </tr>
                    <tr>
                        <td>经营范围:</td>
                        <td><input class="easyui-textbox" type="text" name="motto" data-options="multiline:true,width:200,height:60" /></td>
                    </tr>
                    <tr>
                        <td>法定代表:</td>
                        <td><input class="easyui-textbox" type="text" name="master" data-options="width:200" /></td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_company">确定</a>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_company">重置</a>--%>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var login = '${sessionScope.userinfo_session.login}';
</script>
<script type="text/javascript" src="${ctx}/static/system/company_manage.js"></script>
