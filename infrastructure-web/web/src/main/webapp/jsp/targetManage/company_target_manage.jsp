<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/util.js"></script>
<jsp:include page="../targetManage/dept_target_operation.jsp" flush="true"></jsp:include>
<div class="easyui-layout" style="width:100%;height: 100%;" data-options="fit:true">
    <div data-options="region:'center',title:'公司目标 ',border:false">
        <%--显示列表--%>
        <table id="grid_company_target"></table>
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
<%--条件、按钮--%>
<div id="company_target_toolbar" style="padding: 8px 10px;">
    <div style="float: left;">
        <shiro:hasPermission name="companyTarget:add">
            <a id="add_company_target" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="companyTarget:update">
            <a id="edit_company_target" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="companyTarget:delete">
            <a id="remove_company_target" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </shiro:hasPermission>
    </div>
    <div align="right">
        <shiro:hasPermission name="companyTarget:queryCompanyTargetCompany">
            <input type="hidden" id="companyTargetPerm" value="1">
            所属企业：
            <input id="companyTargetCompanyIdQuery" class="easyui-combobox" name="companyId" style="width: 150px;" data-options="
                editable:false,
                required:false,
                valueField:'id',
                textField:'text'" />
        </shiro:hasPermission>
        公司目标标题：<input id="company_target_title" name="search" class="easyui-textbox" maxlength="100">
        <a id="search_company_target_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
    </div>
</div>
<div id="win_dept_target_detail" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:1200px;height:410px;">
    <div class="easyui-layout" data-options="fit:true">
        <%--显示列表--%>
        <table id="grid_dept_target_detail"></table>
    </div>
    <%--条件、按钮--%>
    <div id="dept_target_detail_toolbar" style="padding: 8px 10px;height: 25px;" >
        <div style="float: left;">
            所属部门：<input id="deptTargetDetailUserIdQuery" class="easyui-combobox" name="deptId" style="width: 100px;" data-options="editable:false,required:false,valueField:'id',textField:'text'" />
            部门目标标题：<input id="dept_target_detail_title" name="search" class="easyui-textbox" maxlength="100">
            <a id="search_dept_target_detail_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            <%--创建人：<input id="search_user" name="search" class="easyui-textbox" style="width: 100px;">--%>
        </div>
    </div>
</div>
<div id="win_company_target" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:500px;height:325px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_company_target" method="post">
            <div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
                <input type="hidden" id="id" name="id">
                <table>
                    <tr style="height: 30px;">
                        <td><span style="letter-spacing: 3em;">年</span>份:</td>
                        <td>
                            <input id="targetYear" class="input easyui-numberbox" type="text" name="targetYear" style="width: 170px;" data-options="required:true,min:1980,max:9999,prompt:'请输入4位正确的年份'"/>
                        </td>
                        <td>时间段:</td>
                        <td>
                            <input class="easyui-combobox" id="targetTimeSlot" name="targetTimeSlot" style="width: 170px;" data-options="
                            required:true,
                            editable: false,
                            valueField: 'label',
                            textField: 'value',
                            data: [{
                                label: 'slot_year',
                                value: '年度'
                            },{
                                label: 'slot_half_year',
                                value: '半年度'
                            }]" />
                        </td>
                    </tr>
                    <tr style="height: 30px;" id="addCompanyTargetAmount">
                    </tr>
                    <tr style="height: 80px;">
                        <td style="vertical-align: top;"><span style="letter-spacing: 3em;">内</span>容:</td>
                        <td colspan="4">
                            <input class="easyui-textbox company_target" type="text" name="targetContent" data-options="multiline:true" style="width: 390px;height: 70px;" maxlength="2000"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;"><span style="letter-spacing: 3em;">备</span>注:</td>
                        <td colspan="4">
                            <input class="easyui-textbox company_target" type="text" maxlength="100" name="remark" data-options="multiline:true" style="width: 390px;height: 80px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_company_target">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-reload'" id="reset_company_target">重置</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="close_company_target" style="display: none">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--  目标Window结束 -->
<script type="text/javascript" src="${ctx}/static/targetManage/company_target_manage.js"></script>
<script type="text/javascript">
    $(".easyui-datebox").datebox({
        panelHeight:240
    });
</script>