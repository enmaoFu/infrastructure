//表格对象
var grid_target;
//表单
var form_target;
//获取窗口
var win_target;
//请求远程数据时发送的额外参数
var queryParams = {

};
var targetTypeData;
var comDept;
var ok_target;
var reset_target;
var close_target;
$(function(){
    //获取表格对象
    grid_target = $("#grid_target");
    //获取表单数据
    form_target= $("#form_target");
    //获取窗口
    win_target = $("#win_target");
    targetTypeData = $("#targetTypeData");
    comDept = $("#comDept");
    ok_target = $("#ok_target");
    reset_target = $("#reset_target");
    close_target = $("#close_target");
    /**
     * 初始化grid_target
     */
    grid_target.datagrid({
        url : PATH + '/target/loadTarget',
        title : '目标管理',
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
        toolbar : '#target_toolbar',
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.05},
            {field:'targetContent',title:'目标内容',width:$(window).width() * 0.15,
                formatter:function(value,row,index){

                    if(value.length > 20){
                        return value.substring(0,20)+"......";
                    }else {
                        return value;
                    }
            }},
            {field:'timeSlot',title:'目标时间段',width:$(window).width() * 0.05,
                formatter:function(value,row,index){

                    if(value == 'slot_year'){
                        return '年'
                    }else if(value == 'slot_half_year'){
                        return '半年';
                    }else if(value == 'slot_quarter'){
                        return '季度'
                    }else if(value == 'slot_month'){
                        return '月'
                    }else {
                        return '';
                    }
                }},
            {field:'targetType',title:'目标类型',width:$(window).width() * 0.05,
                formatter:function(value,row,index){

                    if(value == 'type_economic_goals'){
                        return '经济目标'
                    }else if(value == 'type_management_objectives'){
                        return '管理目标';
                    }else if(value == 'type_personal_goals'){
                        return '个人目标'
                    }else {
                        return '';
                    }
                }},
            {field:'userName',title:'创建人',width:$(window).width() * 0.1},
            {field:'createTime',title:'创建时间',width:$(window).width() * 0.1,sortable:true,formatter:function(value,row,index){
                return value.substring(0,value.length-8);
            }},
            {field:'deptName',title:'所属部门',width:$(window).width() * 0.1},
            {field:'coName',title:'所属公司',width:$(window).width() * 0.2},
            {field: 'id',
                title: '操作',
                align:'center',
                width:$(window).width() * 0.2,
                formatter:function(value,row,index){
                    var str="<a href='#' onclick='edit_target(\""+row.id+"\",\""+row.deptId+"\",\""+row.targetType+"\",\"select\")' style='text-decoration: none;'><font style='color: green'>查看</font></a>";
                    if(row.userId == userId){
                        var strEdit="<a href='#' onclick='edit_target(\""+row.id+"\",\""+row.deptId+"\",\""+row.targetType+"\",\"edit\")' style='text-decoration: none;'><font style='color: green'>编辑</font></a>";
                        str += "&nbsp;"+strEdit;
                    }
                    return str;
                }},
        ]]
    });

    /**
     * 查询所属企业改变事件
     */
    $("#companyIdQuery").combobox({
        valueField : 'id',
        textField : 'text',
        method : 'post',
        url : PATH +'/system/company/findByCid',
        onSelect : function(record){
            var value = record.id;
            var deptId = $("#deptIdQuery");
            deptId.combobox({
                valueField:'id',
                textField:'text',
                method : 'post',
                url : PATH + '/system/dept/findDept',
                onBeforeLoad: function(param){
                    param.companyId = value;

                }
            });
        }
    });

    /**
     * 新增公司目标按钮点击事件
     */
    $("#add_company_target").on("click",function(){
        //清空comDept
        comDept.find("td").remove();
        comDept.empty();
        //清空targetTypeData
        targetTypeData.find("td").remove();
        targetTypeData.empty();
        //设置请求路径
        form_target.attr("action",PATH + "/target/insert");
        var str = "<td id='companyLabel'>所属企业:</td>" +
        "<td id='compan'>" +
        "<input id='companyTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='companyId' data-options=\"required:true,valueField:'id',textField:'text'," +
            "editable:false," +
             "url : PATH +'/system/company/findByCid'"+
            "\" />" +
        "</td>";
        var targetObj = $(str).appendTo(comDept);
        $.parser.parse(targetObj);
        if(comDept.css("display") == 'none'){
            comDept.css("display","");
        }
        var targetSlot = "<td>目标时间段:</td>" +
            "<td>" +
            "<input class='easyui-combobox' style='width: 170px;' name='timeSlot' id='timeSlotId' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'slot_year'," +
            "value: '年'" +
            "},{" +
            "label: 'slot_half_year'," +
            "value: '半年'" +
            "},{" +
            "label: 'slot_quarter'," +
            "value: '季度'" +
            "},{" +
            "label: 'slot_month'," +
            "value: '月'" +
            "}]\"/>" +
            "</td>";
        var targetSlotObj = $(targetSlot).appendTo(targetTypeData);
        $.parser.parse(targetSlotObj);
        var strType = "<td>目标类型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' id='targetTypeId' style='width: 170px;' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_economic_goals'," +
            "value: '经济目标'" +
            "},{" +
            "label: 'type_management_objectives'," +
            "value: '管理目标'" +
            "}]\"/>" +
            "</td>";
            var strTypeObj = $(strType).appendTo(targetTypeData);
            $.parser.parse(strTypeObj);
        //打开窗口
        win_target.window({
            title: "新增公司目标",
            iconCls: 'icon-add',
            closed: false,
            draggable:true
        });
        //将窗口绝对居中
        win_target.window("center");
        form_target.form("reset");
        if(ok_target.css("display") == "none"){
            ok_target.css("display","");
        }
        if(reset_target.css("display") == "none"){
            reset_target.css("display","");
        }
        if(close_target.css("display") == "" || close_target.css("display") == "inline-block"){
            close_target.css("display","none");
        }
    });

    /**
     * 新增部门目标按钮点击事件
     */
    $("#add_dept_target").on("click",function(){
        //清空comDept
        comDept.find("td").remove();
        comDept.empty();
        //清空targetTypeData
        targetTypeData.find("td").remove();
        targetTypeData.empty();
        //设置请求路径
        form_target.attr("action",PATH + "/target/insert");
        var str = "<td id='companyLabel'>所属企业:</td>" +
            "<td id='compan'>" +
            "<input id='companyTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='companyId' data-options=\"required:true,valueField:'id',textField:'text'," +
            "editable:false," +
            "url : PATH +'/system/company/findByCid'," +
            "onSelect : function(record){" +
            "loadDeptId(this,record.id);" +
            "},"+
            "\" />" +
            "</td>";
        var strDept = "<td id='deptLabel'>所属部门:</td>" +
            "<td id='deptPan'>" +
            "<input id='deptTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='deptId' data-options=\"required:true,valueField:'id',textField:'text',editable:false\" />" +
            "</td>";
        if(comDept.css("display") == 'none'){
            comDept.css("display","");
        }
        var targetObj = $(str).appendTo(comDept);
        var targetDeptObj = $(strDept).appendTo(comDept);
        $.parser.parse(targetObj);
        $.parser.parse(targetDeptObj);
        var targetSlot = "<td>目标时间段:</td>" +
            "<td>" +
            "<input class='easyui-combobox' style='width: 170px;' name='timeSlot' id='timeSlotId' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'slot_year'," +
            "value: '年'" +
            "},{" +
            "label: 'slot_half_year'," +
            "value: '半年'" +
            "},{" +
            "label: 'slot_quarter'," +
            "value: '季度'" +
            "},{" +
            "label: 'slot_month'," +
            "value: '月'" +
            "}]\"/>" +
            "</td>";
        var targetSlotObj = $(targetSlot).appendTo(targetTypeData);
        $.parser.parse(targetSlotObj);
        var strType = "<td>目标类型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' style='width: 170px;' id='targetTypeId' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_economic_goals'," +
            "value: '经济目标'" +
            "},{" +
            "label: 'type_management_objectives'," +
            "value: '管理目标'" +
            "}]\"/>" +
            "</td>";
            var strTypeObj = $(strType).appendTo(targetTypeData);
            $.parser.parse(strTypeObj);
        //打开窗口
        win_target.window({
            title: "新增部门目标",
            iconCls: 'icon-add',
            closed: false,
            draggable:true
        });
        form_target.form("reset");
        //将窗口绝对居中
        win_target.window("center");
        if(ok_target.css("display") == "none"){
            ok_target.css("display","");
        }
        if(reset_target.css("display") == "none"){
            reset_target.css("display","");
        }
        if(close_target.css("display") == "" || close_target.css("display") == "inline-block"){
            close_target.css("display","none");
        }
    });

    /**
     * 新增个人目标按钮点击事件
     */
    $("#add_person_target").on("click",function(){
        //清空comDept
        comDept.find("td").remove();
        comDept.empty();
        //清空targetTypeData
        targetTypeData.find("td").remove();
        targetTypeData.empty();
        //设置请求路径
        form_target.attr("action",PATH + "/target/insert");
        var targetSlot = "<td>目标时间段:</td>" +
            "<td>" +
            "<input class='easyui-combobox' style='width: 170px;' name='timeSlot' id='timeSlotId' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'slot_year'," +
            "value: '年'" +
            "},{" +
            "label: 'slot_half_year'," +
            "value: '半年'" +
            "},{" +
            "label: 'slot_quarter'," +
            "value: '季度'" +
            "},{" +
            "label: 'slot_month'," +
            "value: '月'" +
            "}]\"/>" +
            "</td>";
        var targetSlotObj = $(targetSlot).appendTo(targetTypeData);
        $.parser.parse(targetSlotObj);
        var strType = "<td>目标类型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' id='targetTypeId' style='width: 170px;' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_personal_goals'," +
            "value: '个人目标'" +
            "}]\"/>" +
            "</td>";
            var strTypeObj = $(strType).appendTo(targetTypeData);
            $.parser.parse(strTypeObj);
        comDept.css("display","none");
        //打开窗口
        win_target.window({
            title: "新增个人目标",
            iconCls: 'icon-add',
            closed: false,
            draggable:true
        });
        form_target.form("reset");
        //将窗口绝对居中
        win_target.window("center");
        if(ok_target.css("display") == "none"){
            ok_target.css("display","");
        }
        if(reset_target.css("display") == "none"){
            reset_target.css("display","");
        }
        if(close_target.css("display") == "" || close_target.css("display") == "inline-block"){
            close_target.css("display","none");
        }
    });

    /**
     * 确认按钮点击事件
     */
    $("#ok_target").on("click",function(){
        //验证是否通过
        var ival = form_target.form("validate");
        if(ival){
            var action = form_target.attr("action");
            //验证通过 请求后台
            form_target.form('submit',{
                url : action,
                success : function(data){
                    var dataJson = $.parseJSON(data);
                    var flag = dataJson.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",dataJson.message,icon,function(){
                        if(dataJson.success){
                            form_target.form("clear");
                            win_target.window("close");
                            //属性表格
                            grid_target.datagrid('reload');
                        }
                    });
                }
            });
        }
    });


    /**
     * 重置按钮
     */
    $("#reset_target").on("click",function(){
        var title = win_target.panel("options").title;
        if(title == '新增'){
            form_target.form("reset");
        }else {
            //获取选中记录
            var selectRows = grid_target.datagrid("getSelections");
            //重置表单数据
            fun_edit_target(selectRows[0].id);
        }
    });

    //关闭窗口
    close_target.on("click",function(){
        win_target.window('close', true);
    });

    /**
     * 删除按钮点击事件
     */
    $("#remove_target").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = grid_target.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要删除的数据","warning");
            return;
        }

        for(var i=0;i<selectRows.length;i++){
            var row = selectRows[i];
            if(userId == row.userId){
                ids.push(row.id);
            }else{
                $.messager.alert("提示","选择项的数据中某项删除权限不够","warning");
                return;
            }
        }
        $.messager.confirm("提示","您确认要删除记录吗？",function(r){
            if(!r){
                return;
            }
            $.ajax({
                type : 'post',
                url : PATH + '/target/delete',
                data : {
                    ids : ids
                },
                success : function(data){
                    var icon = "error";
                    if(data.success){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(data.success){
                            //属性表格
                            grid_target.datagrid('reload');
                        }
                    });
                }
            });
        });
    });

    /**
     * 搜索按钮事件
     */
    $("#search_target_btn").on("click",fun_search);
});
/**
 * 查看或编辑目标计划
 * @param id
 * @param deptId
 * @param tarType
 */
