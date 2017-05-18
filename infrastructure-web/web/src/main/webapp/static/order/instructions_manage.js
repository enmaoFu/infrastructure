//表格对象
var grid_instructions;
//表单
var form_instructions;
var win_instructions;
//请求远程数据时发送的额外参数
var queryParams = {
    listState:"1"
};
var toolbar_launch_instruction;
var instructionsCompanyIdQuery;
var instructionsDeptQuery;
var toolbar_receive_instruction;
var instructions_toolbar;
var win_instructions_detail;
var form_instructions_detail;
var grid_inst_receive_obj;
var win_feedback;
var grid_feedback;
var form_feedback;
var listType = "1";
var win_instructions_forward;
var form_instructions_forward;
$(function(){
    //获取表格对象
    grid_instructions = $("#grid_instructions");
    //获取表单数据
    form_instructions= $("#form_instructions");
    //获取窗口
    win_instructions = $("#win_instructions");
    toolbar_receive_instruction = $("#toolbar_receive_instruction");
    toolbar_launch_instruction = $("#toolbar_launch_instruction");
    instructionsCompanyIdQuery = $("#instructionsCompanyIdQuery");
    instructionsDeptQuery = $("#instructionsDeptQuery");
    instructions_toolbar = $("#instructions_toolbar");
    win_instructions_detail = $("#win_instructions_detail");
    form_instructions_detail = $("#form_instructions_detail");
    grid_inst_receive_obj = $("#grid_inst_receive_obj");
    win_feedback = $("#win_feedback");
    grid_feedback = $("#grid_feedback");
    form_feedback = $("#form_feedback");
    win_instructions_forward = $("#win_instructions_forward");
    form_instructions_forward = $("#form_instructions_forward");

    //加载发出指令
    toolbar_receive_instruction.hide();
    toolbar_launch_instruction.show();
    loadInstructions();
    grid_instructions.datagrid('showColumn','usName');
    grid_instructions.datagrid('hideColumn','sendName');
    grid_instructions.datagrid('hideColumn','feedName');
    grid_instructions.datagrid('hideColumn','feedCreateTime');
    /**
     * 发出指令
     */
    $("#launch_instruction").on("click",function(){
        grid_instructions.datagrid('unselectAll');
        toolbar_receive_instruction.hide();
        toolbar_launch_instruction.show();
        instructions_toolbar.children().eq(0).find("a").linkbutton("unselect");
        $(this).linkbutton("select");
        queryParams = {
            listState:"1"
        };
        loadInstructions();
        grid_instructions.datagrid('hideColumn','sendName');
        grid_instructions.datagrid('hideColumn','feedName');
        grid_instructions.datagrid('hideColumn','feedCreateTime');
        grid_instructions.datagrid('showColumn','usName');
        grid_instructions.datagrid('showColumn','createtime');
        listType = "1";
    });

    /**
     * 收到指令
     */
    $("#receive_instruction").on("click",function(){
        grid_instructions.datagrid('unselectAll');
        toolbar_receive_instruction.show();
        toolbar_launch_instruction.hide();
        instructions_toolbar.children().eq(0).find("a").linkbutton("unselect");
        $(this).linkbutton("select");
        queryParams = {
            listState:"2"
        };
        loadInstructions();
        grid_instructions.datagrid('showColumn','sendName');
        grid_instructions.datagrid('hideColumn','feedName');
        grid_instructions.datagrid('hideColumn','feedCreateTime');
        grid_instructions.datagrid('hideColumn','usName');
        grid_instructions.datagrid('showColumn','createtime');
        listType = "2";
    });

    /**
     * 转发指令
     */
    $("#forwarding_instruction").on("click",function(){
        grid_instructions.datagrid('unselectAll');
        toolbar_receive_instruction.hide();
        toolbar_launch_instruction.hide();
        instructions_toolbar.children().eq(0).find("a").linkbutton("unselect");
        $(this).linkbutton("select");
        queryParams = {
            listState:"3"
        };
        loadInstructions();
        grid_instructions.datagrid('hideColumn','usName');
        grid_instructions.datagrid('showColumn','feedName');
        grid_instructions.datagrid('showColumn','feedCreateTime');
        grid_instructions.datagrid('showColumn','sendName');
        grid_instructions.datagrid('hideColumn','createtime');
        listType = "3";
    });

});
/**
 * 新增按钮点击事件
 */
