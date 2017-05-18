<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<script type="text/javascript">
	//获取当前登录用户的企业id
	var instCompanyId = '${sessionScope.userinfo_session.companyId}';
</script>
<link href="${ctx}/static/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<div class="easyui-layout" style="width:100%;height: 100%;" data-options="fit:true">
	<div data-options="region:'center',title:'指令管理 ',border:false">
		<%--显示列表--%>
		<table id="grid_instructions"></table>
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
<div id="instructions_toolbar" style="padding: 8px 10px;height: 20px;" align="center">
	<div style="float: left;">
		<a id="launch_instruction" class="easyui-linkbutton" data-options="iconCls:'icon-share_my',plain:true,selected:true">发出指令</a>
		<a id="receive_instruction" class="easyui-linkbutton" data-options="iconCls:'icon-share_all',plain:true">收到指令</a>
		<a id="forwarding_instruction" class="easyui-linkbutton" data-options="iconCls:'icon-share_all',plain:true">转发指令</a>
		<span id="toolbar_launch_instruction" >
			<shiro:hasPermission name="instructions:add">
				<a id="add_instructions" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="instructions:update">
				<a id="edit_instructions" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="instructions:delete">
				<a id="remove_instructions" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="instructions:confirm">
				<a id="confirm_instructions" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">确认完成</a>
			</shiro:hasPermission>
		</span>
		<span id="toolbar_receive_instruction" >
			<shiro:hasPermission name="instructions:forward">
				<a id="forward_instructions" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">转发</a>
			</shiro:hasPermission>
		</span>
	</div>
	<div align="right">
		<input id="search_instruction_content" name="content" class="easyui-textbox" data-options="prompt:'指令内容'">
		<input id="search_instruction_startsignDate" name="startsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'创建时间 例:yyyy-MM-dd'" style="width: 160px;"/>
		-
		<input id="search_instruction_endsignDate" name="endsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'创建时间 例:yyyy-MM-dd'" style="width: 160px;"/>
		<a id="search_instruction_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</div>
<div id="win_instructions" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:500px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_instructions" method="post" enctype="multipart/form-data">
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;" align="center">
				<input type="hidden" id="id" name="id">
				<table cellpadding="3">
					<tr>
						<td style="vertical-align: top;"><span style="letter-spacing: 0.4em">指令内容:</span></td>
						<td colspan="4">
							<input class="easyui-textbox" type="text" name="content" data-options="multiline:true" style="height: 100px;width: 340px;"/>
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 2em">附</span>件:</td>
						<td>
							<input class="easyui-filebox" id="file" name="file" style="width: 340px;height:25px;" data-options="buttonText:'选择',prompt:'文件'">
						</td>
					</tr>
					<tr>
						<td>接收对象公司:</td>
						<td>
							<input id="instructionsCompanyIdQuery" name="companyid"  class="easyui-combobox" style="width: 340px;" maxlength="16" data-options="editable:false,required:false,valueField:'id',textField:'text'"/>
						</td>
					</tr>
					<tr>
						<td>接收对象部门:</td>
						<td>
							<input id="instructionsDeptQuery" class="easyui-combobox" style="width: 340px;" data-options="editable:false,required:false,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 0.4em;">接收对象:</span></td>
						<td>
							<div class="easyui-layout" style="width:340px;height:190px;">
								<div data-options="region:'center',title:'待选对象(单击选择)'" style="padding:2px;">
									<ul id="adviseUserTree" class="ztree"></ul>
								</div>
								<div data-options="region:'east',title:'已选对象',collapsible:false" style="width:50%;padding:2px;">
									<ul id="adviseTree" class="ztree"></ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_instructions">确定</a>
			</div>
		</form>
	</div>
</div>
<div id="win_instructions_forward" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:450px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_instructions_forward" method="post" >
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;" align="center">
				<input type="hidden" id="fid" name="instid">
				<input type="hidden" id="parentIdss" name="parentid">
				<input type="hidden" id="objId" name="id">
				<table cellpadding="3">
					<tr>
						<td>对象公司:</td>
						<td>
							<input id="forwardCompanyIdByQuery" name="companyid"  class="easyui-combobox" style="width: 300px;" maxlength="16" data-options="editable:false,required:false,valueField:'id',textField:'text'"/>
						</td>
					</tr>
					<tr>
						<td>对象部门:</td>
						<td>
							<input id="forwardDeptQuery" class="easyui-combobox" style="width: 300px;" data-options="editable:false,required:false,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 0.4em;">转发对象:</span></td>
						<td>
							<div class="easyui-layout" style="width:300px;height:240px;">
								<div data-options="region:'center',title:'待选对象(单击选择)'" style="padding:2px;">
									<ul id="forwardOtherUserTree" class="ztree"></ul>
								</div>
								<div data-options="region:'east',title:'已选对象',collapsible:false" style="width:50%;padding:2px;">
									<ul id="forwardOtherTree" class="ztree"></ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_instructions_forward">确定</a>
			</div>
		</form>
	</div>
</div>
<div id="win_instructions_detail" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:500px;">
	<div class="easyui-layout" style="width:100%;height:100%;" data-options="fit:true">
		<div data-options="region:'center',title:'指令内容',border:false">
			<div class="easyui-layout" style="width:100%;height:100%;">
				<div data-options="region:'north',border:false" style="height:40%;border-bottom: 1px solid #95B8E7;">
					<form id="form_instructions_detail" method="post" >
						<input type="hidden" name="contractid" id="paymentContractId">
						<div data-options="region:'south',border:false" style="text-align:right;padding:0 0 0;">
							<table cellpadding="5">
								<tr>
									<td style="vertical-align: top">指令内容:</td>
									<td>
										<input class="easyui-textbox" readonly="readonly" id="instContent" type="text" name="content" data-options="multiline:true" style="height: 110px;width: 400px;"/>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div data-options="region:'center',title:'接收对象',border:false">
					<table id="grid_inst_receive_obj"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="win_feedback" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:600px;height:410px;">
	<div class="easyui-layout" data-options="fit:true">
		<%--显示列表--%>
		<table id="grid_feedback"></table>
	</div>
	<%--条件、按钮--%>
	<div id="feedback_toolbar" style="padding: 2px 2px;height: 65px;" align="center">
		<div style="float: left;">
			<form id="form_feedback" method="post" >
				<input type="hidden" name="instid" id="feedbackInstId">
				<div data-options="region:'south',border:false" style="text-align:right;padding:0 0 0;">
					<table cellpadding="5">
						<tr>
							<td>回馈内容:</td>
							<td>
								<input class="easyui-textbox" id="fcontent" type="text" name="fcontent" data-options="multiline:true,required:true" style="height: 50px;width: 430px;"/>
							</td>
							<td>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_feedback">确定</a>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
<!--  个人成本结束 -->
<script type="text/javascript" src="${ctx}/static/order/tree_instructions_manage.js"></script>
<script type="text/javascript" src="${ctx}/static/order/instructions_manage.js"></script>
<script type="text/javascript">
	$(".easyui-datebox").datebox({
		panelHeight:240
	});
	$.extend($.fn.validatebox.defaults.rules, {
		date: {// 验证姓名，可以是中文或英文
			validator: function (value) {
				//格式yyyy-MM-dd或yyyy-M-d
				return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
			},
			message: '清输入合适的日期格式'
		},
	});
	$("#search_instruction_startsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_instruction_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_date = $("#search_instruction_endsignDate").datebox('getValue');
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

	$("#search_instruction_endsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_instruction_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_data = $("#search_instruction_endsignDate").datebox('getValue');
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