function edit_target(id,deptId,tarType,type){
    //清空comDept
    comDept.find("td").remove();
    comDept.empty();
    //清空targetTypeData
    targetTypeData.find("td").remove();
    targetTypeData.empty();
    //设置保存请求路径
    form_target.attr("action",PATH + "/target/update");
    if(deptId != null && deptId != "" && deptId != "undefined" && tarType != "type_personal_goals"){
        var str = "<td id='companyLabel'>所属企业:</td>" +
            "<td id='compan'>" +
            "<input id='companyTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='companyId' data-options=\"required:true,valueField:'id',textField:'text'," +
            "editable:false," +
            "url : PATH +'/system/company/findByCid'," +
            "onSelect : function(record){" +
            "loadDeptId(this,record.id);" +
            "},"+
            "\" />" +
            "</td>";
        var strDept = "<td id='deptLabel'>所属部门:</td>" +
            "<td id='deptPan'>" +
            "<input id='deptTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='deptId' data-options=\"required:true,valueField:'id',textField:'text',editable:false\" />" +
            "</td>";
        if(comDept.css("display") == 'none'){
            comDept.css("display","");
        }
        var targetObj = $(str).appendTo(comDept);
        var targetDeptObj = $(strDept).appendTo(comDept);
        $.parser.parse(targetObj);
        $.parser.parse(targetDeptObj);

    }
    if(tarType != "type_personal_goals" && deptId == "undefined"){
        var str = "<td id='companyLabel'>所属企业:</td>" +
            "<td id='compan'>" +
            "<input id='companyTargetId' style='width: 170px;' class='easyui-combobox' maxlength='16' name='companyId' data-options=\"required:true,valueField:'id',textField:'text'," +
            "editable:false," +
            "url : PATH +'/system/company/findByCid'" +
            "\" />" +
            "</td>";
        if(comDept.css("display") == 'none'){
            comDept.css("display","");
        }
        var targetObj = $(str).appendTo(comDept);
        $.parser.parse(targetObj);
    }
    var targetSlot = "<td>目标时间段:</td>" +
        "<td>" +
        "<input class='easyui-combobox' style='width: 170px;' id='timeSlotId' name='timeSlot' data-options=\"required:true," +
        "editable:false," +
        "valueField: 'label'," +
        "textField: 'value'," +
        "data: [{" +
        "label: 'slot_year'," +
        "value: '年'" +
        "},{" +
        "label: 'slot_half_year'," +
        "value: '半年'" +
        "},{" +
        "label: 'slot_quarter'," +
        "value: '季度'" +
        "},{" +
        "label: 'slot_month'," +
        "value: '月'" +
        "}]\"/>" +
        "</td>";
    var targetSlotObj = $(targetSlot).appendTo(targetTypeData);
    $.parser.parse(targetSlotObj);
    if(tarType != "type_personal_goals"){
        var strType = "<td>目标类型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' id='targetTypeId' style='width: 170px;' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_economic_goals'," +
            "value: '经济目标'" +
            "},{" +
            "label: 'type_management_objectives'," +
            "value: '管理目标'" +
            "}]\"/>" +
            "</td>";
        var strTypeObj = $(strType).appendTo(targetTypeData);
        $.parser.parse(strTypeObj);
    }else{
        var strType = "<td>目标类型:</td>" +
            "<td id='encoTarget'>" +
            "<input class='easyui-combobox' name='targetType' id='targetTypeId' style='width: 170px;' data-options=\"required:true," +
            "editable:false," +
            "valueField: 'label'," +
            "textField: 'value'," +
            "data: [{" +
            "label: 'type_personal_goals'," +
            "value: '个人目标'" +
            "}]\"/>" +
            "</td>";
        var strTypeObj = $(strType).appendTo(targetTypeData);
        $.parser.parse(strTypeObj);
        comDept.css("display","none");
    }
    //填充表单数据
    if(type == "select"){
        //打开窗口
        win_target.window({
            iconCls: 'icon-search',
            closed: false,
            draggable:true
        });
        fun_edit_target(id,"select");
    }else if(type == "edit"){
        //打开窗口
        win_target.window({
            iconCls: 'icon-edit',
            closed: false,
            draggable:true
        });
        fun_edit_target(id);
    }
    //打开窗口
    win_target.window('open');
    if(deptId == null || deptId == "" || deptId == "undefined" && type == "edit"){
        win_target.window("setTitle","编辑公司目标");
    }
    if(deptId != null && deptId != "" && deptId != "undefined" && type == "edit"){
        win_target.window("setTitle","编辑部门目标");
    }
    if(tarType == "type_personal_goals" && type == "edit"){
        win_target.window("setTitle","编辑个人目标");
    }
    if(deptId == null || deptId == "" || deptId == "undefined" && type == "select"){
        win_target.window("setTitle","查看公司目标");
    }
    if(deptId != null && deptId != "" && deptId != "undefined" && type == "select"){
        win_target.window("setTitle","查看部门目标");
    }
    if(tarType == "type_personal_goals" && type == "select"){
        win_target.window("setTitle","查看个人目标");
    }
    //将窗口绝对居中
    win_target.window("center");
    if(ok_target.css("display") == "none"){
        ok_target.css("display","");
    }
    if(reset_target.css("display") == "none"){
        reset_target.css("display","");
    }
    if(close_target.css("display") == "" || close_target.css("display") == "inline-block"){
        close_target.css("display","none");
    }
}
//填充表单数据
function fun_edit_target(id,sel){
    form_target.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/target/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_target.form({
                onLoadSuccess : function(data){
                    if(data != null){
                            $("#deptTargetId").combobox({
                                valueField:'id',
                                textField:'text',
                                method : 'post',
                                url : PATH + '/system/dept/findDept',
                                onBeforeLoad: function(param){
                                    param.companyId = data.companyId;
                                }
                            });
                            //选中
                            $("#deptTargetId").combobox("select",data.deptId);
                        if(sel != null && sel != "" && sel != "undefined"){
                            $('#companyTargetId').combobox('readonly');
                            $('#deptTargetId').combobox('readonly');
                            $('#timeSlotId').combobox('readonly');
                            $('#targetTypeId').combobox('readonly');
                            $("#ok_target").css("display","none");
                            $("#reset_target").css("display","none");
                            $("#close_target").css("display","");
                        }else{
                            if($('#companyTargetId').attr("readonly") != null){
                                $('#companyTargetId').removeAttribute("readonly");
                            }
                            if($('#deptTargetId').attr("readonly") != null){
                                $('#deptTargetId').removeAttribute("readonly");
                            }
                            if($('#timeSlotId').attr("readonly") != null){
                                $('#timeSlotId').removeAttribute("readonly");
                            }
                            if($('#targetTypeId').attr("readonly") != null){
                                $('#targetTypeId').removeAttribute("readonly");
                            }
                            $("#ok_target").css("display","");
                            $("#reset_target").css("display","");
                            $("#close_target").css("display","none");
                        }
                    }
                }
            });
            form_target.form("load",data);
        }
    });
}
/**
 * 搜索
 */
