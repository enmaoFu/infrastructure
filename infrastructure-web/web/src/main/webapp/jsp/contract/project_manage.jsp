<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/static/common/js/util.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<script type="text/javascript">
	var projectCompanyId = '${sessionScope.userinfo_session.companyId}';
</script>
<div class="easyui-layout" style="width:100%;height: 100%;" data-options="fit:true">
	<div data-options="region:'center',title:'项目管理 ',border:false">
		<%--显示列表--%>
		<table id="grid_contract_project"></table>
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
<div id="contract_project_toolbar" style="padding: 8px 10px;height: 20px;" align="center">
	<div style="float: left;">
		<shiro:hasPermission name="contractProject:add">
			<a id="add_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="contractProject:update">
			<a id="edit_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="contractProject:delete">
			<a id="remove_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="contractProject:distribution">
			<a id="distribution_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">分配</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="contractProject:completionStatus">
			<a id="completion_status_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">完成情况</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="contractProject:progressSituation">
			<a id="progress_situation_contract_project" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true">进展情况</a>
		</shiro:hasPermission>
	</div>
	<div align="right">
		<shiro:hasPermission name="project:allCompany">
			<input type="hidden" value="1" id="isProjectPerm">
			<input id="projectCompanyIdQuery" class="easyui-combobox" name="companyId" style="width: 150px;" data-options="
				prompt:'所属企业',
				editable:false,
				required:false,
				valueField:'id',
				textField:'text'" />
		</shiro:hasPermission>
		<input id="search_project_name" name="projectName" class="easyui-textbox" data-options="prompt:'项目名称'">
		<input id="search_contract_project_startsignDate" name="startsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'创建时间 例:yyyy-MM-dd'" style="width: 160px;"/>
		-
		<input id="search_contract_project_endsignDate" name="endsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'创建时间 例:yyyy-MM-dd'" style="width: 160px;"/>
		<a id="search_contract_project_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</div>
<div id="win_contract_project" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:400px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_contract_project" method="post" >
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;" align="center">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="setProjectNum" name="projectNum">
				<table cellpadding="2">
					<tr>
						<td><span style="letter-spacing: 5em;">名</span>称:</td>
						<td>
							<input id="projectName" name="projectname" style="width: 240px;" class="easyui-textbox select_contract_project" maxlength="25" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 5em;">编</span>号:</td>
						<td>
							<input id="projectNum" name="projectnum" style="width: 240px;" class="easyui-textbox select_contract_project" maxlength="25" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 0.7em;">客户名称:</span></td>
						<td>
							<input id="customerId" name="customerid" style="width: 240px;" class="easyui-combobox select_contract_project" maxlength="16" data-options="editable:false,prompt:'选择客户名称',required:true,valueField:'id',textField:'text',url:'${ctx}/sell/customer/finByCompanyId'"/>
						</td>
					</tr>
					<tr>
						<td>营销负责人部门:</td>
						<td>
							<input id="marketersDeptId" name="deptId" class="easyui-combobox select_contract_project" style="width: 240px;" data-options="editable:false,required:false,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 0.4em;">营销负责人:</span></td>
						<td>
							<input id="marketersUserId" class="easyui-combobox select_contract_project" style="width: 240px;" name="marketers" data-options="editable:false,required:false,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr class="contractDetail">
						<td><span style="letter-spacing: 0.7em;">完成状态:</span></td>
						<td>
							<input id="noCompletion" type="radio" name="completion" checked="checked" value="N" disabled/>未完成
							<input id="yesCompletion" type="radio" name="completion" value="Y" disabled/>已完成
						</td>
					</tr>
					<tr class="contractDetail">
						<td><span style="letter-spacing: 0.7em;">分配时间:</span></td>
						<td>
							<input id="search_distributionTime" readonly="readonly" name="distributiontime" class="easyui-datebox" data-options="validType:'date[this]',prompt:'分配时间 例:yyyy-MM-dd'" style="width: 240px;"/>
						</td>
					</tr>
					<tr class="contractDetail">
						<td><span style="letter-spacing: 0.7em;">完成时间:</span></td>
						<td>
							<input id="search_finishTime" name="finishtime" readonly="readonly" class="easyui-datebox" data-options="validType:'date[this]',prompt:'完成时间 例:yyyy-MM-dd'" style="width: 240px;"/>
						</td>
					</tr>
					<tr class="contractDetail">
						<td style="vertical-align: top;"><span style="letter-spacing: 0.7em;">分配描述</span>:</td>
						<td colspan="4">
							<input class="easyui-textbox" type="text" readonly="readonly" name="descr" data-options="multiline:true" style="height: 70px;width: 240px;"/>
						</td>
					</tr>
					<tr class="contractDetail">
						<td style="vertical-align: top;"><span style="letter-spacing: 0.7em;">完成情况</span>:</td>
						<td colspan="4">
							<input class="easyui-textbox" type="text" readonly="readonly" name="options" data-options="multiline:true" style="height: 70px;width: 240px;"/>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_contract_project">确定</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-reload'" id="reset_contract_project">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="close_contract_project" style="display: none" >关闭</a>
			</div>
		</form>
	</div>
