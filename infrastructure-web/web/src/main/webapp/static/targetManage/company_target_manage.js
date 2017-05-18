//表格对象
var grid_company_target;
//表单
var form_company_target;
//获取窗口
var win_company_target;
//请求远程数据时发送的额外参数
var queryParams = {

};
var ok_company_target;
var reset_company_target;
var close_company_target;
var companyTargetCompanyIdQuery;
var companyTargetPerm;
var targetType;
var addCompanyTargetAmount;
var targetYear;
var targetTimeSlot;
var win_dept_target_detail;
var grid_dept_target_detail;
var deptTargetDetailUserIdQuery;
var win_dept_target_detail_detail;
var form_dept_target_detail;
var addTargetAmountDetail;
var close_dept_target_detail;
var selCompanyTargetId;
var companyTargetType
var companyTargetTimeSlot;
var companyDeptTargetYear
$(function(){
    //获取表格对象
    grid_company_target = $("#grid_company_target");
    //获取表单数据
    form_company_target= $("#form_company_target");
    //获取窗口
    win_company_target = $("#win_company_target");
    ok_company_target = $("#ok_company_target");
    reset_company_target = $("#reset_company_target");
    close_company_target = $("#close_company_target");
    companyTargetCompanyIdQuery = $("#companyTargetCompanyIdQuery");
    companyTargetPerm = $("#companyTargetPerm");
    targetType = $("#targetType");
    addCompanyTargetAmount = $("#addCompanyTargetAmount");
    targetYear = $("#targetYear");
    targetTimeSlot = $("#targetTimeSlot");
    win_dept_target_detail = $("#win_dept_target_detail");
    grid_dept_target_detail = $("#grid_dept_target_detail");
    deptTargetDetailUserIdQuery = $("#deptTargetDetailUserIdQuery");
    win_dept_target_detail_detail = $("#win_dept_target_detail_detail");
    form_dept_target_detail = $("#form_dept_target_detail");
    addTargetAmountDetail = $("#addTargetAmountDetail");
    close_dept_target_detail = $("#close_dept_target_detail");

    /**
     * 初始化grid_company_target
     */
    grid_company_target.datagrid({
        url : PATH + '/companyTarget/loadCompanyTarget',
        //title : '目标管理',
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        sortName : 'createTime',
        sortOrder : 'desc',
        pageSize : pageSize,
        pageList : pageList,
        toolbar : '#company_target_toolbar',
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.05},
            {field:'targetTitle',title:'公司目标标题',width:$(window).width() * 0.2},
            {field:'targetTimeSlot',title:'目标时间段',width:$(window).width() * 0.1,
                formatter:function(value,row,index){
                    if(value == 'slot_year'){
                        return '年度'
                    }else if(value == 'slot_half_year'){
                        return '半年度';
                    }else {
                        return '';
                    }
                }},
            {field:'targetType',title:'公司目标类型',width:$(window).width() * 0.1,
                formatter:function(value,row,index){
                    if(value == 'type_economic_goals'){
                        return '经济目标'
                    }else if(value == 'type_management_objectives'){
                        return '管理目标';
                    }else {
                        return '';
                    }
                }},
            {field:'targetAmount',title:'公司目标金额',width:$(window).width() * 0.15,formatter:function(value,row,index){
                if(value == "" || value == null || value == 'undefined'){
                    return value;
                }else {
                    return util.strFormatAmount(value,1);
                }
            }},
            {field:'coName',title:'所属公司',width:$(window).width() * 0.2},
            {field: 'id',
                title: '操作',
                align:'center',
                width:$(window).width() * 0.2,
                formatter:function(value,row,index){
                    var str="<a href='javascript:void(0)' onclick='select_target_detail(\""+row.id+"\",\""+row.targetType+"\")' style='text-decoration: none;'><font style='color: green'>查看详情</font></a>";
                    var strDeptTarget="<a href='javascript:void(0)' onclick='select_dept_target(\""+row.id+"\",\""+row.companyId+"\",\""+row.targetType+"\",\""+row.targetTimeSlot+"\",\""+row.targetYear+"\")' style='text-decoration: none;'><font style='color: green'>部门目标</font></a>";
                    str += "&nbsp;"+strDeptTarget;
                    return str;
                }},
        ]],
        onDblClickRow: function(index, row){
            select_target_detail(row.id,row.targetType);
        }
    });

    /**
     * 查询所属企业改变事件
     */
    companyTargetCompanyIdQuery.combobox({
        valueField : 'id',
        textField : 'text',
        method : 'post',
        url : PATH +'/system/company/findByCid',
        icons : [
            {
                iconCls:'icon-clear',
                handler : function(){
                    $(this).parent().parent().prev().combobox("clear");
                }
            }
        ],
        editable:false
    });


    /**
     * 新增公司目标按钮点击事件
     */
    $("#add_company_target").on("click",function(){
        addCompanyTargetAmount.empty();
        targetYear.numberbox("readonly",false);
        targetTimeSlot.combobox('readonly',false);
        targetType.combobox('readonly',false);
        $(".company_target").textbox("readonly",false);
        var strType = "<td><span style='letter-spacing: 3em;'>类</span>型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' style='width: 170px;' id='targetType' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_economic_goals'," +
            "value: '经济目标'" +
            "},{" +
            "label: 'type_management_objectives'," +
            "value: '管理目标'" +
            "}]," +
            "onSelect : function(record){" +
            "loadTargetAmount(record.label);" +
            "},"+
            "\" />" +
            "</td>";
        var strTypeObj = $(strType).appendTo(addCompanyTargetAmount);
        $.parser.parse(strTypeObj);
        //设置请求路径
        form_company_target.attr("action",PATH + "/companyTarget/insert");
        //打开窗口
        win_company_target.window({
            title: "新增公司目标",
            iconCls: 'icon-add',
            closed: false,
            draggable:true
        });
        //将窗口绝对居中
        win_company_target.window("center");
        form_company_target.form("reset");
        if(ok_company_target.css("display") == "none"){
            ok_company_target.css("display","");
        }
        if(reset_company_target.css("display") == "none"){
            reset_company_target.css("display","");
        }
        if(close_company_target.css("display") == "" || close_company_target.css("display") == "inline-block"){
            close_company_target.css("display","none");
        }
    });

    /**
     * 确认按钮点击事件
     */
    ok_company_target.on("click",function(){
        var $this = $(this);
        form_company_target = $(this).parent().parent().parent();
        //验证是否通过
        var ival = form_company_target.form("validate");
        if(ival){
            $this.linkbutton('disable');
            $this.linkbutton({
                iconCls: 'icon-loding'
            });
            var action = form_company_target.attr("action");
            $(this)
            //验证通过 请求后台
            form_company_target.form('submit',{
                url : action,
                success : function(data){
                    $this.linkbutton('enable');
                    $this.linkbutton({
                        iconCls: 'icon-ok'
                    });
                    var dataJson = $.parseJSON(data);
                    var flag = dataJson.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",dataJson.message,icon,function(){
                        if(dataJson.success){
                            form_company_target.form("clear");
                            win_company_target.window("close");
                            //清除所有选择的行
                            grid_company_target.datagrid('clearSelections');
                            //属性表格
                            grid_company_target.datagrid('reload');
                        }
                    });
                }
            });
        }
    });
    /**
     * 重置按钮
     */
    reset_company_target.on("click",function(){
        var title = win_company_target.panel("options").title;
        if(title == '新增公司目标'){
            form_company_target.form("reset");
        }else {
            //获取选中记录
            var selectRows = grid_company_target.datagrid("getSelections");
            //重置表单数据
            fun_edit_company_target(selectRows[0].id);
        }
    });

    //关闭窗口
    close_company_target.on("click",function(){
        win_company_target.window('close', true);
    });

    /**
     *编辑按钮点击事件
     */
    $("#edit_company_target").on("click",function(){
        addCompanyTargetAmount.empty();
        var strType = "<td><span style='letter-spacing: 3em;'>类</span>型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' style='width: 170px;' id='targetType' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_economic_goals'," +
            "value: '经济目标'" +
            "},{" +
            "label: 'type_management_objectives'," +
            "value: '管理目标'" +
            "}]," +
            "onSelect : function(record){" +
            "loadTargetAmount(record.label);" +
            "},"+
            "\" />" +
            "</td>";
        var strTypeObj = $(strType).appendTo(addCompanyTargetAmount);
        $.parser.parse(strTypeObj);
        //设置保存请求路径
        form_company_target.attr("action",PATH + "/companyTarget/update");
        //获取所有选中记录
        var selectRows = grid_company_target.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要编辑的数据","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据","warning");
            return;
        }
        //ID
        var id = selectRows[0].id;
        var types = selectRows[0].targetType;
        if(types == "type_economic_goals"){
            var strAmoType = "<td id='amounts'><span style='letter-spacing: 1em;'>金</span>额:</td>" +
                "<td id='amountTarget'>" +
                "<input class='input easyui-numberbox' min='0.01' precision='2' max='10000000000'  type='text' name='targetAmount' style='width: 170px;' data-options=\"required:true\" />" +
                "</td>";
            var strAmoTypeObj = $(strAmoType).appendTo(addCompanyTargetAmount);
            $.parser.parse(strAmoTypeObj);
        }
        //填充表单数据
        fun_edit_company_target(id);
        //打开窗口
        win_company_target.window({
            title: "编辑公司目标",
            iconCls: 'icon-edit',
            closed: false,
            draggable:true
        });
        //将窗口绝对居中
        win_company_target.window("center");
        if(ok_company_target.css("display") == "none"){
            ok_company_target.css("display","");
        }
        if(reset_company_target.css("display") == "none"){
            reset_company_target.css("display","");
        }
        if(close_company_target.css("display") == "" || close_company_target.css("display") == "inline-block"){
            close_company_target.css("display","none");
        }
    });
    /**
     * 删除按钮点击事件
     */
    $("#remove_company_target").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = grid_company_target.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要删除的数据","warning");
            return;
        }
        for(var i=0;i<selectRows.length;i++){
            var row = selectRows[i];
                ids.push(row.id);
        }
        $.messager.confirm("提示","您确认要删除记录吗？",function(r){
            if(!r){
                return;
            }
            $.ajax({
                type : 'post',
                url : PATH + '/companyTarget/delete',
                data : {
                    ids : ids
                },
                success : function(data){
                    data = eval("("+data+")");
                    var icon = "error";
                    if(data.success){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(data.success){
                            //清除所有选择的行
                            grid_company_target.datagrid('clearSelections');
                            //属性表格
                            grid_company_target.datagrid('reload');
                        }
                    });
                }
            });
        });
    });
});
//填充表单数据
function fun_edit_company_target(id,sel){
    form_company_target.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/companyTarget/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_company_target.form({
                onLoadSuccess : function(data){
                    if(data != null){
                        if(sel != null && sel != "" && sel != "undefined"){
                            targetYear.numberbox("readonly");
                            targetTimeSlot.combobox('readonly');
                            targetType.combobox('readonly');
                            $(".company_target").textbox("readonly");
                            ok_company_target.css("display","none");
                            reset_company_target.css("display","none");
                            close_company_target.css("display","");
                        }else{
                            targetYear.numberbox("readonly",false);
                            targetTimeSlot.combobox('readonly',false);
                            targetType.combobox('readonly',false);
                            $(".company_target").textbox("readonly",false);
                            ok_company_target.css("display","");
                            reset_company_target.css("display","");
                            close_company_target.css("display","none");
                        }
                    }
                }
            });
            form_company_target.form("load",data);
        }
    });
}
/**
 * 查看个人成本
 */