$("#add_instructions").on("click",function(){
    //设置请求路径
    form_instructions.attr("action",PATH + "/instructions/insert");
    //打开窗口
    win_instructions.window({
        title: "新增指令",
        iconCls: 'icon-add',
        closed: false,
        draggable:true,
        height:500
    });
    form_instructions.form("reset");
    getTreeNodes(instCompanyId,"", "","received","");
    //将窗口绝对居中
    win_instructions.window("center");
});
$("#forward_instructions").on("click",function(){
    //设置请求路径
    form_instructions_forward.attr("action",PATH + "/instructions/insertForward");
    //获取所有选中记录
    var selectRows = grid_instructions.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要转发的数据","warning");
        return;
    }
    if(selectRows.length > 1){
        $.messager.alert("提示","只能选择一条数据","warning");
        return;
    }
    var row = selectRows[0];
    if(row.state == "Y"){
        $.messager.alert("提示","该项指令已完成","warning");
        return;
    }else {
        //ID
        var id = row.id;
        $("#fid").val(id);
        $("#parentIdss").val(row.objUserId);
        $("#objId").val(row.objId);
        //打开窗口
        win_instructions_forward.window({
            title: "转发",
            iconCls: 'icon-redo',
            closed: false,
            draggable:true,
            height:410
        });
        form_instructions_forward.form("reset");
        getTreeNodes(instCompanyId,"", "forward","")
        //将窗口绝对居中
        win_instructions_forward.window("center");
    }
});
/**
 * 确认按钮点击事件
 */
$("#ok_instructions_forward").on("click",function(){
    var $this = $(this);
    form_instructions_forward = $(this).parent().parent().parent();
    //获取接收者的用户
    var selectForwardOtherUsers = chooseForwardOtherTree.getNodes();
    var forwardOtherUserIds = [];
    for(var i=0;i<selectForwardOtherUsers.length;i++){
        forwardOtherUserIds.push(selectForwardOtherUsers[i].id);
    }
    if(forwardOtherUserIds==null || forwardOtherUserIds.length<=0){
        $.messager.alert("提示","请选择转发的对象","warning");
        return;
    }
    //验证是否通过
    var ival = form_instructions_forward.form("validate");
    if(ival){
        $this.linkbutton('disable');
        $this.linkbutton({
            iconCls: 'icon-loding'
        });
        var action = form_instructions_forward.attr("action");
        //验证通过 请求后台
        form_instructions_forward.form('submit',{
            url : action,
            queryParams : {
                userIds:forwardOtherUserIds.join(",")
            },
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
                        form_instructions_forward.form("clear");
                        win_instructions_forward.window("close");
                        //清除所有选择的行
                        grid_instructions.datagrid('clearSelections');
                        //属性表格
                        grid_instructions.datagrid('reload');
                    }
                });
            }
        });
    }
});
/**
 * 确认按钮点击事件
 */
$("#ok_instructions").on("click",function(){
    var $this = $(this);
    form_instructions = $(this).parent().parent().parent();
    //获取接收者的用户
    var selectForwardOtherUsers = chooseReceivedTree.getNodes();
    var forwardOtherUserIds = [];
    for(var i=0;i<selectForwardOtherUsers.length;i++){
        forwardOtherUserIds.push(selectForwardOtherUsers[i].id);
    }
    if(forwardOtherUserIds==null || forwardOtherUserIds.length<=0){
        $.messager.alert("提示","请选择接收的对象","warning");
        return;
    }
    //验证是否通过
    var ival = form_instructions.form("validate");
    if(ival){
        $this.linkbutton('disable');
        $this.linkbutton({
            iconCls: 'icon-loding'
        });
        var action = form_instructions.attr("action");
        //验证通过 请求后台
        form_instructions.form('submit',{
            url : action,
            queryParams : {
                userIds:forwardOtherUserIds.join(","),
                fileName:$("#file").filebox("getText")
            },
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
                        form_instructions.form("clear");
                        win_instructions.window("close");
                        //清除所有选择的行
                        grid_instructions.datagrid('clearSelections');
                        //属性表格
                        grid_instructions.datagrid('reload');
                    }
                });
            }
        });
    }
});
/**
 * 回馈确认按钮点击事件
 */
$("#ok_feedback").on("click",function(){
    var $this = $(this);
    form_feedback = $(this).parent().parent().parent();
    //验证是否通过
    var ival = form_feedback.form("validate");
    if(ival){
        $this.linkbutton('disable');
        $this.linkbutton({
            iconCls: 'icon-loding'
        });
        var action = form_feedback.attr("action");
        //验证通过 请求后台
        $("#form_feedback").form('submit',{
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
                        form_feedback.form("clear");
                        //清除所有选择的行
                        grid_feedback.datagrid('clearSelections');
                        //属性表格
                        grid_feedback.datagrid('reload');
                    }
                });
            }
        });
    }
});
/**
 *编辑按钮点击事件
 */
