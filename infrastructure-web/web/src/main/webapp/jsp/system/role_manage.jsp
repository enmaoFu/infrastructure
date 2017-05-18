<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link href="${ctx}/static/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<%--岗位管理--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false">
        <table id="grid_role"></table>
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

<div id="tb_role" style="padding: 8px 10px;">
    <shiro:hasPermission name="role:add">
        <a id="add_role" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:update">
        <a id="edit_role" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:delete">
        <a id="remove_role" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:resource">
        <a id="authorization" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">菜单授权</a>
    </shiro:hasPermission>
</div>
<div id="win_role" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="height:380px;width:370px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_role" method="post">
            <div data-options="region:'center',border:false" style="padding-top: 10px;" align="center">
                <input type="hidden" id="id" name="id">
                <table cellpadding="3">
                    <tr>
                        <td>岗位名称:</td>
                        <td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>岗位描述:</td>
                        <td><input class="easyui-textbox" type="text" name="description" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="easyui-textbox" type="text" name="remark" data-options="multiline:true" style="width: 200px;height: 50px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>所属企业:</td>
                        <td>
                            <input id="companyId" class="easyui-combobox" name="companyId" data-options="required:true,editable:false,valueField:'id',textField:'text'" style="width: 200px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input id="deptId" class="easyui-combobox" name="deptId" data-options="required:true,editable:false" style="width: 200px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>上级岗位:</td>
                        <td>
                            <input id="parentId" class="easyui-combobox" name="parentId" style="width: 200px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>排序标识:</td>
                        <td>
                            <input class="easyui-numberbox" name="sorted" style="width: 200px;" data-options="min:0,max:100000,prompt:'例如:101,值越小排序越靠前'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>系统管理员:</td>
                        <td>
                            <input type="checkbox" name="isSys" value="Y">
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_role">确定</a>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_role">重置</a>--%>
            </div>
        </form>
    </div>
</div>
<div id="win_tree" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:500px;padding:5px;">
    <div class="easyui-layout" style="width:100%;height:100%;">
        <div data-options="region:'center',border:false" style="padding:5px;">
            <ul id="tree" class="ztree"></ul>
        </div>
        <div data-options="region:'south',border:false" style="height:35px;">
            <div data-options="region:'south',border:false" style="text-align:right;padding:4px 2px;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_tree">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_tree">重置</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/system/role_manage.js"></script>