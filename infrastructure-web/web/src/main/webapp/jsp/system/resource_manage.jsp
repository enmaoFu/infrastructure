<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<%--资源管理--%>
<table id="grid_resource"></table>

<div id="tb_resource" style="padding: 8px 10px;">
    <shiro:hasPermission name="resource:addChildren">
        <a id="add_resource" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="display: none;">添加下级</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="resource:update">
        <a id="edit_resource" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="resource:delete">
        <a id="remove_resource" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
</div>
<%--资源新增/修改--%>
<div id="win_resource" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:400px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_resource" method="post">
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <input type="hidden" id="id" name="id">
                <input type="hidden" id="parentId" name="parentId">
                <table cellpadding="5">
                    <tr>
                        <td>资源名称:</td>
                        <td><input class="easyui-textbox"  name="name" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>资源类型:</td>
                        <td>
                            <div class="easyui-radio" id="radio">
                                <label><input type="radio" name="type" value="MENU" checked /> 菜单</label>&nbsp;&nbsp;
                                <label><input type="radio" name="type" value="BUTTON" /> 按钮</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>图标:</td>
                        <td><input class="easyui-textbox" name="icon" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>权限标识符:</td>
                        <td><input class="easyui-textbox" name="permission" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>请求地址:</td>
                        <td><input class="easyui-textbox" name="url" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>排序编号:</td>
                        <td><input class="easyui-textbox" name="sorted" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_resource">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_resource">重置</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/system/resource_manage.js"></script>