<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<%---------------------------------------------窗口-------------------------------%>
<%--战略窗口--%>
<div id="win_strategy" hidden class="easyui-dialog" style="padding:5px"
     data-options="closed:true,modal : true,iconCls: 'icon-save',width:400,height:300,
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler : fun_ok
				}]">
    <form method="post">
        <input type="hidden" name="id"/>
        <table>
            <tr>
                <td>战略:</td>
                <td>
                    <input class="easyui-textbox" name="content" data-options="required:true,multiline:true,width:320,height:200" />
                </td>
            </tr>
        </table>
    </form>
</div>
<%--奖惩--%>
<div id="main_win_rp" hidden class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',modal:true" style="width:500px;height:390px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="2">
                    <tr>
                        <th>奖惩类型：</td>
                        <td>
                            <label><input type="radio" name="type" value="0" checked disabled/> 奖励</label>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="type" value="1" disabled/> 惩罚</label>
                        </td>
                    </tr>
                    <tr>
                        <td>奖惩对象类型：</td>
                        <td><input class="easyui-combobox" name="objType" data-options="
                            width:250,
                            valueField:'id',
                            textField:'text',
                            readonly:true,
                            data: [{
                                id: '0',
                                text: '企业'
                            },{
                                id: '1',
                                text: '部门'
                            },{
                                id: '2',
                                text: '个人'
                            }]" />
                        </td>
                    </tr>
                    <tr>
                        <td>奖惩对象：</td>
                        <td>
                            <input type="text" name="rewardObj" class="easyui-textbox" data-options="multiline:true,width:250,height:80,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td>奖惩内容：</td>
                        <td><input type="text" name="content" class="easyui-textbox" data-options="readonly:true,multiline:true,width:250,height:150" /></td>
                    </tr>
                    <tr>
                        <td>所属公司:</td>
                        <td>
                            <input class="easyui-textbox" name="companyName" data-options="readonly:true,width:250" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--通知公告--%>
<div id="main_ac_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:600px;height:450px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>标<i style="margin-left: 2em;"></i>题:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="readonly:true,width:400" />
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">内<i style="margin-left: 2em;"></i>容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:400,height:300" />
                        </td>
                    </tr>
                    <tr>
                        <td >附<i style="margin-left: 2em;"></i>件:</td>
                        <td mark="fileName"></td>
                    </tr>
                    <tr>
                        <td>发布时间:</td>
                        <td>
                            <input class="easyui-textbox" name="releaseTime" data-options="readonly:true,width:400" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--会议通知--%>
<div id="main_meeting_view" hidden class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,resizable:false,minimizable:false,iconCls:'icon-save',modal:true,width:600,height:450">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>标<i style="margin-left: 1em;"></i>题：</td>
                        <td>
                            <input type="text" name="title" class="easyui-textbox" readonly data-options="readonly:true,width:400" >
                        </td>

                    </tr>
                    <tr>
                        <td>日<i style="margin-left: 1em;"></i>期：</td>
                        <td>
                            <input type="text" name="meetingTime" class="easyui-datetimebox" data-options="readonly:true,width: 400" >
                        </td>
                    </tr>
                    <tr>
                        <td>地<i style="margin-left: 1em;"></i>点：</td>
                        <td>
                            <input type="text" name="meetingAddress" class="easyui-textbox" data-options="readonly:true,width: 400" />
                        </td>
                    </tr>
                    <tr>
                        <td>参与人：</td>
                        <td>
                            <input type="text" name="joinUserNames" class="easyui-textbox" data-options="readonly:true,multiline:true,width:400,height:140">
                        </td>
                    </tr>
                    <tr>
                        <td>记录者：</td>
                        <td>
                            <input type="text" name="recordUser" class="easyui-textbox" data-options="readonly:true,width: 400">
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">要<i style="margin-left: 1em;"></i>求：</td>
                        <td>
                            <input type="text" name="meetingClaim" class="easyui-textbox" data-options="readonly:true,multiline:true,width:400,height:140">
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--制度--%>
<div id="main_regime_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',inline:false" style="width:500px;height:320px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>附<span style="margin-left: 2em;"></span>件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                    <tr>
                        <td>所属模块:</td>
                        <td>
                            <input name="model"  class="easyui-combobox" data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/resource/queryUrlNotNull',width:200" />
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">制度内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:300,height:200" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<!--流程审批查看-->
<div id="main_approve_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-search',inline:false" style="width:500px;height:350px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding-top: 10px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td valign="top">审批内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,width:300,height:100,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td>附<span style="margin-left: 2em;"></span>件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                    <tr>
                        <td valign="top">备<span style="margin-left: 2em;"></span>注:</td>
                        <td>
                            <input class="easyui-textbox" name="remark" data-options="multiline:true,width:300,height:70,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td>创建时间:</td>
                        <td>
                            <input class="easyui-textbox" name="createTime" data-options="width:200,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td>创<span style="margin-left: 0.5em;"></span>建<span style="margin-left: 0.5em;"></span>人:</td>
                        <td>
                            <input class="easyui-textbox" name="username" data-options="width:200,readonly:true" />
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="check_ok">通过</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="check_reset">驳回</a>
            </div>
        </form>
    </div>
