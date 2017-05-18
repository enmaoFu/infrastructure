<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<script type="text/javascript" src="${ctx}/static/common/js/util.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/common.js"></script>
<jsp:include page="../load/loadingDiv.jsp" flush="true"></jsp:include>
<jsp:include page="../costContract/cost_add_common.jsp" flush="true"></jsp:include>
<%--合同管理--%>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height: 100%;">
    <div data-options="region:'center',title : '合同管理',border:false">
        <%--显示列表--%>
        <table id="grid_pro_contract"></table>
    </div>
    <div data-options="region:'east',title:'制度',split:true,border:false" style="width:200px;">
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
<div id="pro_contract_toolbar" style="padding: 8px 10px;">
    <div style="float: left;">
        <a id="my_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-share_my',plain:true,selected:true">我的合同</a>
        <shiro:hasPermission name="proContract:dept">
            <a id="dept_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-share_all',plain:true">部门合同</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="proContract:company">
            <a id="company_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-share_all',plain:true">公司合同</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="proContract:sign">
            <a id="sign_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-speaker',plain:true">待签收</a>
        </shiro:hasPermission>
        <span id="toolbar_my_pro_contract" >
            <shiro:hasPermission name="proContract:add">
                <a id="add_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="proContract:update">
                <a id="edit_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="proContract:delete">
                <a id="remove_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="proContract:expireadd">
                <a id="add_pro_expireadd" class="easyui-linkbutton" data-options="iconCls:'icon-alarm_clock',plain:true">设置到期提醒</a>
            </shiro:hasPermission>
            <a id="tenders_warnday_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-speaker',plain:true">到期提醒(<span style="color: red" id="proContractSize"></span>)</a>
        </span>
        <span id="toolbar_company_pro_contract" >
            <shiro:hasPermission name="proContract:invoice">
                <a id="invoice_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">发票</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="proContract:payment">
                <a id="payment_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">回款</a>
            </shiro:hasPermission>
        </span>
        <span id="toolbar_sign_pro_contract" >
            <shiro:hasPermission name="proContract:confirmReceipt">
                <a id="confirm_receipt_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">确认签收</a>
            </shiro:hasPermission>
        </span>
        <span id="toolbar_section_pro_contract" >
            <shiro:hasPermission name="proContract:subSection">
                <a id="sub_section_pro_contract" class="easyui-linkbutton" data-options="iconCls:'icon-request',plain:true">分标段合同</a>
            </shiro:hasPermission>
        </span>
    </div>
    <div align="right">
        <span id="select_company_pro_contract">
            <shiro:hasPermission name="proContract:allCompany">
                <input type="hidden" value="1" id="isProContractPerm">
                <input id="proContractCompanyIdQuery" class="easyui-combobox" name="companyId" style="width: 150px;" data-options="
				prompt:'所属企业',
				editable:false,
				required:false,
				valueField:'id',
				textField:'text'" />
            </shiro:hasPermission>
            <input id="proContractDeptIdQueryDetail" class="easyui-combobox" name="deptId" style="width: 150px;" data-options="
                        prompt:'所属部门',
                        editable:false,
                        required:false,
                        valueField:'id',
                        textField:'text'" />
        </span>
        <span id="select_dept_pro_contract">
                <input id="proContractDeptIdQuery" class="easyui-combobox" name="deptId" style="width: 150px;" data-options="
                        prompt:'所属部门',
                        editable:false,
                        required:false,
                        valueField:'id',
                        textField:'text'" />
        </span>
        <input id="search_name" name="name" class="easyui-textbox" data-options="prompt:'合同名称',validType:['length[0,32]']" style="width: 120px;"/>
        <input id="search_customer" name="customerid"  class="easyui-combobox" maxlength="16" data-options="editable:false,prompt:'客户',valueField:'id',textField:'text',url:'${ctx}/sell/customer/finByCompanyId'"/>
        <input id="search_startsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-01-01',panelHeight:220" style="width: 160px;"/>
        -
        <input id="search_endsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-12-31',panelHeight:220" style="width: 160px;"/>
        <a id="search_pro_contract_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
    </div>