function select_target_detail(id,type){
    addCompanyTargetAmount.empty();
    var strType = "<td><span style='letter-spacing: 3em;'>类</span>型:</td>" +
        "<td id='encoTarget'>" +
        "<input class='easyui-combobox' readonly='readonly' name='targetType' style='width: 170px;' id='targetType' data-options=\"required:true," +
        "editable:false," +
        "valueField: 'label'," +
        "textField: 'value'," +
        "data: [{" +
        "label: 'type_economic_goals'," +
        "value: '经济目标'" +
        "},{" +
        "label: 'type_management_objectives'," +
        "value: '管理目标'" +
        "}]," +
        "onSelect : function(record){" +
        "loadTargetAmount(record.label);" +
        "},"+
        "\" />" +
        "</td>";
    var strTypeObj = $(strType).appendTo(addCompanyTargetAmount);
    $.parser.parse(strTypeObj);
    if(type == "type_economic_goals"){
        var strAmoType = "<td id='amounts'>金&nbsp;&nbsp;&nbsp;额:</td>" +
            "<td id='amountTarget'>" +
            "<input class='input easyui-numberbox' min='0.01' precision='2' max='1000000000'  type='text' name='targetAmount' readonly='readonly' style='width: 170px;' data-options=\"required:true\" />" +
            "</td>";
        var strAmoTypeObj = $(strAmoType).appendTo(addCompanyTargetAmount);
        $.parser.parse(strAmoTypeObj);
    }
    //填充表单数据
    fun_edit_company_target(id,"select");
    //打开窗口
    win_company_target.window({
        title: "查看公司目标",
        iconCls: 'icon-search',
        closed: false,
        draggable:true
    });
    //将窗口绝对居中
    win_company_target.window("center");
}
/**
 * 搜索
 */