</div>
<%--个人计划详情--%>
<div id="man_plan_view" hidden class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,minimizable:false,modal:true" style="width:600px;height:500px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding-left:30px;padding-top: 10px;">
                <table cellpadding="2">
                    <tr>
                        <td>重点工作:</td>
                        <td>
                            <input type="radio" name="keyWork" value="1" checked="checked" disabled/>是
                            <input type="radio" name="keyWork" value="0" style="padding-left: 5%;" disabled/>否
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">完成时间:</td>
                        <td colspan="4">
                            <input id="planCompletionTime" class="easyui-datebox" name="planCompletionTime" data-options="prompt:'yyyy-mm-dd',editable: false,readonly:true,width:440"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">计划内容:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="detailPlanContent" data-options="multiline:true,width:440,height:70,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">计划标准:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="detailPlanStandard" data-options="multiline:true,width:440,height:70,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">配合部门:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="planCoordination" data-options="multiline:true,width:440,height:70,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">自我评价:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="detailCompletionStatus" data-options="multiline:true,width:440,height:70,readonly:true" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<div id="main_approve" hidden class="easyui-dialog" style="width:400px;height:200px;padding:10px"
     data-options="closed:true,modal : true,iconCls: 'icon-save',
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler : approve_ok
				}]">
    <table cellpadding="2">
        <tr>
            <td>意见:</td>
            <td>
                <input type="hidden" id="ma_state" />
                <input type="hidden" id="ma_id" />
                <input class="easyui-textbox" id="main_app_option" data-options="multiline:true,width:300,height:100" />
            </td>
        </tr>
    </table>
</div>
<%--指令--%>
<div id="win_inst_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-search',inline:false" style="width:450px;height:290px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <input type="hidden" name="id">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td style="vertical-align: top">指令内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:300,height:100" />
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">附</span>件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--/****************************日历*************************************/--%>
<div id="calendar_right_menu" class="easyui-menu" style="width:100px;">
    <div id="add_event" data-options="iconCls:'icon-add'" onclick="add_event_fun()">新建记事</div>
    <div id="select_event" data-options="iconCls:'icon-search'" onclick="select_event_fun()">记事列表</div>
</div>
<div id="win_event_grid" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:300px;padding:5px;">
    <table id="event_grid"></table>
</div>
<%--新增--%>
<div id="win_event" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:300px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_event" method="post">
            <input type="hidden" id="day" name="day">
            <div data-options="region:'center',border:false" style="padding-top: 10px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>时<span style="margin-left: 2em;"></span>间:</td>
                        <td>
                            <input name="dtime" class="easyui-timespinner" data-options="value:'00:00',width:260">
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">记事内容:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="multiline:true,width:260,height:150" />
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_event">确定</a>
            </div>
        </form>
    </div>
</div>
<%--查看--%>
<div id="win_event_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:400px;height:260px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding-top: 10px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>时<span style="margin-left: 2em;"></span>间:</td>
                        <td>
                            <input name="dtime" class="easyui-timespinner" data-options="readonly:true,value:'00:00',width:260">
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">记事内容:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="readonly:true,multiline:true,width:260,height:150" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--/*******************************请假/公出 start******************************************/--%>
<%--请假--%>
<div id="win_main_afl" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',width:900,height:480" style="padding:5px;">
    <table id="main_grid_afl"></table>
</div>
<%--公出--%>
<div id="win_main_out" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',width:900,height:480" style="padding:5px;">
    <table id="main_grid_out"></table>
