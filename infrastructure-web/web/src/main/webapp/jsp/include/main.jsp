<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link rel="stylesheet" href="${ctx}/static/index/css/documentation.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/index/css/jalendar.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/index/js/jalendar.js"></script>

<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <div data-options="region:'north',split:true,border:false" style="height:40%;">
                <div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                    <div id="tb_strategy" data-options="region:'west',border:false,title:'战略',split:true,tools:'#strategyTools'" style="width:40%;padding: 5px 3px;">
                        <div id="strategy">
                            ${strategy.content}
                        </div>
                    </div>
                    <div data-options="region:'center',title:'年度经济目标/上月计划完成率',border:false">
                        <div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                            <div data-options="region:'north',border:false" style="height:50%;border-bottom:1px solid #95B8E7;">
                                <table style="height: 100%;">
                                    <tr>
                                        <td>年度经济目标：</td>
                                        <td style="font-size: 40px;color: red;font-weight: bold;">${targetAmount}</td>
                                    </tr>
                                </table>
                            </div>
                            <div data-options="region:'center',border:false">
                                <table style="height: 100%;">
                                    <tr>
                                        <td>上月计划完成率：</td>
                                        <td style="font-size: 40px;color: red;font-weight: bold;">${completionRate}%</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div data-options="region:'east',border:false,title:'奖惩',split:true,tools:'#rpTools'" style="width:30%;">
                        <table id="main_grid_rp"></table>
                    </div>
                </div>
            </div>
            <div data-options="region:'center',border:false">
                <div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
                    <div data-options="region:'west',border:false,title:'通知公告/会议通知',split:true,tools:'#decomentTools'" style="width:40%">
                        <table id="main_announcement"></table>
                    </div>
                    <div data-options="region:'center',border:false,title:'制度',tools:'#regimeTools'">
                        <table id="main_grid_regime"></table>
                    </div>
                    <div data-options="region:'east',border:false,title:'工作台(待审批/个人计划)',split:true,tools:'#main_approved_search'" style="width:30%;">
                        <table id="main_approved"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'east',iconCls:'icon-reload',split:true,border:false" style="width:300px;border-top: 0;">
        <div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
            <div data-options="region:'north',title:'日历(右键添加记事)',split:true,border:false" style="height:70%;" align="center">
                <div id="myId" class="jalendar">
                    <c:forEach var="even" items="${events}">
                        <div class="added-event" id="${even.id}" data-date="${even.day}" data-time="${even.dtime}" data-title="${even.title}"></div>
                    </c:forEach>
                </div>
            </div>
            <div data-options="region:'center',split:true,title:'人员详情',border:false" align="center">
                <table>
                    <tr>
                        <td style="padding-left: 10px;">在线：</td>
                        <td><a href="javascript:query_zx(0);" style="text-decoration:none;">${zx_session}</a> 人</td>
                    </tr>
                    <tr>
                        <td style="padding-left: 10px;">请假：</td>
                        <td><a href="javascript:queryAfl(0);" style="text-decoration:none;">${aflSize}</a> 人</td>
                    </tr>
                    <tr>
                        <td style="padding-left: 10px;">公出：</td>
                        <td><a href="javascript:queryOut(0);" style="text-decoration:none;">${outSize}</a> 人</td>
                    </tr>
                </table>
                <div style="position:absolute;bottom:0px;width: 100%;font-size: 12px; right:0px;color: red">
                    <c:if test="${names != ''}">
                        <marquee scrollamount="5"><a style="margin-right: 5px;">${names}</a>生日快乐</marquee>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%---------------------------------------------toolbar-------------------------------%>
<!--战略按钮-->
<div id="strategyTools">
    <span>
    <shiro:hasPermission name="strategy:add">
        <a href="javascript:void(0);" class="icon-add" onclick="javascript:strategy_tools_add()"></a>
    </shiro:hasPermission>
    </span>
    <span hidden>
    <shiro:hasPermission name="strategy:edit">
        <a href="javascript:void(0);" class="icon-edit" onclick="javascript:strategy_tools_edit(this)"></a>
    </shiro:hasPermission>
    </span>
</div>
<%--奖惩--%>
<div id="rpTools">
    <a href="javascript:void(0);" class="icon-search" onclick="javascript:rp_tools_query(this)"></a>
</div>
<%--通知公告--%>
<div id="decomentTools">
    <a href="javascript:void(0);" class="icon-search" onclick="javascript:ann_tools_query()"></a>
