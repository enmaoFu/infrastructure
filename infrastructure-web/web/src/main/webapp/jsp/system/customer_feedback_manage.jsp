<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/static/common/js/util.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<div class="easyui-layout" style="width:100%;height: 100%;" data-options="fit:true">
	<div data-options="region:'center',title:'客户反馈 ',border:false">
		<%--显示列表--%>
		<table id="grid_customer_feedback"></table>
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
<div id="customer_feedback_toolbar" style="padding: 8px 10px;height: 20px;" align="center">
	<div style="float: left;">
		创建时间：
		<input id="search_customer_feedback_startsignDate" class="easyui-datebox" type="text" name="startsignDate" style="width: 100px;" data-options="validType:'date[this]',prompt:'yyyy-mm-dd'"/>
		至
		<input id="search_customer_feedback_endsignDate" class="easyui-datebox" type="text" name="endsignDate" style="width: 100px;" data-options="validType:'date[this]',prompt:'yyyy-mm-dd'"/>
		<a id="search_customer_feedback_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</div>
<div id="win_customer_feedback" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:555px;height:210px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<form id="form_customer_feedback" method="post" >
			<div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
				<input type="hidden" id="id" name="id">
				<table cellpadding="5">
					<tr>
						<td style="vertical-align: top;">反馈意见:</td>
						<td colspan="4">
							<input class="easyui-textbox" type="text" name="content" data-options="multiline:true,width:420,height:100" readonly="readonly" maxlength="1000"/>
						</td>
					</tr>
				</table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="close_customer_feedback">关闭</a>
			</div>
		</form>
	</div>
</div>
<!--  个人成本结束 -->
<script type="text/javascript" src="${ctx}/static/system/customer_feedback_manage.js"></script>
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
	$("#search_customer_feedback_startsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_customer_feedback_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_date = $("#search_customer_feedback_endsignDate").datebox('getValue');
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

	$("#search_customer_feedback_endsignDate").datebox({
		onSelect:function(date){
			var start_data = $("#search_customer_feedback_startsignDate").datebox('getValue');
			var start_data_temple = start_data!=null&&start_data!=''?
					new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

			var end_data = $("#search_customer_feedback_endsignDate").datebox('getValue');
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


