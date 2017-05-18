//表格对象
var sysuser_grid;
//获取表单
var form;
var $companyId = $("#companyId");
var $deptId = $("#deptId");
var $login = $("#login");
var $perType;
//请求远程数据时发送的额外参数
var queryParams = {

};
$(function(){
    $perType = $("#perType");
    //获取表格对象
    sysuser_grid = $("#sysuser_grid");
    //获取表单
    form = $("#form");
    //获取窗口对象
    var win = $("#win");
    /**
     * 初始化sysuser_grid
     */
    sysuser_grid.datagrid({
        url : PATH + '/system/sysuser/load',
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        sortName : 'login',
        sortOrder : 'asc',
        toolbar : '#toolbar',
        queryParams : queryParams,
        pageSize : pageSize,
        pageList : pageList,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'login',title:'登录名',sortable:true},
            {field:'username',title:'姓名'},
            {field:'sex',title:'性别',formatter:sex_formate},
            {field:'age',title:'年龄'},
            {field:'birthday',title:'生日'},
            {field:'parentName',title:'上级'},
            {field:'deptName',title:'所属部门'},
            {field:'companyName',title:'所属公司'},
            {field:'creataDate',title:'创建时间',sortable:true},
            {field:'updateDate',title:'修改时间',sortable:true},
            {field:'available',title:'是否可用',formatter:available_formate}
        ]]
    });

    /**
     * 初始化下拉框
     */
    $("#select").combobox({
        valueField : 'id',
        textField : 'text',
        data : [
            {
                'id' : '-1',
                'text' : '=请选择='
            },
            {
                'id' : 'login',
                'text' : '登录名'
            },
            {
                'id' : 'username',
                'text' : '姓名'
            }
        ],
        onLoadSuccess : function(){
            $(this).combobox("select","-1");
        }
    });

    /*$(document).on("keyup",function(e){
        var message_window = $(".messager-window");
        if(message_window.length == 0 && e.keyCode == 13){
            $("#ok").click();
        }
    });*/

    /**
     * 新增按钮点击事件
     */
    $("#add").on("click",function(){
        //设置请求路径
        form.attr("action",PATH + "/system/sysuser/insert");
        //打开窗口
        win.window('open');
        win.window("setTitle","新增");
        //将窗口绝对居中
        win.window("center");
        form.form("reset");
        //选中radio
        $($("input[name='sex']")[0]).prop("checked",true);
        $($("input[name='available']")[0]).prop("checked",true);
        $prefix.textbox("setValue",prefix);
        $companyId.combobox("select",companyId);
    });

    /**
      *编辑按钮点击事件
      */
    $("#edit").on("click",function(){
        //设置保存请求路径
        form.attr("action",PATH + "/system/sysuser/update");
        //获取所有选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
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
        fun_edit(id);
        //打开窗口
        win.window('open');
        win.window("setTitle","编辑");
        //将窗口绝对居中
        win.window("center");
    });

    /**
     * 删除按钮点击事件
     */
    $("#remove").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
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
                url : PATH + '/system/sysuser/delete',
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
                            //刷新表格
                            sysuser_grid.datagrid('reload');
                            sysuser_grid.datagrid("clearSelections");
                        }
                    });
                }
            });
        });
    });

    /**
     * 关联角色
     */
    $("#select_role").on("click",function(){
        $perType.val("role");
        //获取所有选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
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
        //公司ID
        var companyId = selectRows[0].companyId;
        var win_select_tree = $("#win_select_tree");
        //打开窗口
        win_select_tree.window('open');
        win_select_tree.window("setTitle","关联角色");
        //将窗口绝对居中
        win_select_tree.window("center");
        select_tree(id,companyId);
    });

    /**
     * 赋予查询权限
     */
    $("#select_query_perm").on("click",function(){
        //获取所有选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要赋予查询权限的数据","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据","warning");
            return;
        }
        var win_select_tree = $("#win_select_tree");
        //打开窗口
        win_select_tree.window('open');
        win_select_tree.window("setTitle","查询权限");
        //将窗口绝对居中
        win_select_tree.window("center");
        $perType.val("perType");
        select_query_perm_tree();
    });

    /**
     * 关联角色 确定
     */
    $("#ok_tree").on("click",function(){
        //判断按钮类型(关联角色,查询权限)
        var perType = $perType.val();
        //获取所有选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
        //ID
        var id = selectRows[0].id;
        //获取所有选中的角色
        var checkedNodes = $('#ztree').tree("getChecked");
        //选中角色的ID
        var roleIds = [];
        $(checkedNodes).each(function(i,o){
            if(typeof o.children == 'undefined'){
                roleIds.push(o.id);
            }
        });
        if(roleIds.length > 1){
            $.messager.alert("提示","每个用户只能拥有一个岗位","warning");
            return;
        }
        if(perType == 'role'){
            $.ajax({
                type : 'post',
                url : PATH + '/system/sysuser/addUserRole',
                data : {
                    uId : id,
                    roleIds : roleIds.join(",")
                },
                success : function(msg){
                    var flag = msg.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",msg.message,icon,function(){
                        if(flag){
                            $("#win_select_tree").window("close");
                        }
                    });
                }
            });

        }else if(perType == 'perType'){
            if(roleIds.length == 0){
                $.messager.alert("提示","请选择需要赋予查询权限的数据","warning");
                return;
            }else{
                $.ajax({
                    type : 'post',
                    url : PATH + '/queryPerm/addQueryUserPermissions',
                    data : {
                        roleIds : roleIds.join(",")
                    },
                    success : function(msg){
                        var flag = msg.success;
                        var icon = "error";
                        if(flag){
                            icon = "info";
                        }
                        $.messager.alert("提示",msg.message,icon,function(){
                            if(flag){
                                $("#win_select_tree").window("close");
                            }
                        });
                    }
                });
            }
        }
    });

    /**
     * 关联角色弹窗重置事件
     */
    $("#reset_tree").on("click",function(){
        //获取所有选中记录
        var selectRows = sysuser_grid.datagrid("getSelections");
        //ID
        var id = selectRows[0].id;
        //公司ID
        var companyId = selectRows[0].companyId;
        select_tree(id,companyId);
    });

    /**
     * 确认按钮点击事件
     */
    $("#ok").linkbutton({
        onClick:function() {
            var $this = $(this);
            form = $this.parent().parent().parent();
            //验证是否通过
            var ival = form.form("validate");
            //密码
            var pwd = $("#pwd").val();
            //重复密码
            var rpwd = $("#rpwd").val();
            if(pwd != '' && rpwd != '' && pwd != rpwd) {
                ival = false;
                $.messager.alert("警告","两次密码不相同","error");
            }
            if(ival){
                $this.linkbutton('disable');
                $this.linkbutton({
                    iconCls : 'icon-loding'
                });
                var action = form.attr("action");
                //验证通过 请求后台
                form.form('submit',{
                    url : action,
                    success : function(data){
                        data = data.substr(0,data.lastIndexOf("}")+1);
                        var dataJson = $.parseJSON(data);
                        var flag = dataJson.success;
                        var icon = "error";
                        if(flag){
                            icon = "info";
                        }
                        $this.linkbutton('enable');
                        $this.linkbutton({
                            iconCls : 'icon-ok'
                        });
                        $.messager.alert("提示",dataJson.message,icon,function(){
                            if(dataJson.success){
                                form.form("clear");
                                win.window("close");
                                //属性表格
                                sysuser_grid.datagrid('reload');
                            }
                        });
                    }
                });
            }
        }
    });

    /**
     * 重置按钮
     */
    $("#reset").on("click",function(){
        var title = win.panel("options").title;
        if(title == '新增'){
            form.form("clear");
            //选中radio
            $($("input[name='sex']")[0]).prop("checked",true);
            $prefix.textbox("setValue",prefix);
        }else {
            //获取选中记录
            var selectRows = sysuser_grid.datagrid("getSelections");
            //重置表单数据
            fun_edit(selectRows[0].id);
        }
    });

    /**
     * 搜索按钮事件
     */
    $("#search_btn").on("click",fun_search);

    $.ajax({
        type : 'post',
        url : PATH + '/system/company/findCidByRoles',
        success : function(data){
            if(data.length == 2){
                return;
            }
            data = eval("("+data+")");
            data = eval("("+data+")");
            $("#roles").combotree("loadData",data);
        }
    });
});