$("#search_company_target_btn").on("click",function(){
    //标题
    var title = $("#company_target_title").val();
    if(companyTargetPerm != "undefined" && companyTargetPerm != null && companyTargetPerm != ""){
        //所属企业
        var companyId = companyTargetCompanyIdQuery.combobox("getValue");
    }
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para= '';
    if(companyTargetPerm != "undefined" && companyTargetPerm != null && companyTargetPerm != ""){
        para = '{"title":"'+title+'","companyId":"'+companyId+'"}';
        queryParams={
            companyId:companyId,
            title: title
        }
    }else {
        para = '{"title":"'+title+'"}';
        queryParams={
            title: title
        }
    }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_company_target.datagrid("load",para);
});

/**
 * 目标类型改变事件
 */
function loadTargetAmount(label){
        if(label == "type_economic_goals"){
            var strAmoType = "<td class='amountTarget'>金&nbsp;&nbsp;&nbsp;额:</td>" +
                "<td class='amountTarget'>" +
                "<input class='input easyui-numberbox' min='0.01' precision='2' max='1000000000'  type='text' name='targetAmount' style='width: 170px;' data-options=\"required:true\" />" +
                "</td>";
            var strAmoTypeObj = $(strAmoType).appendTo(addCompanyTargetAmount);
            $.parser.parse(strAmoTypeObj);
        }else {
            var tdLength = addCompanyTargetAmount.find("td");
            if(tdLength.length > 2){
                addCompanyTargetAmount.find("td")[3].remove();
                addCompanyTargetAmount.find("td")[2].remove();
            }
        }
}
/**
 * 查看部门目标
 * @param id
 */
