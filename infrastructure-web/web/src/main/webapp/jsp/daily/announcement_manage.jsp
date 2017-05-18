<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<!--公告管理-->
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%;height: 100%">
    <div data-options="region:'center',border:false" style="padding:5px;">
        <table id="grid_announcement"></table>
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

<%--新增/编辑--%>
<div id="win_ac" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:420px;height:325px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_ann" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em;">标</span>题:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="required:true,prompt:'标题',width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em;">内</span>容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,required:true,width:250,height:150" />
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em;">附</span>件:</td>
                        <td>
                            <input class="easyui-filebox" id="ann_file" name="file" style="width: 250px;" data-options="buttonText:'选择',prompt:'文件'">
                        </td>
                    </tr>
                    <tr>
                        <td>所属模块:</td>
                        <td>
                            <input name="model"  class="easyui-combobox" data-options="valueField:'id',textField:'text',url:'${ctx}/system/resource/queryUrlNotNull',editable:false,width:250" />
                        </td>
                    </tr>
                </table>
            </div>
            <div id="south" data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
             <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_ac">确定</a>
            </div>
        </form>
    </div>
</div>
<%--查看--%>
<div id="win_ac_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:420px;height:325px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em;">标</span>题:</td>
                        <td>
                            <input class="easyui-textbox" name="title" data-options="readonly:true,width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em;">内</span>容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:250,height:150" />
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em;">附</span>件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                    <tr>
                        <td>所属模块:</td>
                        <td>
                            <input name="model"  class="easyui-combobox" data-options="valueField:'id',readonly:true,textField:'text',url:'${ctx}/system/resource/queryUrlNotNull',width:250" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<!--toolbar-->
<div id="tb_ac" style="padding: 6px 10px;">
    <div style="float: left;">
        <shiro:hasPermission name="announcement:add">
            <a id="add_ac" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="announcement:edit">
            <a id="edit_ac" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="announcement:delete">
            <a id="remove_ac" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="announcement:release">
            <a id="release_ac" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">发布</a>
        </shiro:hasPermission>
    </div>
    <div align="right">
        <input class="easyui-textbox" type="text" data-options="prompt:'标题'" style="width: 150px;" />
        <a id="ac_search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
    </div>
</div>
<%--发布--%>
<div id="dlg_release" class="easyui-dialog" style="width:400px;height:150px;padding:10px" align="center"
     data-options="
                closed:true,
                modal : true,
                iconCls: 'icon-save',
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler : release_ok
				}]">
    <table cellpadding="5">
        <tr>
            <td>分享到:</td>
            <td>
                <div class="easyui-radio" id="radio">
                    <label><input type="radio" name="arange" value="my" checked /> 本公司</label>&nbsp;&nbsp;
                    <label><input type="radio" name="arange" value="all" /> 关联企业</label>
                </div>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="${ctx}/static/daily/js/announcement_manage.js"></script>