//格式化
function available_formate(value,row,index){
    var span;
    if(value){
        span = "<span style='color: #27ff0d;'>√</span>";
    }else{
        span = "<span style='color: red;'>×</span>";
    }
    return span;
}
//性别格式化
function sex_formate(value,row,index){
    if(value == '' || value == null){
        return '';
    }
    return value == "male" ? "男" : "女";
}

//填充表单数据
function fun_edit(id){
    form.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/system/sysuser/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            data.available?data.available=1:data.available=0;
            //填充记录到表单中
            form.form({
                onLoadSuccess : function(data){
                    if(data != null){
                        var deptId = data.deptId;
                        var companyId = data.companyId;
                        findCidByDept(companyId);
                        //选中
                        $deptId.combobox("select",deptId);
                    }
                }
            });
            form.form("load",data);
            var login = data.login;
            if(typeof data.prefix != 'undefined'){
                login = login.substr(data.prefix.length,login.length);
            }
            $login.textbox("setValue",login);
        }
    });
}

/**
 * 搜索
 */
function fun_search(){
    //获取select的值
    var select_val = $("#select").combobox("getValue");
    //获取搜索框的值
    var search_val = $("#search").val();
    if(select_val != -1){
        //需要传人后台的参数 格式必须是{"name":"text"}
        var para = '{"'+select_val+'":"'+search_val+'"}';
        //转为JSON对象
        para = $.parseJSON(para);
        $.extend(para,queryParams);
        sysuser_grid.datagrid("load",para);
    }else{
        sysuser_grid.datagrid("load",queryParams);
    }
}