</div>
<div id="win_pro_contract" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,minimizable:false,iconCls:'icon-save'" style="width:360px;height:400px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_pro_contract" method="post" enctype="multipart/form-data">
            <div data-options="region:'center',border:false"  align="center">
                <input type="hidden" id="id" name="id">
                <input type="hidden" id="parentId" name="parentid">
                <input type="hidden" id="setContractNumber" name="contractNumber">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em">名</span>称:</td>
                        <td><input class="easyui-textbox" name="name" data-options="required:true,validType:['length[0,50]']" style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td>合同编号:</td>
                        <td><input class="easyui-textbox" name="number" data-options="required:true,validType:['length[0,25]']" style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">类</span>型:</td>
                        <td>
                            <input id="add_common_combobox" name="type" class="easyui-combobox" maxlength="50" data-options="editable:false,required:true,prompt:'选择常用合同类型',valueField:'id',textField:'text',url:'${ctx}/select/querySelect?s_mark=S002'"/>
                            <shiro:hasPermission name="proContract:classes">
                                <a id="add_common" lang="S002" class="easyui-linkbutton add_common" data-options="iconCls:'icon-add'"></a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                    <tr id="projectTr">
                        <td><span style="letter-spacing: 2em">项</span>目:</td>
                        <td>
                            <input id="projectId" name="projectid"  class="easyui-combobox" maxlength="16" style="width: 200px;" data-options="editable:false,prompt:'选择项目名称',required:true,valueField:'id',textField:'text',url:'${ctx}/project/finByCompanyId'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">金</span>额:</td>
                        <td>
                            <input class="input easyui-numberbox" min="0.01" precision="2" max="1000000000"  type="text" name="money" data-options="required:true" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>签订日期:</td>
                        <td>
                            <input class="easyui-datebox" data-options="editable:false,prompt:'签订日期 例:2016-01-01',panelHeight:220" name="signdate" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>到期日期:</td>
                        <td>
                            <input class="easyui-datebox" data-options="editable:false,prompt:'到期日期 例:2016-12-31',panelHeight:220" name="expiredate" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">附</span>件:</td>
                        <td>
                          <input class="easyui-filebox" id="file" name="file" style="width: 200px;height:25px;" data-options="buttonText:'选择',prompt:'文件'">
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">备</span>注:</td>
                        <td>
                            <input class="easyui-textbox" name="remark" data-options="multiline:true,validType:['length[0,125]']" style="width: 200px;height: 80px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
              <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_pro_contract">确定</a>
            </div>
        </form>
    </div>
</div>
<div id="win_pro_contract_detail" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,minimizable:false,iconCls:'icon-search'" style="width:560px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_pro_contract_detail" method="post">
            <div data-options="region:'center',border:false"  align="center">
                <input type="hidden" id="contractId" name="id">
                <table cellpadding="2">
                    <tr>
                        <td><span style="letter-spacing: 2em">名</span>称:</td>
                        <td><input class="easyui-textbox" name="name" data-options="required:true,validType:['length[0,50]']" readonly="readonly" style="width: 200px;"/></td>
                        <td><span style="letter-spacing: 2em">编</span>号:</td>
                        <td><input class="easyui-textbox" name="number" data-options="required:true,validType:['length[0,25]']" readonly="readonly" style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">类</span>型:</td>
                        <td>
                            <input name="type" class="easyui-combobox" maxlength="50" readonly="readonly" style="width: 200px;" data-options="editable:false,required:true,prompt:'选择常用合同类型',valueField:'id',textField:'text',url:'${ctx}/select/querySelect?s_mark=S002'"/>
                        </td>
                        <td><span style="letter-spacing: 2em">项</span>目:</td>
                        <td>
                            <input name="projectid"  class="easyui-combobox" maxlength="16" style="width: 200px;" readonly="readonly" data-options="editable:false,prompt:'选择项目名称',required:true,valueField:'id',textField:'text',url:'${ctx}/project/finByCompanyId'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">客</span>户:</td>
                        <td>
                            <input name="customerId"  class="easyui-combobox" readonly="readonly" style="width: 200px;" maxlength="16" data-options="editable:false,prompt:'选择客户名称',required:true,valueField:'id',textField:'text',url:'${ctx}/sell/customer/finByCompanyId'"/>
                        </td>
                        <td><span style="letter-spacing: 2em">金</span>额:</td>
                        <td>
                            <input class="input easyui-numberbox" min="0.01" precision="2" max="1000000000"  type="text" name="money" readonly="readonly" data-options="required:true" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>签订日期:</td>
                        <td>
                            <input class="easyui-datebox" readonly="readonly" data-options="editable:false,prompt:'签订日期 例:2016-01-01',panelHeight:220" name="signdate" style="width: 200px;"/>
                        </td>
                        <td>到期日期:</td>
                        <td>
                            <input class="easyui-datebox" readonly="readonly" data-options="editable:false,prompt:'到期日期 例:2016-12-31',panelHeight:220" name="expiredate" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="letter-spacing: 2em">备</span>注:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="remark" readonly="readonly" data-options="multiline:true,validType:['length[0,125]']" style="width: 460px;height: 80px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>签收意见:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" name="signForAdvice" readonly="readonly" data-options="multiline:true,validType:['length[0,125]']" style="width: 460px;height: 80px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="close_pro_contract">确定</a>
            </div>
        </form>
    </div>