function fun_search(){
    //所属企业
    var companyId = $("#companyIdQuery").combobox("getValue");
    //所属部门
    var deptId = $("#deptIdQuery").combobox("getValue");
    //目标时间段
    var timeSlot = $("#timeSlot").combobox("getValue");
    //目标类型
    var targetType = $("#targetType").combobox("getValue");
    //创建时间：
    var search_startsignDate = $("#search_target_startsignDate").datebox("getValue");
    var search_endsignDate = $("#search_target_endsignDate").datebox("getValue");
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para = '{"companyId":"'+companyId+'","deptId":"'+deptId+'","timeSlot":"'+timeSlot+'","targetType":"'+targetType+'"}';
    queryParams={
        stertsignDate: search_startsignDate,
        endsignDate: search_endsignDate
    }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_target.datagrid("load",para);
}

/**
 * 公司部门联动
 * @param obj
 */
function loadDeptId(obj,companyId){
    var lis=obj.parentNode.parentNode;
    var td = lis.cells[3].getElementsByTagName('input');
    $(td).combobox({
        required:true,
        valueField:'id',
        textField:'text',
        method : 'post',
        url : PATH + '/system/dept/findDept',
        onBeforeLoad: function(param){
            param.companyId = companyId;

        }
    });
}

function loadCombobox(userId){
    $("#userId").combotree({
        url: PATH + "/system/sysuser/findByParentId",
        type : 'post',
        loadFilter: function(rows){
            return convertUser(rows);
        },
        onSelect: function(rec){

        },
        onLoadSuccess: function (node, data) {
            if(userId != undefined){
                var node = $(this).tree('find',userId);
                $(this).tree('select',node.target);
                $('#userId').combotree('setValue', node.target);
            }
        }
    });

}

//加载当前登录用户信息目录
function convertUser(rows){
    function exists(rows, parentId){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentId) return true;
        }
        return false;
    }

    var nodes = [];
    // get the top level nodes
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentId)){
            nodes.push({
                id:row.id,
                text:row.username
            });
        }
    }

    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();	// the parent node
        // get the children nodes
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentId == node.id){
                var child = {id:row.id,text:row.username};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}