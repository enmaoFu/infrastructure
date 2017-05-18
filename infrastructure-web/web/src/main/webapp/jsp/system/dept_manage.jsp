<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<%--岗位管理--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false">
        <table id="grid_dept"></table>
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

<!--toolbar-->
<div id="tb_dept" style="padding: 8px 10px;">
    <shiro:hasPermission name="dept:add">
        <a id="add_dept" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="dept:update">
        <a id="edit_dept" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="dept:delete">
        <a id="remove_dept" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
</div>
<!--window-->
<div id="win_dept" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:370px;height:280px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_dept" method="post">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <input type="hidden" id="id" name="id">
                <table cellpadding="3">
                    <tr>
                        <td>部门名称:</td>
                        <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,width:200" /></td>
                    </tr>
                    <tr>
                        <td>部门编号:</td>
                        <td><input class="easyui-textbox" type="text" name="code" data-options="width:200" /></td>
                    </tr>
                    <tr>
                        <td>所属企业:</td>
                        <td>
                            <input id="companyId" class="easyui-combobox" name="companyId" data-options="required:true,editable:false,width:200,valueField:'id',textField:'text'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>上级部门:</td>
                        <td>
                            <input id="parentId" class="easyui-combobox" name="parentId" data-options="editable:false,width:200" />
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="easyui-textbox" type="text" name="remark" data-options="multiline:true,width:200,height:60" />
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_dept">确定</a>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_dept">重置</a>--%>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${ctx}/static/system/dept_manage.js"></script>