</div>
<div id="win_sub_section" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:1200px;height:410px;">
    <div class="easyui-layout" data-options="fit:true">
        <%--显示列表--%>
        <table id="grid_sub_section"></table>
    </div>
    <%--条件、按钮--%>
    <div id="sub_section_toolbar" style="padding: 2px 2px;">
        <div style="float: left;" id="toolbar_sub">
            <shiro:hasPermission name="subSection:add">
                <a id="add_sub_section" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="subSection:update">
                <a id="edit_sub_section" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="subSection:delete">
                <a id="remove_sub_section" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="subSection:invoice">
                <a id="invoice_sub_section" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">发票</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="subSection:payment">
                <a id="payment_sub_section" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">回款</a>
            </shiro:hasPermission>
        </div>
        <div align="right">
            <input id="search_sub_section_name" name="name" class="easyui-textbox" data-options="prompt:'合同名称',validType:['length[0,32]']" style="width: 120px;"/>
            <input id="search_sub_section_customer" name="customerid"  class="easyui-combobox" maxlength="16" data-options="editable:false,prompt:'客户',valueField:'id',textField:'text',url:'${ctx}/sell/customer/finByCompanyId'"/>
            <input id="search_sub_section_startsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-01-01',panelHeight:220" style="width: 160px;"/>
            -
            <input id="search_sub_section_endsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-12-31',panelHeight:220" style="width: 160px;"/>
            <a id="search_sub_section_pro_contract_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
</div>
<!--到期提醒-->
<div id="win_expiration_reminder_list" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:1200px;height:410px;">
    <div class="easyui-layout" data-options="fit:true">
        <%--显示列表--%>
        <table id="grid_expiration_reminder_list"></table>
    </div>
    <%--条件、按钮--%>
    <div id="expiration_reminder_list_toolbar" style="padding: 2px 2px;">
        <div align="right">
            <input id="search_expiration_reminder_name" name="name" class="easyui-textbox" data-options="prompt:'合同名称',validType:['length[0,32]']" style="width: 120px;"/>
            <input id="search_expiration_reminder_customer" name="customerid"  class="easyui-combobox" maxlength="16" data-options="editable:false,prompt:'客户',valueField:'id',textField:'text',url:'${ctx}/sell/customer/finByCompanyId'"/>
            <input id="search_expiration_reminder_startsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-01-01',panelHeight:220" style="width: 160px;"/>
            -
            <input id="search_expiration_reminder_endsignDate" class="easyui-datebox" data-options="validType:'date[this]',prompt:'签订时间 例:2016-12-31',panelHeight:220" style="width: 160px;"/>
            <a id="search_expiration_reminder_pro_contract_btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
</div>
<div id="win_confirm_receipt_contract_project" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:330px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_confirm_receipt_contract_project" method="post" >
            <div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
                <input type="hidden" id="confirmReceiptId" name="id">
                <table cellpadding="5">
                    <tr>
                        <td>签收状态:</td>
                        <td>
                            <input id="noIsReport" type="radio" name="isreport" checked="checked" value="Y" />签收
                            <input id="yesIsReport" type="radio" name="isreport" value="R" />驳回
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;">签收意见:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" id="signForAdvice" type="text" name="signForAdvice" data-options="multiline:true" style="height: 90px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_confirm_receipt_contract_project">确定</a>
            </div>
        </form>
    </div>
