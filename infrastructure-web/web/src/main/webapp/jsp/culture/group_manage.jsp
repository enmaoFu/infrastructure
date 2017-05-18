<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<link href="${ctx}/static/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/sock/sockjs.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>

<%--工作群组--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%;height: 100%">
    <div data-options="region:'center',border:false" style="padding:5px;background:#eee;">
        <table id="grid_group"></table>
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
<div id="tb_group" style="padding: 8px 10px;">
    <a id="company_group" class="easyui-linkbutton" data-options="iconCls:'icon-share_all',plain:true,selected:true">公司群组</a>
    <a id="my_group" class="easyui-linkbutton" data-options="iconCls:'icon-share_my',plain:true">我的群组</a>
    <a id="verification" class="easyui-linkbutton" data-options="iconCls:'icon-speaker',plain:true">验证消息</a>
    <span id="toolbar_group" style="margin-left: 20px;" hidden>
        <shiro:hasPermission name="group:add">
            <a id="add_group" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">创建群组</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="group:edit">
            <a id="edit_group" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="group:remove">
            <a id="remove_group" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </shiro:hasPermission>
    </span>
</div>
<!--window-->
<div id="win_group" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:530px;height:470px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_group" method="post">
            <input type="hidden" name="id">
            <input type="hidden" id="userIds" name="userIds">
            <div data-options="region:'center',border:false" align="center">
                <table cellpadding="2">
                    <tr>
                        <td>群组名称:</td>
                        <td>
                            <input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 200px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>选择成员:</td>
                        <td>
                            <div class="easyui-layout" style="width:400px;height:250px;">
                                <div data-options="region:'center',title:'待选联系人'" style="padding:2px;">
                                    <ul id="all_tree" class="ztree"></ul>
                                </div>
                                <div data-options="region:'east',title:'已选联系人',collapsible:false" style="width:50%;padding:2px;">
                                    <ul id="choose_tree" class="ztree"></ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em;">备</span>注:</td>
                        <td>
                            <input class="easyui-textbox" name="remark" data-options="multiline:true" style="width: 400px;height: 100px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="south" data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_group">确定</a>
        </div>
        </form>
    </div>
</div>
<!--聊天窗口-->
<div id="win_message" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,onBeforeClose:beforeClose" style="width:550px;height:550px;padding:5px;">
    <div class="easyui-layout" style="width:100%;height:100%;">
        <input type="hidden" id="groupId">
        <div data-options="region:'center',border:false">
            <div class="easyui-layout" style="width:100%;height:100%;">
                <div data-options="region:'north',split:true,border:false" style="height:350px;border-radius: 5px;">
                    <input id="text" class="easyui-textbox"  data-options="multiline:true,readonly:true" style="width: 100%;height: 100%;overflow-y:scroll;"/>
                </div>
                <div data-options="region:'center',border:false">
                    <input id="send_value" class="easyui-textbox"  data-options="multiline:true" style="width: 100%;height: 100%;"/>
                </div>
                <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="close_break();">关闭</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="send" onclick="SendData();">发送</a>
                </div>
            </div>
        </div>
        <div data-options="region:'east',title:'讨论组成员',split:true,collapsible:false" style="width:150px;">
            <ul id="user_zTree" class="ztree"></ul>
        </div>
    </div>
</div>
<!--验证消息弹窗-->
<div id="win_verification" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,iconCls:'icon-speaker',onBeforeClose:pending" style="width:700px;height:500px;padding:5px;">
    <table id="grid_verification"></table>
    <div id="tb_verification" style="padding: 8px 10px;">
        <a id="remove_verification" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>
<!--申请入群-->
<div id="win_apply" class="easyui-dialog" data-options="modal:true,closed:true,iconCls:'icon-save',buttons:'#apply_buttons'" style="width:330px;height:210px;padding:5px;">
    <form id="form_apply" method="post">
        <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
            <table cellpadding="5">
                <tr>
                    <td>验证信息:</td>
                    <td>
                        <input class="easyui-textbox" name="information" data-options="multiline:true" style="width: 200px;height: 100px;"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div  id="apply_buttons">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_apply">确定</a>
</div>
<script type="text/javascript">
    var uId = '${sessionScope.userinfo_session.id}';
    var userName = '${sessionScope.userinfo_session.username}';
</script>
<script type="text/javascript" src="${ctx}/static/culture/js/group_manage.js"></script>