</div>
<%--制度--%>
<div id="regimeTools">
    <a href="javascript:void(0);" class="icon-search" onclick="javascript:regime_tools_query();"></a>
</div>
<%--工作台(待审批)--%>
<div id="main_approved_search">
    <a href="javascript:void(0);" class="icon-search" onclick="javascript:approved_query();"></a>
</div>

<%---------------------------------------------窗口-------------------------------%>
<%--战略窗口--%>
<div id="win_strategy" class="easyui-dialog" style="width:400px;height:300px;padding:5px"
     data-options="closed:true,modal : true,iconCls: 'icon-save',
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
                <input class="easyui-textbox" name="content" data-options="required:true,multiline:true" style="width: 320px;height: 200px;"/>
            </td>
        </tr>
    </table>
    </form>
</div>
<%--奖惩--%>
<div id="main_win_rp" class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',modal:true" style="width:500px;height:400px; padding: 5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding: 5px;" align="center">
                <table>
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
                        <td><input class="easyui-combobox" name="objType" style="width: 250px;" data-options="
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
                </table>
            </div>
        </form>
    </div>
</div>
<%--通知公告/会议通知--%>
<div id="main_ac_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:350px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table cellpadding="5">
                    <tr>
                        <td>标<i style="margin-left: 2em;"></i>题:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="readonly:true,width:300" />
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">内<i style="margin-left: 2em;"></i>容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:300,height:150" />
                        </td>
                    </tr>
                    <tr>
                        <td >附<i style="margin-left: 2em;"></i>件:</td>
                        <td mark="fileName"></td>
                    </tr>
                    <tr>
                        <td>发布时间:</td>
                        <td>
                            <input class="easyui-textbox" name="releaseTime" data-options="readonly:true,width:300" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--会议通知--%>
<div id="main_meeting_view" class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,resizable:false,minimizable:false,iconCls:'icon-save',modal:true"
     style="width:420px;height:390px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding: 5px;">
                <table style="width: 320px; margin: 0px auto">
                    <tr>
                        <td>标<i style="margin-left: 1em;"></i>题：</td>
                        <td>
                            <input type="text" name="title" class="easyui-textbox" readonly data-options="required:true" style="width: 260px;" >
                        </td>

                    </tr>
                    <tr>
                        <td>日<i style="margin-left: 1em;"></i>期：</td>
                        <td>
                            <input type="text" name="meetingTime" class="easyui-datetimebox" data-options="editable:false" readonly style="width: 260px" >
                        </td>
                    </tr>
                    <tr>
                        <td>地<i style="margin-left: 1em;"></i>点：</td>
                        <td>
                            <input type="text" name="meetingAddress" class="easyui-textbox" readonly style="width: 260px" />
                        </td>
                    </tr>
                    <tr>
                        <td>参与人：</td>
                        <td>
                            <input type="text" name="joinUserNames" class="easyui-textbox" readonly data-options="multiline:true,width:260,height:110">
                        </td>
                    </tr>
                    <tr>
                        <td>记录者：</td>
                        <td>
                            <input type="text" name="recordUser" class="easyui-textbox" readonly style="width: 260px">
                        </td>

                    </tr>
                    <tr>
                        <td valign="top">要<i style="margin-left: 1em;"></i>求：</td>
                        <td>
                            <input type="text" name="meetingClaim" class="easyui-textbox" readonly data-options="multiline:true,width:260,height:110">
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
</div>
<%--制度--%>
<div id="main_regime_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',inline:false" style="width:500px;height:400px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table>
                    <tr>
                        <td>附件:</td>
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
                        <td>制度内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true" style="width: 300px;height: 200px;"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<!--待审批查看-->
<div id="main_approve_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-search',inline:false" style="width:500px;height:400px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <input type="hidden" name="id">
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table cellpadding="5">
                    <tr>
                        <td>审批内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,width:300,height:100,readonly:true" />
                        </td>
                    </tr>
                    <tr>
                        <td>附件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
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
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="check_ok">通过</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="check_reset">驳回</a>
            </div>
        </form>
    </div>
