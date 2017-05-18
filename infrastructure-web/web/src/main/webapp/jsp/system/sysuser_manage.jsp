<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link href="${ctx}/static/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<%--用户管理--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false">
        <table id="sysuser_grid"></table>
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

<div id="toolbar" style="padding: 8px 10px;">
    <div style="float: left;">
        <shiro:hasPermission name="sysuser:add">
            <a id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="sysuser:update">
            <a id="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="sysuser:delete">
            <a id="remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="sysuser:role">
            <a id="select_role" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">授予岗位</a>
        </shiro:hasPermission>
    </div>
    <div align="right">
        <input id="select" class="easyui-combobox" style="width: 100px;">
        <input id="search" name="search" class="easyui-textbox" style="width: 100px;">
        <a id="search_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
    </div>
</div>
<div id="win" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:450px;height:470px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form" method="post">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <input type="hidden" name="id">
                <table cellpadding="3">
                    <tr>
                        <td>登录名:</td>
                        <td>
                            <input class="easyui-textbox" id="prefix" name="prefix" data-options="prompt:'前缀'" readonly style="width: 50px;"> +
                            <input class="easyui-textbox" id="login" name="login" data-options="required:true" style="width: 135px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td><input class="easyui-textbox" type="password" id="pwd" name="password" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>重复密码:</td>
                        <td><input class="easyui-textbox" type="password" id="rpwd" name="password" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>姓名:</td>
                        <td><input class="easyui-textbox" name="username" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>性别:</td>
                        <td>
                            <div class="easyui-radio" id="radio">
                                <label><input type="radio" name="sex" value="male" checked /> 男</label>&nbsp;&nbsp;
                                <label><input type="radio" name="sex" value="female" /> 女</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>年龄:</td>
                        <td><input class="easyui-textbox" name="age" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>生日:</td>
                        <td><input class="easyui-datebox" name="birthday" data-options="width:200,panelHeight:200" /></td>
                    </tr>
                    <tr>
                        <td>手机号码:</td>
                        <td><input class="easyui-textbox" name="phone" data-options="required:true" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>邮箱:</td>
                        <td><input class="easyui-textbox" data-options="validType:'email'" name="email" style="width: 200px;" /></td>
                    </tr>
                    <tr>
                        <td>企业:</td>
                        <td>
                            <input id="companyId" class="easyui-combobox" name="companyId" style="width: 200px;"
                                   data-options="required:true,editable:false,valueField:'id',textField:'text',url:'${ctx}/system/company/findByCid' "/>
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input id="deptId" class="easyui-combobox" name="deptId" data-options="required:true,editable:false,width:200" />
                        </td>
                    </tr>
                    <tr>
                        <td>上级:</td>
                        <td>
                            <!--上级员工-->
                            <input id="users" name="parentId"  class="easyui-combobox" data-options="valueField:'id',textField:'text',url:'${ctx}/system/sysuser/findByCompanyId'" style="width: 200px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>锁定:</td>
                        <td>
                            <div class="easyui-radio">
                                <label><input type="radio" name="available" value="1" checked /> 否</label>&nbsp;&nbsp;
                                <label><input type="radio" name="available" value="0" /> 是</label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok">确定</a>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset">重置</a>--%>
            </div>
        </form>
    </div>
</div>
<div id="win_select_tree" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:500px;padding:5px;">
    <div class="easyui-layout" style="width:100%;height:100%;">
        <input type="hidden" id="perType" value="role" />
        <div data-options="region:'center',border:false" style="padding:5px;">
            <ul id="ztree" class="easyui-tree" data-options="checkbox:true,animate:true"></ul>
        </div>
        <div data-options="region:'south',border:false" style="height:35px;">
            <div data-options="region:'south',border:false" style="text-align:right;padding:4px 2px;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_tree">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_tree">重置</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var prefix = '${sessionScope.userinfo_session.prefix}';
    var companyId = '${sessionScope.userinfo_session.companyId}';
    var $prefix = $('#prefix');
    if(companyId == null || companyId == ''){
        $prefix.attr('readonly',false);
    }
</script>
<script type="text/javascript" src="${ctx}/static/system/sysuser_manage.js"></script>
