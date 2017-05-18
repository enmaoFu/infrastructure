<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<!--制度管理-->
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%;height: 100%">
    <div data-options="region:'center',border:false">
        <table id="grid_regime"></table>
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
<div id="tb_regime" style="padding: 8px 10px;">
    <div style="float: left;">
        <shiro:hasPermission name="regime:add">
            <a id="add_regime" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="regime:edit">
            <a id="edit_regime" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="regime:delete">
            <a id="remove_regime" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </shiro:hasPermission>
    </div>
    <div align="right">
        &nbsp;
    </div>
</div>
<!--制度新增/修改-->
<div id="win_regime" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',inline:false" style="width:440px;height:270px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_regime" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id">
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em">附</span>件:</td>
                        <td>
                            <input class="easyui-filebox" id="regime_file" name="file" data-options="buttonText:'选择',prompt:'文件',width:250">
                        </td>
                    </tr>
                    <tr>
                        <td>所属模块:</td>
                        <td>
                            <input name="model"  class="easyui-combobox" data-options="editable:false,valueField:'id',textField:'text',url:'${ctx}/system/resource/queryUrlNotNull',width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td>制度内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,required:true,width:250,height:120" />
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_regime">确定</a>
            </div>
        </form>
    </div>
</div>
<!--查看-->
<div id="win_regime_view" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save',inline:false" style="width:440px;height:270px;">
    <div class="easyui-layout" data-options="fit:true">
        <form>
            <div data-options="region:'center',border:false" style="padding-top: 5px;" align="center">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em">附</span>件:</td>
                        <td mark="fileName">

                        </td>
                    </tr>
                    <tr>
                        <td>所属模块:</td>
                        <td>
                            <input name="model"  class="easyui-combobox" data-options="readonly:true,valueField:'id',textField:'text',url:'${ctx}/system/resource/queryUrlNotNull',width:250" />
                        </td>
                    </tr>
                    <tr>
                        <td>制度内容:</td>
                        <td>
                            <input class="easyui-textbox" name="content" data-options="multiline:true,readonly:true,width:250,height:120" />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/daily/js/regime_manage.js"></script>