$("#edit_instructions").on("click",function(){
    //设置保存请求路径
    form_instructions.attr("action",PATH + "/instructions/update");
    //获取所有选中记录
    var selectRows = grid_instructions.datagrid("getSelections");
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
    //填充表单数据
    fun_edit_instructions(id,"edit");
    //打开窗口
    win_instructions.window({
        title: "编辑指令",
        iconCls: 'icon-edit',
        closed: false,
        draggable:true,
        height:500
    });
    //将窗口绝对居中
    win_instructions.window("center");
});
//填充表单数据
function fun_edit_instructions(id,edit){
    form_instructions.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/instructions/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_instructions.form({
                onLoadSuccess : function(data){
                    if(data != null){
                        if(edit == 'edit'){
                            getTreeNodes("","","",data.usId,"");
                            $("#file").filebox("setText",data.sysFile.originalFileName);
                        }
                    }
                }
            });
            form_instructions.form("load",data);
        }
    });
}
//填充表单数据
function fun_search_instructions(id){
    form_instructions_detail.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/instructions/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_instructions_detail.form({
                onLoadSuccess : function(data){
                    if(data != null){
                    }
                }
            });
            form_instructions_detail.form("load",data);
        }
    });
}
/**
 * 删除按钮点击事件
 */
$("#remove_instructions").on("click",function(){
    var ids = [];
    //获取选中记录
    var selectRows = grid_instructions.datagrid("getSelections");
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
            url : PATH + '/instructions/delete',
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
                        grid_instructions.datagrid('clearSelections');
                        //属性表格
                        grid_instructions.datagrid('reload');
                    }
                });
            }
        });
    });
});
/**
 * 查看指令详情
 */
function select_instructions_detail(id){
    //填充表单数据
    fun_search_instructions(id);
    loadInstReceiveObjDetail(id);
    //打开窗口
    win_instructions_detail.window({
        title: "查看指令",
        iconCls: 'icon-search',
        closed: false,
        draggable:true,
        height:410
    });
    //将窗口绝对居中
    win_instructions_detail.window("center");
}
/**
 * 回馈
 */
function feedback_instructions(id){
    form_feedback.attr("action",PATH + "/instructions/insertInstFeedback");
    //填充表单数据
    loadInstFeedback(id);
    $("#feedbackInstId").val(id);
    //打开窗口
    win_feedback.window({
        title: "回馈",
        iconCls: 'icon-redo',
        closed: false,
        draggable:true,
        height:410
    });
    form_feedback.form("reset");
    //将窗口绝对居中
    win_feedback.window("center");
}
/**
 * 确认完成
 */
$("#confirm_instructions").on("click",function(){
    var ids = [];
    //获取选中记录
    var selectRows = grid_instructions.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要确认完成的数据","warning");
        return;
    }
    for(var i=0;i<selectRows.length;i++){
        var row = selectRows[i];
        ids.push(row.id);
    }
    $.messager.confirm("提示",",您确认要通过记录吗？",function(r){
        if(!r){
            return;
        }
        $.ajax({
            type : 'post',
            url : PATH + '/instructions/confirm',
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
                        grid_instructions.datagrid('clearSelections');
                        //属性表格
                        grid_instructions.datagrid('reload');
                    }
                });
            }
        });
    });
});
/**
 * 指令列表展示
 * @param id
 */