</div>
<div id="win_distribution_contract_project" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:330px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_distribution_contract_project" method="post" >
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
				<input type="hidden" id="projectId" name="id">
				<table cellpadding="5">
					<tr>
						<td>技术负责人部门:</td>
						<td>
							<input id="headDeptId" class="easyui-combobox" data-options="editable:false,required:true,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr>
						<td><span style="letter-spacing: 0.4em;">技术负责人:</span></td>
						<td>
							<input id="headUserId" class="easyui-combobox" name="head" data-options="editable:false,required:true,valueField:'id',textField:'text'" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;"><span style="letter-spacing: 0.7em;">分配描述</span>:</td>
						<td colspan="4">
							<input class="easyui-textbox" type="text" name="descr" data-options="multiline:true" style="height: 90px;"/>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_distribution_contract_project">确定</a>
			</div>
		</form>
	</div>
</div>
<div id="win_completion_status_contract_project" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:330px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_completion_status_contract_project" method="post" >
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
				<input type="hidden" id="projectCompletionStatusId" name="id">
				<table cellpadding="5">
					<tr>
						<td>完成状态:</td>
						<td>
							<input id="noCompletionStatusCompletion" type="radio" name="completion" checked="checked" value="N" />未完成
							<input id="yesCompletionStatusCompletion" type="radio" name="completion" value="Y" />已完成
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">完成情况:</td>
						<td colspan="4">
							<input class="easyui-textbox" id="optionsMult" type="text" name="options" data-options="multiline:true" style="height: 90px;"/>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_completion_status_contract_project">确定</a>
			</div>
		</form>
	</div>
</div>
<div id="win_progress_situation" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:600px;height:410px;">
	<div class="easyui-layout" data-options="fit:true">
		<%--显示列表--%>
		<table id="grid_progress_situation"></table>
	</div>
	<%--条件、按钮--%>
	<div id="progress_situation_toolbar" style="padding: 2px 2px;height: 65px;" align="center">
		<div style="float: left;">
			<form id="form_progress_situation" method="post" >
				<input type="hidden" name="projectId" id="progressSituationProjectId">
				<div data-options="region:'south',border:false" style="text-align:right;padding:0 0 0;">
					<table cellpadding="5">
						<tr>
							<td>进度描述:</td>
							<td>
								<input class="easyui-textbox" id="prodescribeMult" type="text" name="prodescribe" data-options="multiline:true,required:true" style="height: 50px;width: 430px;"/>
							</td>
							<td>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_progress_situation">确定</a>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
<!--  个人成本结束 -->
<script type="text/javascript" src="${ctx}/static/contract/project_manage.js"></script>
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
	$("#search_contract_project_startsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_contract_project_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_date = $("#search_contract_project_endsignDate").datebox('getValue');
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

	$("#search_contract_project_endsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_contract_project_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_data = $("#search_contract_project_endsignDate").datebox('getValue');
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


