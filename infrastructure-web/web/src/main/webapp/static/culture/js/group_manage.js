//获取表格
var grid_group;
var $grid_verification;
//获取窗口
var win_group;
var win_message;
var $win_verification;
var $win_apply;
//获取表单
var form_group;
var $form_apply;
//按钮
var $company_group;
var $my_group;
var $verification;
var $tb_group;

var ul_all_tree = $("#all_tree");
var ul_choose_tree = $("#choose_tree");
var $user_zTree = $("#user_zTree");
var send_value = $('#send_value');
var textarea = $("#text");
var param = {
    state : 'company'
};
var timeBox;
//公司群租按钮是否选中
var btn_com_selected = true;
$(function(){
    grid_group = $("#grid_group");
    $grid_verification = $("#grid_verification");
    win_group = $("#win_group");
    win_message = $("#win_message");
    $win_verification = $("#win_verification");
    $win_apply = $("#win_apply");
    form_group = $("#form_group");
    $form_apply = $("#form_apply");
    $company_group = $("#company_group");
    $my_group = $("#my_group");
    $verification = $("#verification");
    $tb_group = $("#tb_group");
    /**
     * 初始化grid_group
     */
    grid_group.datagrid({
        url : PATH + '/culture/group/load',
        queryParams: param,
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        singleSelect : true,
        sortName : 'createDate',
        sortOrder : 'desc',
        toolbar : '#tb_group',
        pageSize : pageSize,
        pageList : pageList,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'name',title:'群组名称',width:$(window).width()*0.2},
            {field:'userName',title:'创建人',width:$(window).width()*0.1},
            {field:'companyName',title:'所属公司',width:$(window).width()*0.2},
            {field:'createDate',title:'创建时间',sortable:true,width:$(window).width()*0.1},
            {field:'remark',title:'备注',width:$(window).width()*0.2},
            {field:'no',title:'操作','formatter':function(value,row,index){
                var html = "<a href='javascript:message()'>会话</a>";
                if(btn_com_selected){
                    var userList = row.userList;
                    var flag = true;
                    $(userList).each(function(i,o){
                        if(o.id == uId){
                            flag = false;
                            return false;
                        }
                    });
                    if(flag){
                        html = "<a href='javascript:apply(0);'>申请加入</a>";
                    }
                }
                return html;
            },width:$(window).width()*0.1}
        ]],
        onClickRow : function(rowIndex, rowData){
            if(rowData.createUser != uId){
                $("#edit_group").hide();
            }else {
                $("#edit_group").show();
            }
        },
        onDblClickRow : function(rowIndex, rowData){
            var userList = rowData.userList;
            var flag = true;
            $(userList).each(function(i,o){
                if(o.id == uId){
                    flag = false;
                    return false;
                }
            });
            if(!flag){
                message();
            }
        }
    });
    //查询是否有待处理的验证消息
    pending();

    /**
     * 公司群组
     */
    $company_group.on("click",function(){
        $tb_group.children().eq(3).hide();
        $tb_group.find("a").linkbutton("unselect");
        $(this).linkbutton("select");
        btn_com_selected = true;
        grid_group.datagrid("load",param);
        grid_group.datagrid("unselectAll");
    });

    /**
     * 我的群组
     */
    $my_group.on("click",function(){
        $tb_group.children().eq(3).show();
        $tb_group.find("a").linkbutton("unselect");
        $(this).linkbutton("select");
        btn_com_selected = false;
        grid_group.datagrid("load",{
            state : 'my'
        });
        grid_group.datagrid("unselectAll");
    });

    /**
     * 验证消息
     */
    $verification.on("click",function(){
        $win_verification.window("open");
        $win_verification.window("setTitle","验证消息");
        $win_verification.window("center");
        $grid_verification.datagrid({
            url : PATH + '/group/verification/load',
            idField:'id',
            pagination : true,
            height : '100%',
            border : false,
            fitColumns : true,
            autoRowHeight : false,
            rownumbers : true,
            ctrlSelect : true,
            toolbar : '#tb_verification',
            sortName : 'createTime',
            sortOrder : 'desc',
            columns : [[
                {field:'ck',checkbox:true},
                {field:'id',title:'ID',hidden:true},
                {field:'apply',title:'申请信息',width:$(window).width()*0.3,'formatter':function(value,row,index){
                    var html = "<span style='font-weight:bold;'>"+row.userName+"</span>申请加入";
                    html += "<span style='font-weight:bold;'>"+row.groupName+"</span>";
                    return html;
                }},
                {field:'information',title:'验证信息',width:$(window).width()*0.2},
                {field:'createTime',title:'申请时间',width:$(window).width()*0.15},
                {field:'isPass',title:'处理状态',width:$(window).width()*0.1,'formatter':function(value,row,index){
                    var html = "<span style='color: green;'>已通过</span>";
                    if(value == 'P'){
                        html = "<span style='color: red;'>待处理</span>";
                    }else if(value == 'N'){
                        html = "<span style='color: #aaaaaa;'>已拒绝</span>";
                    }
                    return html;
                }},
                {field:'cz',title:'操作',width:$(window).width()*0.1,'formatter':function(value,row,index){
                    if(row.isPass != 'P'){
                        return;
                    }
                    var id = row.id;
                    var html = "<a href='javascript:operating(\""+id+"\",\"Y\");'><img src='"+PATH+"/static/easyui/css/icons/ok.png'></a>";
                        html += "&nbsp;&nbsp;&nbsp;";
                        html += "<a href='javascript:operating(\""+id+"\",\"N\");'><img src='"+PATH+"/static/easyui/css/icons/cancel.png'></a>";
                    return html;
                }}
            ]]
        });
    });

    /**
     * 创建讨论组
     */
    $("#add_group").on("click",function(){
        //设置提交地址
        form_group.attr("action",PATH + "/culture/group/insert");
        form_group.form("reset");
        win_group.window("open");
        win_group.window("setTitle","创建讨论组");
        win_group.window("center");
        var all_zNodes = [];
        var choose_zNodes = [];
        var node = "{'id':'"+uId+"','name':'"+userName+"'}";
        choose_zNodes.push(eval("("+node+")"));
        $.ajax({
            type : 'post',
            url : PATH + '/system/dept/findUserByCid',
            success : function(msg){
                var json = $.parseJSON(msg);
                $.each(json,function(i,o){
                    node = "{'id':'"+o.id+"','name':'"+o.name+"'}";
                    all_zNodes.push(eval("("+node+")"));
                    var userSet = o.userSet;
                    $.each(userSet,function(index,obj){
                        obj = $(obj)[0];
                        node = "{'id':'"+obj.id+"','name':'"+obj.username+"','pId':'"+o.id+"'}";
                        all_zNodes.push(eval("("+node+")"));
                    });
                });
                to_choose(all_zNodes,choose_zNodes);
            }
        });
    });

    /**
     * 编辑
     */
    $("#edit_group").on("click",function(){
        //获取所有选中的行
        var selectRows = grid_group.datagrid("getSelections");
        if(selectRows.length == 0){
            //$.messager.alert("提示","请选择需要编辑的数据","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据进行编辑","warning");
            return;
        }
        //设置提交地址
        form_group.attr("action",PATH + "/culture/group/update");
        form_group.form("reset");
        form_group.form("load",selectRows[0]);
        win_group.window("open");
        win_group.window("setTitle","编辑讨论组");
        //将窗口绝对居中
        win_group.window("center");
        var all_zNodes = [];
        var choose_zNodes = [];
        $.ajax({
            type : 'post',
            url : PATH + '/system/dept/findUserByCid',
            success : function(msg){
                var json = $.parseJSON(msg);
                $.each(json,function(i,o){
                    node = "{'id':'"+o.id+"','name':'"+o.name+"'}";
                    all_zNodes.push(eval("("+node+")"));
                    var userSet = o.userSet;
                    $.each(userSet,function(index,obj){
                        obj = $(obj)[0];
                        node = "{'id':'"+obj.id+"','name':'"+obj.username+"','pId':'"+o.id+"'}";
                        all_zNodes.push(eval("("+node+")"));
                    });
                });
                $.ajax({
                    type : 'post',
                    url : PATH + '/culture/group/findGroupUser',
                    data : {
                        'groupId' : selectRows[0].id
                    },
                    success : function(result){
                        json = $.parseJSON(result);
                        $.each(json,function(i,o){
                            var node = "{'id':'"+ o.userId+"','name':'"+ o.userName+"'}";
                            choose_zNodes.push(eval("("+node+")"));
                        });
                        to_choose(all_zNodes,choose_zNodes);
                    }
                });
            }
        });
    });

    /**
     * 删除
     */
    $("#remove_group").on("click",function(){
        //获取所有选中的行
        var selectRows = grid_group.datagrid("getSelections");
        if(selectRows.length == 0){
            return;
        }
        var ids = [];
        $(selectRows).each(function(index,row){
            ids.push(row.id);
        });
        $.messager.confirm("提示","确认退出群组？",function(r){
            if(!r){
                return;
            }
            $.ajax({
                type : 'post',
                url : PATH + '/culture/group/delete',
                data : {
                    ids : ids.join(",")
                },
                success : function(data){
                    data = $.parseJSON(data);
                    var icon = "error";
                    if(data.success){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(data.success){
                            //刷新表格
                            grid_group.datagrid('reload');
                            grid_group.datagrid("clearSelections");
                        }
                    });
                }
            });
        });
    });

    /**
     *确认
     */