function loadInstructions(){
    /**
     * grid_instructions
     */
    grid_instructions.datagrid({
        url : PATH + '/instructions/loadInstructions',
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
        queryParams:queryParams,
        toolbar : instructions_toolbar,
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.1},
            {field:'objUserId',title:'接收者',width:$(window).width() * 0.01,hidden:true},
            {field:'objId',title:'接收者编号',width:$(window).width() * 0.01,hidden:true},
            {field:'content',title:'内容',width:$(window).width() * 0.15},
            {field:'usName',title:'接收对象',width:$(window).width() * 0.1},
            {field:'sendName',title:'发出者',width:$(window).width() * 0.1},
            {field:'feedName',title:'转发对象',width:$(window).width() * 0.1},
            {field:'originalFileName', title:'附件',width:$(window).width()*0.1,'formatter':function(value,row,index){
                if(typeof row.sysFile == 'undefined') return;
                value = row.sysFile.originalFileName;
                if(typeof value == 'undefined') return;
                return "<a href='"+PATH+"/instructions/download/"+row.id+"'>"+value+"</a>";
            }},
            {field:'feedCreateTime',title:'转发时间',width:$(window).width() * 0.1,sortable:true,formatter:function(value,row,index){
                if(value == "" || value == null || value == 'undefined'){
                    return "";
                }else {
                    return value.substring(0,value.length-8);
                }
            }},
            {field:'createtime',title:'发出时间',width:$(window).width() * 0.1,sortable:true,formatter:function(value,row,index){
                if(value == "" || value == null || value == 'undefined'){
                    return "";
                }else {
                    return value.substring(0,value.length-8);
                }
            }},
            {field:'state',title:'完成状态',width:$(window).width() * 0.05,
                formatter:function(value,row,index){
                    if(value == "Y"){
                        return "已完成";
                    }else if(value == "N"){
                        return "<span style='color: red;'>进展中</span>";
                    }else {
                        return "";
                    }
                }},
            {field: 'id',
                title: '操作',
                width:$(window).width() * 0.1,
                align:'center',
                formatter:function(value,row,index){
                    var str="<a href='javascript:void(0)' onclick='select_instructions_detail(\""+value+"\")' style='text-decoration: none;'><font style='color: green'>查看</font></a>";
                    if(row.state == "N"){
                        var report = "<a href='javascript:void(0)' onclick='feedback_instructions(\""+value+"\")' style='text-decoration: none;'><font style='color: green'>回馈</font></a>";
                        str += "&nbsp;"+report;
                    }
                    return str;
                }}
        ]],
        onDblClickRow: function(index, row){
            select_instructions_detail(row.id);
        }
    });
}

/**
 * 查看详情接收对象
 * @param id
 */
function loadInstReceiveObjDetail(id){
    /**
     * grid_instructions
     */
    grid_inst_receive_obj.datagrid({
        url : PATH + '/instructions/loadInstReceiveObj',
        queryParams:{
            instId:id
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
        pageSize : pageSize,
        pageList : pageList,
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.1},
            {field:'userName',title:'接收者',width:$(window).width() * 0.5,sortable:true},
            {field:'forward',title:'转发状态',width:$(window).width() * 0.4,
                formatter:function(value,row,index){
                    if(value == "Y"){
                        return "已转发";
                    }else if(value == "N"){
                        return "<span style='color: red;'>执行人</span>";
                    }else {
                        return "";
                    }
                }}
        ]]
    });
    /**
     * 关闭按钮监听
     */
    win_instructions_detail.window({
        onBeforeClose:function(){
            //清除所有选择的行
            grid_inst_receive_obj.datagrid('clearSelections');
        }
    });
}

/**
 * 回馈列表
 * @param id
 */
function loadInstFeedback(id){
    /**
     * grid_feedback
     */
    grid_feedback.datagrid({
        url : PATH + '/instructions/loadInstFeedback',
        queryParams:{
            instId:id
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
        pageSize : pageSize,
        pageList : pageList,
        toolbar : $("#feedback_toolbar"),
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.1},
            {field:'fcontent',title:'内容',width:$(window).width() * 0.5,sortable:true},
            {field:'userName',title:'回馈对象',width:$(window).width() * 0.2},
            {field:'createtime',title:'回馈时间',width:$(window).width() * 0.2,sortable:true,formatter:function(value,row,index){
                if(value == "" || value == null || value == 'undefined'){
                    return "";
                }else {
                    return value.substring(0,value.length-8);
                }
            }}
        ]],
        onDblClickRow: function(index, row){
            $("#fcontent").textbox("setValue",row.fcontent);
        }
    });
    /**
     * 关闭按钮监听
     */
    win_feedback.window({
        onBeforeClose:function(){
            //清除所有选择的行
            grid_feedback.datagrid('clearSelections');
        }
    });
}
/**
 * 搜索
 */
$("#search_instruction_btn").on("click", function () {

    //项目名称
    var content = $("#search_instruction_content").val();
    //创建时间：
    var search_startsignDate = $("#search_instruction_startsignDate").datebox("getValue");
    var search_endsignDate = $("#search_instruction_endsignDate").datebox("getValue");
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para = '{"content":"'+content+'","listState":"'+listType+'","stertsignDate":"'+search_startsignDate+'","endsignDate":"'+search_endsignDate+'"}';
        queryParams={
            content : content,
            listState:listType,
            stertsignDate: search_startsignDate,
            endsignDate: search_endsignDate
        }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_instructions.datagrid("load",para);
});