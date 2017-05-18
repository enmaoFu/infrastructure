<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>

<div class="easyui-layout" style="width:100%;height: 100%;">
	<div data-options="region:'center',title:'查询权限管理',border:false">
		<%--显示列表--%>
		<table id="grid_queryPerm"></table>
	</div>
	<div data-options="region:'east',title:'制度',split:true,border:false" style="width:200px;">

	</div>
</div>
<%--条件、按钮--%>
<div id="queryPerm_toolbar" style="padding: 8px 10px;height: 35px;" align="center">
	<div style="float: left;">
		<shiro:hasPermission name="queryPerm:add">
			<a id="add_queryPerm" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="queryPerm:update">
			<a id="edit_queryPerm" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="queryPerm:delete">
			<a id="remove_queryPerm" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
		</shiro:hasPermission>
		查询权限名称：<input id="search_user" name="search" class="easyui-textbox" style="width: 100px;">
		创建时间：
		<input id="search_startsignDate" class="easyui-datebox" type="text" name="startsignDate" style="width: 91px;" data-options="editable:false"/>
		至
		<input id="search_endsignDate" class="easyui-datebox" type="text" name="endsignDate" style="width: 91px;" data-options="editable:false"/>
		<a id="search_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</div>

<div id="win_queryPerm" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'${id==null||id==""?'icon-add':'icon-update'}'" style="width:400px;height:230px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_queryPerm" method="post" action="${id==null||id==""?'/queryPerm/insert':'/queryPerm/update'}">
			<div data-options="region:'center'" style="padding-left:30px;padding-top: 10px;">
				<input type="hidden" id="id" name="id">
				<table>
					<tr style="height: 30px;">
						<td>查询权限名称:</td>
						<td colspan="4">
							<input id="permName" name="permName" maxlength="50" class="easyui-textbox" type="text" data-options="required:true" />
						</td>
					</tr>
					<tr style="height: 30px;">
						<td>查询权限标识:</td>
						<td colspan="4">
							<input id="permIdentification" name="permIdentification" maxlength="50" class="easyui-textbox" type="text" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;"><span style="letter-spacing: 2em;">备</span>注:</td>
						<td>
							<input class="easyui-textbox" type="text" maxlength="120" name="remark" data-options="multiline:true" style="height: 50px;"/>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_queryPerm">确定</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="reset_queryPerm">重置</a>
			</div>
		</form>
	</div>
</div>
<!--  目标结束 -->

<script type="text/javascript" src="${ctx}/static/queryPerm/queryPerm_manage.js"></script>
<script type="text/javascript">
	$("#search_startsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_date = $("#search_endsignDate").datebox('getValue');
			var end_date_temple = end_date!=null&&end_date!=''?
					new Date(end_date+"".replace("-", "/").replace("-", "/")).getTime():'';

			if(end_date_temple!=null && end_date_temple!=""){
				if(end_date_temple<start_data_temple){
					$.messager.alert("提示","结束时间不应小于起始时间","warning");
					$(this).datebox('setValue','');
					return false;
				}
			}


			var now_date=new Date();
			var now_date_temple = now_date!=null&&now_date!=''?
					new Date(now_date+"".replace("-", "/").replace("-", "/")).getTime():'';
			if(now_date_temple<start_data_temple){
				$.messager.alert("提示","不能超过当期时间","warning");
				$(this).datebox('setValue','');
				return false;
			}


		}
	});

	$("#search_endsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_data = $("#search_endsignDate").datebox('getValue');
			var end_date_temple = end_data!=null&&date!=''?
					new Date(end_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			if(start_data_temple!=null&& start_data_temple!=""){
				if(start_data_temple>end_date_temple){
					$.messager.alert("提示","起始时间不大于结束时间","warning");
					$(this).datebox('setValue','');
					return false;
				}
			}


			var now_date=new Date();
			var now_date_temple = now_date!=null&&now_date!=''?
					new Date(now_date+"".replace("-", "/").replace("-", "/")).getTime():'';
			if(now_date_temple<end_date_temple){
				$.messager.alert("提示","不能超过当期时间","warning");
				$(this).datebox('setValue','');
				return false;
			}

		}
	});
</script>