function select_dept_target(companyTargetId,companyId,targetType,targetTimeSlot,deptTargetYear){
    loadDeptTarget(companyTargetId,companyId,targetType,targetTimeSlot,deptTargetYear);
    win_dept_target_detail.window('open');
    win_dept_target_detail.window("setTitle","部门目标");
    //将窗口绝对居中
    win_dept_target_detail.window("center");

}
//加载部门目标
function loadDeptTarget(companyTargetId,companyId,targetType,targetTimeSlot,deptTargetYear){
    selCompanyTargetId = companyTargetId;
    companyTargetType = targetType;
    companyTargetTimeSlot = targetTimeSlot;
    companyDeptTargetYear = deptTargetYear;
    /**
     * 加载部门
     */
    deptTargetDetailUserIdQuery.combobox({
        valueField: 'id',
        textField: 'text',
        method: 'post',
        url: PATH + '/system/dept/findDept',
        onBeforeLoad: function (param) {
            param.companyId = companyId;

        },
        icons : [
            {
                iconCls:'icon-clear',
                handler : function(){
                    $(this).parent().parent().prev().combobox("clear");
                }
            }
        ]
    });
    /**
     * 初始化grid_dept_target_detail
     */
    grid_dept_target_detail.datagrid({
        url : PATH + '/deptTarget/loadDeptTarget',
        queryParams : {
            companyTargetId : companyTargetId,
            companyId:companyId,
            targetType:targetType,
            targetTimeSlot:targetTimeSlot,
            deptTargetYear:deptTargetYear
        },
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        sortName : 'createTime',
        sortOrder : 'desc',
        toolbar : grid_dept_target_detail.parent().next(),
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.05},
            {field:'deptTargetTitle',title:'部门目标标题',width:$(window).width() * 0.35},
            {field:'deptTargetTimeSlot',title:'目标时间段',width:$(window).width() * 0.1,
                formatter:function(value,row,index){
                    if(value == 'slot_year'){
                        return '年度'
                    }else if(value == 'slot_half_year'){
                        return '半年度';
                    }else {
                        return '';
                    }
                }},
            {field:'deptTargetType',title:'部门目标类型',width:$(window).width() * 0.1,
                formatter:function(value,row,index){
                    if(value == 'type_economic_goals'){
                        return '经济目标'
                    }else if(value == 'type_management_objectives'){
                        return '管理目标';
                    }else {
                        return '';
                    }
                }},
            {field:'deptTargetAmount',title:'部门目标金额',width:$(window).width() * 0.1,formatter:function(value,row,index){
                if(value == "" || value == null || value == 'undefined'){
                    return value;
                }else {
                    return util.strFormatAmount(value,1);
                }
            }},
            {field:'deptName',title:'所属部门',width:$(window).width() * 0.1},
            {field: 'id',
                title: '操作',
                align:'center',
                width:$(window).width() * 0.2,
                formatter:function(value,row,index){
                    var str="<a href='javascript:void(0)' onclick='select_dept_target_detail_detail(\""+row.id+"\",\""+row.deptTargetType+"\")' style='text-decoration: none;'><font style='color: green'>查看详情</font></a>";
                    return str;
                }},
        ]],
        onDblClickRow: function(index, row){
            select_dept_target_detail_detail(row.id,row.deptTargetType);
        }
    });

    //关闭窗口
    close_dept_target_detail.on("click",function(){
        win_dept_target_detail_detail.window('close', true);
    });
    /**
     * 关闭按钮监听
     */
    win_dept_target_detail.window({
        onBeforeClose:function(){
            //清除所有选择的行
            grid_dept_target_detail.datagrid('clearSelections');
        }
    });
}