$('#ok_group').linkbutton({
            onClick:function() {
                var $this = $(this);
                //验证是否通过
                var ival = form_group.form("validate");
                if (ival) {
                    $this.linkbutton('disable');
                    $this.linkbutton({
                        iconCls : 'icon-loding'
                    });

                    var treeObj = $.fn.zTree.getZTreeObj("choose_tree");
                    var nodes = treeObj.getNodes();
                    //成员ID
                    var treeIds = [];
                    +function (nodes, ids) {
                        for (var i = 0, len = nodes.length; i < len; i++) {
                            var node = nodes[i];
                            if (node.children) {
                                arguments.callee(node.children, ids);
                            } else {
                                ids.push(node.id);
                            }
                        }
                    }(nodes, treeIds);
                    $("#userIds").val(treeIds.join(","));
                    var action = form_group.attr("action");
                    //验证通过 请求后台
                    form_group.form('submit', {
                        url: action,
                        success: function (data) {
                            data = data.substr(0, data.lastIndexOf("}") + 1);
                            data = $.parseJSON(data);
                            var flag = data.success;
                            var icon = "error";
                            if (flag) {
                                icon = "info";
                            }
                            $this.linkbutton('enable');
                            $this.linkbutton({
                                iconCls : 'icon-ok'
                            });
                            $.messager.alert("提示", data.message, icon, function () {
                                if (flag) {
                                    form_group.form("clear");
                                    win_group.window("close");
                                    grid_group.datagrid('reload');
                                }
                            });
                        }
                    });
                }
            }
    });

    /**
     * 取消
     */
    $("#reset_group").on("click",function(){
        win_group.window("close");
    });

});