</div>
<%--个人计划详情--%>
<div id="man_plan_view" class="easyui-window" data-options="closed:true,collapsible:false,maximizable:false,minimizable:false,modal:true" style="width:600px;height:500px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding-left:30px;padding-top: 10px;">
                <table cellpadding="5">
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
                            <input id="planCompletionTime" class="easyui-datebox" name="planCompletionTime" data-options="prompt:'yyyy-mm-dd',editable: false,readonly:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">计划内容:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="content" data-options="multiline:true,width:440,height:70,readonly:true" />
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
<div id="main_approve" class="easyui-dialog" style="width:400px;height:200px;padding:10px"
     data-options="closed:true,modal : true,iconCls: 'icon-save',
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler : approve_ok
				}]">
    <table cellpadding="5">
        <tr>
            <td>意见:</td>
            <td>
                <input type="hidden" id="ma_state" />
                <input type="hidden" id="ma_id" />
                <input class="easyui-textbox" id="main_app_option" data-options="multiline:true" style="width: 300px;height: 100px;"/>
            </td>
        </tr>
    </table>
</div>
<%--/****************************日历*************************************/--%>
<div id="calendar_right_menu" class="easyui-menu" style="width:100px;">
    <div id="add_event" data-options="iconCls:'icon-add'" onclick="add_event_fun()">新建记事</div>
    <div id="select_event" data-options="iconCls:'icon-search'" onclick="select_event_fun()">记事列表</div>
</div>
<div id="win_event_grid" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:300px;padding:5px;">
    <table id="event_grid"></table>
</div>
<div id="win_event" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:300px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_event" method="post">
            <input type="hidden" id="day" name="day">
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table cellpadding="5">
                    <tr>
                        <td>时间:</td>
                        <td>
                            <input name="dtime" class="easyui-timespinner" data-options="value:'00:00'">
                        </td>
                    </tr>
                    <tr>
                        <td>记事内容:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="multiline:true,width:260,height:120" />
                        </td>
                    </tr>
                </table>
            </div>
            <div id="south" data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_event">确定</a>
            </div>
        </form>
    </div>
</div>
<%--/*******************************请假/公出 start******************************************/--%>
<%--请假--%>
<div id="win_main_afl" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:900px;height:480px;padding:5px;">
    <table id="main_grid_afl"></table>
</div>
<%--公出--%>
<div id="win_main_out" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:900px;height:480px;padding:5px;">
    <table id="main_grid_out"></table>
</div>
<div id="win_main_afl_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:450px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table cellpadding="5">
                    <tr>
                        <td>申请人:</td>
                        <td>
                            <input class="easyui-textbox" name="applicant" data-options="readonly:true" style="width: 250px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>申请日期:</td>
                        <td>
                            <input class="easyui-datebox" name="applyTime" data-options="readonly:true" style="width: 250px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input name="dept" class="easyui-combobox"
                                   data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/dept/findDept',onBeforeLoad:function(param){param.companyId='${userinfo_session.companyId}'},width:120"/>
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
                            <input class="easyui-datebox" name="startTime" style="width:155px" readonly>
                            <select id="main_start_hour_view" name="startHour" style="width:45px;" readonly=""></select> 时
                            <select id="main_start_min_view" name="startMin" style="width:45px;" readonly></select> 分
                        </td>
                    </tr>
                    <tr>
                        <td>结束时间:</td>
                        <td>
                            <input class="easyui-datebox" name="endTime" style="width:155px" readonly>
                            <select id="main_end_hour_view" name="endHour" style="width:45px;" readonly></select> 时
                            <select id="main_end_min_view" name="endMin" style="width:45px;" readonly></select> 分
                        </td>
                    </tr>
                    <tr>
                        <td>合计:</td>
                        <td>
                            <input class="easyui-numberbox" name="count" data-options="readonly:true" style="width: 155px;" /> 天
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
        </form>
    </div>
</div>
<%--公出 查看--%>
<div id="win_main_out_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:500px;height:400px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center'" style="padding-top: 10px;" align="center">
                <table cellpadding="5">
                    <tr>
                        <td>申请人:</td>
                        <td>
                            <input class="easyui-textbox" name="applicant" data-options="readonly:true" style="width: 250px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>所属部门:</td>
                        <td>
                            <input name="dept" class="easyui-combobox"
                                   data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/dept/findDept',onBeforeLoad:function(param){param.companyId='${userinfo_session.companyId}'},width:120"/>
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
                            <input class="easyui-numberbox" name="count" data-options="readonly:true,min:0,precision:1,prompt:'合计'" style="width: 155px;" /> 天
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<%--在线人数--%>
<div id="win_main_zx" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:320px;height:200px;padding:5px;">
    <c:forEach items="${zxList}" var="user">
        <div style="width: 50px;float: left;">${user.username}</div>
    </c:forEach>
</div>
<%--/*******************************请假/公出 end******************************************/--%>
<script type="text/javascript">
    var strategy = '${strategy}';
</script>
<script type="text/javascript" src="${ctx}/static/index/js/main.js"></script>