</div>
<%--请假查看--%>
<div id="win_main_afl_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',width:470,height:410" >
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="3">
                    <tr>
                        <td>申请人:</td>
                        <td>
                            <input class="easyui-textbox" name="applicant" data-options="readonly:true,width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td>申请日期:</td>
                        <td>
                            <input class="easyui-datebox" name="applyTime" data-options="readonly:true,width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input name="dept" class="easyui-combobox"
                                   data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/dept/findDept',onBeforeLoad:function(param){param.companyId='${userinfo_session.companyId}'},width:150"/>
                        </td>
                    </tr>
                    <tr>
                        <td>类型:</td>
                        <td>
                            <input name="oType" class="easyui-combobox" data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/select/querySelect?s_mark=afl',width:150"/>
                        </td>
                    </tr>
                    <tr>
                        <td>请假事由:</td>
                        <td>
                            <input class="easyui-textbox" name="subject" data-options="multiline:true,readonly:true,width:250,height:80" />
                        </td>
                    </tr>
                    <tr>
                        <td>开始时间:</td>
                        <td>
                            <input class="easyui-datebox" data-options="readonly:true,width:155" name="startTime" readonly>
                            <select id="main_start_hour_view" name="startHour" style="width:45px;" readonly></select> 时
                            <select id="main_start_min_view" name="startMin" style="width:45px;" readonly></select> 分
                        </td>
                    </tr>
                    <tr>
                        <td>结束时间:</td>
                        <td>
                            <input class="easyui-datebox" name="endTime" data-options="readonly:true,width:155">
                            <select id="main_end_hour_view" name="endHour" style="width:45px;" readonly></select> 时
                            <select id="main_end_min_view" name="endMin" style="width:45px;" readonly></select> 分
                        </td>
                    </tr>
                    <tr>
                        <td>合计:</td>
                        <td>
                            <input class="easyui-numberbox" name="count" data-options="readonly:true,min:0,precision:2" style="width: 155px;" /> 天
                        </td>
                    </tr>
                    <tr>
                        <td>审批人:</td>
                        <td>
                            <input name="userId" class="easyui-combobox" readonly data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/sysuser/findByCompanyId',width:155"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" mark="ty">同意</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" mark="bh">驳回</a>
            </div>
        </form>
    </div>
</div>
<div id="dlg_afl" hidden class="easyui-dialog"
     data-options="
                width:410,
                height:250,
                closed:true,
                modal : true,
                iconCls: 'icon-save',
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler : fun_alf_ok
				}]">
    <table cellpadding="3">
        <tr>
            <td>意<span style="margin-left: 2em;"></span>见:</td>
            <td>
                <input type="hidden" id="afl_id" />
                <input type="hidden" id="afl_state" />
                <input class="easyui-textbox" id="afl_opinion" data-options="multiline:true,width:300,height:100" />
            </td>
        </tr>
        <tr>
            <td>转<span style="margin-left: 2em;"></span>发:</td>
            <td>
                <input id="check_user_id" />
            </td>
        </tr>
        <tr>
            <td>短信通知:</td>
            <td>
                <input id="sms" type="checkbox" />
            </td>
        </tr>
    </table>
</div>
<%--公出 查看--%>
<div id="win_main_out_view" hidden class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:460px;height:310px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>申请人:</td>
                        <td>
                            <input class="easyui-textbox" name="applicant" data-options="readonly:true,width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input name="dept" class="easyui-combobox"
                                   data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/dept/findDept',onBeforeLoad:function(param){param.companyId='${userinfo_session.companyId}'},width:155"/>
                        </td>
                    </tr>
                    <tr>
                        <td>公出事由:</td>
                        <td>
                            <input class="easyui-textbox" name="subject" data-options="readonly:true,multiline:true,prompt:'请假事由',width:250,height:80" />
                        </td>
                    </tr>
                    <tr>
                        <td>开始时间:</td>
                        <td>
                            <input class="easyui-datebox" name="startTime" data-options="readonly:true,width:155" >
                        </td>
                    </tr>
                    <tr>
                        <td>结束时间:</td>
                        <td>
                            <input class="easyui-datebox" name="endTime" data-options="readonly:true,width:155">
                        </td>
                    </tr>
                    <tr>
                        <td>合计:</td>
                        <td>
                            <input class="easyui-numberbox" name="count" data-options="readonly:true,min:0,precision:1,prompt:'合计',width:155" /> 天
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--集团公司请假公出详情--%>
<div id="win_main_afl_out" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',width:1100,height:550">
    <div class="easyui-layout" data-options="fit:true" style="width:100%;height:100%;">
        <div data-options="region:'center',title:'请假',split:true,border:false" style="border-bottom: 1px solid #95B8E7;">
            <table id="main_grid_afl_all"></table>
        </div>
        <div data-options="region:'south',title:'公出',border:false" style="height:50%;">
            <table id="main_grid_out_all"></table>
        </div>
    </div>
</div>
<%--/*******************************请假/公出 end******************************************/--%>
<%--在线人数--%>
<div id="win_main_zx" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-search'" style="width:350px;height:200px;padding:5px;">
    <c:forEach items="${zxList}" var="user">
        <div style="width: 65px;float: left;">${user.username}</div>
    </c:forEach>
</div>