</div>
<div id="win_invoice_pro_contract" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:600px;height:410px;">
    <div class="easyui-layout" data-options="fit:true">
        <%--显示列表--%>
        <table id="grid_invoice_pro_contract"></table>
    </div>
    <%--条件、按钮--%>
    <div id="invoice_pro_contract_toolbar" style="padding: 2px 2px;height: 35px;" align="center">
        <div style="float: left;">
            <form id="form_invoice_pro_contract" method="post" >
                <input type="hidden" name="contractid" id="invoiceContractId">
                <div data-options="region:'south',border:false" style="text-align:right;padding:0 0 0;">
                    <table cellpadding="5">
                        <tr>
                            <td><span style="letter-spacing: 2em"></span>金额:</td>
                            <td>
                                <input class="input easyui-numberbox" min="0.01" precision="2" max="1000000000"  type="text" name="money"  data-options="required:true" />
                            </td>
                            <td>开票时间:</td>
                            <td>
                                <input class="easyui-datebox" data-options="editable:false,required:true,prompt:'开票时间 例:2016-01-01',panelHeight:220" name="billingtime" style="width: 200px;"/>
                            </td>
                            <td>
                                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_invoice_pro_contract">确定</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="win_payment_pro_contract" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,iconCls:'icon-tip',maximizable:false" style="width:600px;height:410px;">
    <div class="easyui-layout" data-options="fit:true">
        <%--显示列表--%>
        <table id="grid_payment_pro_contract"></table>
    </div>
    <%--条件、按钮--%>
    <div id="payment_pro_contract_toolbar" style="padding: 2px 2px;height: 35px;" align="center">
        <div style="float: left;">
            <form id="form_payment_pro_contract" method="post" >
                <input type="hidden" name="contractid" id="paymentContractId">
                <div data-options="region:'south',border:false" style="text-align:right;padding:0 0 0;">
                    <table cellpadding="5">
                        <tr>
                            <td><span style="letter-spacing: 2em"></span>金额:</td>
                            <td>
                                <input class="input easyui-numberbox" min="0.01" precision="2" max="1000000000"  type="text" name="money"  data-options="required:true" />
                            </td>
                            <td>开票时间:</td>
                            <td>
                                <input class="easyui-datebox" data-options="editable:false,required:true,prompt:'开票时间 例:2016-01-01',panelHeight:220" name="payment" style="width: 200px;"/>
                            </td>
                            <td>
                                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_payment_pro_contract">确定</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
<%--设置到期提醒页面--%>
<div id="win_expiration_reminder" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-alarm_clock'" style="width:350px;height:130px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_expiration_reminder" method="post">
            <input type="hidden" name="id">
            <div data-options="region:'center',border:false" style="padding-left:40px;">
                <table cellpadding="2">
                    <tr>
                        <td>提前提醒天数:</td>
                        <td>
                            <input class="easyui-numberbox" name="days" data-options="required:true,min:0,precision:0" />
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_expiration_reminder">确定</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var proContractCompanyId = '${sessionScope.userinfo_session.companyId}';
    $.extend($.fn.validatebox.defaults.rules, {
        date: {// 验证姓名，可以是中文或英文
            validator: function (value) {
                //格式yyyy-MM-dd或yyyy-M-d
                return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
            },
            message: '请输入合适的日期格式'
        },
    });
    //签订时间 开始
    $("#search_startsignDate").datebox({
        panelHeight:240,
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
    //签订时间 结束
    $("#search_endsignDate").datebox({
        panelHeight:240,
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

    //签订时间 开始
    $("#search_sub_section_startsignDate").datebox({
        panelHeight:240,
        onSelect:function(date){
            var start_data = $("#search_sub_section_startsignDate").datebox('getValue');
            var start_data_temple = start_data!=null&&start_data!=''?
                    new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

            var end_date = $("#search_sub_section_endsignDate").datebox('getValue');
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
    //签订时间 结束
    $("#search_sub_section_endsignDate").datebox({
        panelHeight:240,
        onSelect:function(date){
            var start_data = $("#search_sub_section_startsignDate").datebox('getValue');
            var start_data_temple = start_data!=null&&start_data!=''?
                    new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

            var end_data = $("#search_sub_section_endsignDate").datebox('getValue');
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


    //签订时间 开始
    $("#search_expiration_reminder_startsignDate").datebox({
        panelHeight:240,
        onSelect:function(date){
            var start_data = $("#search_expiration_reminder_startsignDate").datebox('getValue');
            var start_data_temple = start_data!=null&&start_data!=''?
                    new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

            var end_date = $("#search_expiration_reminder_endsignDate").datebox('getValue');
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
    //签订时间 结束
    $("#search_expiration_reminder_endsignDate").datebox({
        panelHeight:240,
        onSelect:function(date){
            var start_data = $("#search_expiration_reminder_startsignDate").datebox('getValue');
            var start_data_temple = start_data!=null&&start_data!=''?
                    new Date(start_data+"".replace("-", "/").replace("-", "/")).getTime():'';

            var end_data = $("#search_expiration_reminder_endsignDate").datebox('getValue');
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
<script type="text/javascript" src="${ctx}/static/contract/pro_contract_manage.js"></script>