function to_choose(all_zNodes,choose_zNodes){
    var all_tree;
    var choose_tree;
    var all_setting = {
        treeId : "all_zTree",
        edit : {
            showRemoveBtn : false,
            showRenameBtn : false,
            drag : {
                isCopy : false,
                isMove : false,
                prev : false,
                next : false,
                inner : false
            }
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        view : {
            showLine : false
        },
        callback:{
            onClick: function(event, treeId, treeNode){
                if(treeNode == null) return;
                var childs = treeNode.children;
                if(childs){
                    $(childs).each(function(i,o){
                        var node = choose_tree.getNodeByParam("id",o.id,null);
                        if(node != null){
                            return;
                        }
                        choose_tree.addNodes(null, o);
                    });
                }else {
                    var node = choose_tree.getNodeByParam("id",treeNode.id,null);
                    if(node != null){
                        return;
                    }
                    choose_tree.addNodes(null, treeNode);
                }
            }
        }
    };
    var choose_setting = {
        treeId : "choose_zTree",
        edit: {
            enable: true,
            showRemoveBtn: true,
            showRenameBtn: false,
            drag: {
                isCopy: false,
                isMove: false,
                prev: true,
                next: true,
                inner: true
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeRemove: function(treeId, treeNode){
                if(treeNode.id == uId){
                    return false;
                }
            }
        }
    };
    all_tree = $.fn.zTree.init(ul_all_tree,all_setting,all_zNodes);
    choose_tree = $.fn.zTree.init(ul_choose_tree, choose_setting,choose_zNodes);
}

/**
 * 会话
 */
function message(){
    textarea.textbox("clear");
    var selectRow = $("#grid_group").datagrid("getSelected");
    win_message.window("open");
    win_message.window("setTitle",selectRow.name);
    //将窗口绝对居中
    win_message.window("center");
    $("#groupId").val(selectRow.id);
    $.ajax({
        type : 'post',
        url : PATH + '/culture/group/findGroupIdByUser',
        data : {
            groupId : selectRow.id
        },
        success : function(msg){
            var json = $.parseJSON(msg);
            if(json.success){
                $.messager.alert("提示",json.message,'info');
                return;
            }
            var jsonDate = $(json.group);
            var list = $(json.list);
            textarea.textbox("setValue","");
            var textVal = "";
            list.each(function(i,o){
                var name = o.userName;
                var time = o.sendDate;
                time = time.substr(0,time.length-4);
                var message = o.content;
                while (message.indexOf("/n/") != -1){
                    message = message.replace("/n/","\n  ");
                }
                textVal += name+" "+ time +"\r\n  "+ message;
                if(list.length-1 != i){
                    textVal += "\r\n";
                }
            });
            textarea.textbox("setText",textVal);
            var treeNodes = [];
            jsonDate.each(function(i,o){
                var node = "{'id':'"+o.id+"','name':'"+o.username+"'}";
                treeNodes.push(eval("("+node+")"));
            });
            var setting = {
                treeId : "user_zTree",
                edit: {
                    enable: false,
                    showRemoveBtn: false,
                    showRenameBtn: false,
                    drag: {
                        isCopy: false,
                        isMove: false,
                        prev: true,
                        next: true,
                        inner: true
                    }
                },
                view: {
                    showLine: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    beforeRemove: function(treeId, treeNode){
                        if(treeNode.id == uId){
                            return false;
                        }
                    }
                }
            };
            $.fn.zTree.init($user_zTree,setting,treeNodes);
            //打开websocket
            open_socket();
        }
    });
    //获取焦点
    send_value.textbox('textbox').focus();
    scroll();
    $("textarea[readonly]").on('scroll',function(){
        clearInterval(timeBox);
    });
}

/**
 * 保持滚动条在最下方
 */
function scroll(){
    timeBox = setInterval(function(){
        var scrollTop = $("textarea[readonly]")[0].scrollHeight;
        $("textarea[readonly]").scrollTop(scrollTop);
    },500);
}

var websocket = null;
function open_socket(){
    if (window['WebSocket']) {
        websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket');
    } else {
        websocket = new new SockJS(PATH + '/websocket/socketjs');
    }
    var getText;
    websocket.onopen = function(event) {

    };
    websocket.onmessage = function(event) {
        var json = eval("("+event.data+")");
        getText = textarea.textbox("getText");
        var name = json.name;
        var time = json.time;
        var message = json.message;
        while (message.indexOf("/n/") != -1){
            message = message.replace("/n/","\n  ");
        }
        if(getText != ''){
            getText = getText+"\r\n";
        }
        var msg = name+" "+ time +"\r\n  "+ message;
        textarea.textbox("setText",getText + msg);
    };
}

//向服务器发送数据
function SendData() {
    clearInterval(timeBox);
    //群组ID
    var groupId = $("#groupId").val();
    //待发送的消息
    var value = send_value.textbox("getValue");
    if(value=='' || value == null){
        return;
    };
    $.ajax({
        type : 'post',
        url : PATH + '/message/send',
        data : {
            'groupId' : groupId,
            'userId' : uId,
            'message' : value
        }
    });
    send_value.textbox("setValue","");
    send_value.textbox('textbox').focus();
    scroll();
};

/**
 * 申请加入
 */
function apply(){
    var row = grid_group.datagrid("getSelected");
    var groupId = row.id;
    $.ajax({
        type : 'post',
        url : PATH + '/group/verification/select',
        data : {
            groupId : groupId
        },
        success : function(data){
            data = $.parseJSON(data);
            if(!data.success){
                $form_apply.attr("action",PATH + "/group/verification/insert");
                $form_apply.form("reset");
                $win_apply.dialog("open");
                $win_apply.dialog("setTitle","添加群");
                $win_apply.dialog("center");
            }else {
                $.messager.alert("提示",'已申请,无需重复申请','info');
            }
        }
    });
}

$('#ok_apply').linkbutton({
        onClick:function() {
            var $this = $(this);
            var row = grid_group.datagrid("getSelected");
            var groupId = row.id;
            var action = $form_apply.attr("action");
            $this.linkbutton('disable');
            $this.linkbutton({
                iconCls : 'icon-loding'
            });
            $form_apply.form('submit', {
                url: action,
                queryParams: {
                    groupId: groupId
                },
                success: function (data) {
                    data = data.substr(0, data.lastIndexOf("}") + 1);
                    data = $.parseJSON(data);
                    var flag = data.success;
                    var icon = "error";
                    if (flag) {
                        icon = "info";
                    }
                    $this.linkbutton('enable');
                    $this.linkbutton({
                        iconCls : 'icon-ok'
                    });
                    $.messager.alert("提示", data.message, icon, function () {
                        if (flag) {
                            $form_apply.form("clear");
                            $win_apply.dialog("close");
                            grid_group.datagrid('reload');
                        }
                    });
                }
            });
        }
});

/**
 * 关闭窗口
 */
function close_break(){
    win_message.window("close");
}

/**
 * 关闭会话连接
 */
function beforeClose(){
    if(websocket != null) websocket.close();
    clearInterval(timeBox);
}

/**
 * 验证消息删除
 */
$("#remove_verification").on("click",function(){
    var selects = $grid_verification.datagrid("getSelections");
    if(selects.length == 0) return;
    var ids = [];
    $(selects).each(function(i,o){
        ids.push(o.id);
    });
    $.messager.confirm("提示","确定删除消息记录？",function(r){
        if(r){
            $.ajax({
                type : 'post',
                url : PATH + '/group/verification/deleteArray',
                data : {
                    ids : ids.join(",")
                },
                success : function(data){
                    data = $.parseJSON(data);
                    var flag = data.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(flag){
                            $grid_verification.datagrid('reload');
                        }
                    });
                }
            });
        }
    });
});

function operating(id,isPass){
    var mes = isPass == 'Y' ? "同意" : "拒绝";
    $.messager.confirm("提示",mes+"此人加人群组？",function(r){
        if(r){
            $.ajax({
                type : 'post',
                url : PATH + '/group/verification/update',
                data : {
                    id : id,
                    isPass : isPass
                },
                success : function(data){
                    data = $.parseJSON(data);
                    var flag = data.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(flag){
                            $grid_verification.datagrid('reload');
                        }
                    });
                }
            });
        }
    });
}

//查询是否有待处理的验证消息
function pending(){
    $.ajax({
        type : 'post',
        url : PATH + '/group/verification/queryPending',
        success : function(data){
            data = $.parseJSON(data);
            if(data.success){
                $verification.children().children().eq(0).html("验证消息("+data.obj+")");
                $verification.css("color","red");
            }else {
                $verification.children().children().eq(0).html("验证消息");
                $verification.css("color","black");
            }
        }
    });
}