//查询角色
function select_tree(uId,companyId){
    var tree = $('#ztree');
    $.ajax({
        type : 'post',
        url : PATH + '/system/company/findCidByRoles',
        data : {
            companyId : companyId
        },
        success : function(data){
            data = eval("("+data+")");
            data = eval("("+data+")");
            tree.tree({
                data : data
            });
            //根据用户ID查询该用户拥有的角色
            $.ajax({
                type : 'post',
                url : PATH + '/system/role/getRoleByUid',
                data : {
                    uId : uId
                },
                success : function(roles){
                    roles = $($.parseJSON(roles));
                    roles.each(function(i,o){
                        //选中拥有的角色
                        var node = tree.tree("find",o);
                        if(node != null){
                            tree.tree("check",node.target);
                        }
                    });
                }
            });
        }
    });
}

//查询权限
function select_query_perm_tree(uId){
    var tree = $('#ztree');
    $.ajax({
        type : 'post',
        url : PATH + '/queryPerm/findQueryPermByUser',
        success : function(data){
            data = eval("("+data+")");
            data = eval("("+data+")");
            tree.tree({
                data : data
            });
            //根据用户ID查询该用户拥有的角色
            $.ajax({
                type : 'post',
                url : PATH + '/queryPerm/getQueryPermByUid',
                success : function(roles){
                    roles = $($.parseJSON(roles));
                    roles.each(function(i,o){
                        //选中拥有的角色
                        var node = tree.tree("find", o.id);
                        if(node != null){
                            tree.tree("check",node.target);
                        }
                    });
                }
            });
        }
    });
}
/**
 * 所属企业改变事件
 */
$companyId.combobox({
    onSelect : function(record){
        if(!record) return;
        var value = record.id;
        findCidByDept(value);
    }
});

/**
 * 查询部门
 * @param companyId
 */
function findCidByDept(companyId){
    $deptId.combobox({
        valueField:'id',
        textField:'text',
        required:true,
        method : 'post',
        url : PATH + '/system/dept/findDept',
        onBeforeLoad: function(param){
            param.companyId = companyId;
        }
    });
}