/**
 * 部门目标搜索
 */
$("#search_dept_target_detail_btn").on("click",function(){
    //标题
    var title = $("#dept_target_detail_title").val();
    //所属部门
    var deptId = deptTargetDetailUserIdQuery.combobox("getValue");
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para= '{"title":"'+title+'","deptId":"'+deptId+'","companyTargetId":"'+selCompanyTargetId+'","companyTargetType":"'+targetType+'","companyTargetTimeSlot":"'+targetTimeSlot+'","companyDeptTargetYear":"'+deptTargetYear+'"}';
    queryParams={
        deptId:deptId,
        title: title,
        companyTargetId:selCompanyTargetId,
        targetType:companyTargetType,
        targetTimeSlot:companyTargetTimeSlot,
        deptTargetYear:companyDeptTargetYear
    }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_dept_target_detail.datagrid("load",para);
});

//填充表单数据
function fun_edit_dept_target_detail(id){
    form_dept_target_detail.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/deptTarget/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_dept_target_detail.form({
                onLoadSuccess : function(data){
                }
            });
            form_dept_target_detail.form("load",data);
        }
    });
}
/**
 * 查看个人成本
 */
function select_dept_target_detail_detail(id,type){
    addTargetAmountDetail.empty();
    var strType = "<td><span style='letter-spacing: 3em;'>类</span>型:</td>" +
        "<td>" +
        "<input class='easyui-combobox' readonly='readonly' readonly='readonly' name='deptTargetType' style='width: 170px;' id='deptTargetType' data-options=\"required:true," +
        "editable:false," +
        "valueField: 'label'," +
        "textField: 'value'," +
        "data: [{" +
        "label: 'type_economic_goals'," +
        "value: '经济目标'" +
        "},{" +
        "label: 'type_management_objectives'," +
        "value: '管理目标'" +
        "}]," +
        "onSelect : function(record){" +
        "loadDeptTargetAmount(record.label);" +
        "},"+
        "\" />" +
        "</td>";
    var strTypeObj = $(strType).appendTo(addTargetAmountDetail);
    $.parser.parse(strTypeObj);
    if(type == "type_economic_goals"){
        var strAmoType = "<td>金&nbsp;&nbsp;&nbsp;额:</td>" +
            "<td>" +
            "<input class='input easyui-numberbox' readonly='readonly' min='0.01' precision='2' max='1000000000'  type='text' name='deptTargetAmount' readonly='readonly' style='width: 170px;' data-options=\"required:true\" />" +
            "</td>";
        var strAmoTypeObj = $(strAmoType).appendTo(addTargetAmountDetail);
        $.parser.parse(strAmoTypeObj);
    }
    //填充表单数据
    fun_edit_dept_target_detail(id);
    //打开窗口
    win_dept_target_detail_detail.window({
        title: "查看部门目标",
        iconCls: 'icon-search',
        closed: false,
        draggable:true
    });
    //将窗口绝对居中
    win_dept_target_detail_detail.window("center");
}