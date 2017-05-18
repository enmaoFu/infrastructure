//表格对象
var grid_role;
//表单
var form_role;
//资源树
var zTree;
var $deptId;
var $parentId;
var $companyId;
$(function(){
    $deptId = $("#deptId");
    $parentId = $("#parentId");
    //获取表格对象
    grid_role = $("#grid_role");
    //获取表单数据
    form_role = $("#form_role");
    $companyId = $("#companyId");
    //获取窗口
    var win_role = $("#win_role");
    /**
     * 初始化grid_role
     */
    grid_role.datagrid({
        url : PATH + '/system/role/load',
        //title : '角色管理',
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        sortName : 'sorted',
        sortOrder : 'asc',
        toolbar : '#tb_role',
        pageSize : pageSize,
        pageList : pageList,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'name',title:'岗位名称',width:$(window).width()*0.15},
            {field:'description',title:'岗位描述',width:$(window).width()*0.3},
            {field:'deptName',title:'所属部门',width:$(window).width()*0.1},
            {field:'companyName',title:'所属企业',width:$(window).width()*0.25},
            {field:'isSys',title:'系统管理员',width:$(window).width()*0.15,formatter:system_formate},
            {field:'remark',title:'备注',width:$(window).width()*0.1},
            /*{field:'isManager',title:'部门经理',width:$(window).width()*0.1,
                formatter: function(value,row,index){
                    if(value == 'Y'){
                        return "<div style='color: red;'>是</div>";
                    }else {
                        return "<div>否</div>";
                    }
                }
            },*/
            {field:'sorted',title:'排序',sortable:true,width:$(window).width()*0.1}
        ]]
    });

    //监听enter事件
    /*$(document).on("keyup",function(e){
        var message_window = $(".messager-window");
        if(message_window.length == 0 && e.keyCode == 13){
            $("#ok_role").click();
        }
    });*/


    /**
     * 新增按钮点击事件
     */
    $("#add_role").on("click",function(){
        //设置请求路径
        form_role.attr("action",PATH + "/system/role/insert");
        //打开窗口
        win_role.window('open');
        form_role.form("reset");
        win_role.window("setTitle","新增");
        //将窗口绝对居中
        win_role.window("center");
        win_open_load();
    });

    /**
     *编辑按钮点击事件
     */
    $("#edit_role").on("click",function(){
        //设置保存请求路径
        form_role.attr("action",PATH + "/system/role/update");
        //获取所有选中记录
        var selectRows = grid_role.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要编辑的数据","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据","warning");
            return;
        }
        //打开窗口
        win_role.window('open');
        win_role.window("setTitle","编辑");
        //将窗口绝对居中
        win_role.window("center");
        //ID
        var id = selectRows[0].id;
        //填充表单数据
        fun_edit_role(id);
        win_open_load();
    });

    /**
     * 删除按钮点击事件
     */
    $("#remove_role").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = grid_role.datagrid("getSelections");
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
                url : PATH + '/system/role/delete',
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
                            grid_role.datagrid('reload');
                            grid_role.datagrid("clearSelections");
                        }
                    });
                }
            });
        });
    });

    /**
     * 菜单赋权
     */
    $("#authorization").on("click",function(){
        //获取所有选中记录
        var selectRows = grid_role.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要赋权的角色","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据","warning");
            return;
        }
        var win_tree = $("#win_tree");
        //获取选中角色ID
        var id = selectRows[0].id;
        //打开窗口
        win_tree.window('open');
        win_tree.window("setTitle","岗位赋权");
        //将窗口绝对居中
        win_tree.window("center");
        getzTree(id);
    });

    /**
     * 确认按钮点击事件
     */
    $("#ok_role").linkbutton({
        onClick:function() {
            var $this = $(this);
            form_role = $this.parent().parent().parent();
            var ival = form_role.form("validate");
            if(ival){
                $this.linkbutton('disable');
                $this.linkbutton({
                    iconCls : 'icon-loding'
                });
                //获取表单action
                var action = form_role.attr("action");
                //验证通过 请求后台
                form_role.form('submit',{
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
                                form_role.form("clear");
                                win_role.window("close");
                                grid_role.datagrid('reload');
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
    $("#reset_role").on("click",function(){
        var title = win_role.panel("options").title;
        if(title == '新增'){
            form_role.form("reset");
        }else {
            //获取选中记录
            var selectRows = grid_role.datagrid("getSelections");
            //重置表单数据
            fun_edit_role(selectRows[0].id);
        }
    });

    //菜单赋权保存
    $("#ok_tree").on("click",function(){
        //获取所有选中记录
        var selectRows = grid_role.datagrid("getSelections");
        //获取选中角色ID
        var rId = selectRows[0].id;
        //获取被勾选的节点集合
        var nodes = zTree.getCheckedNodes(true);
        var ids = [];
        if(nodes.length > 0){
            $(nodes).each(function(i,o){
                ids.push(o.id);
            });
        }
        $.ajax({
            type : 'post',
            data : {
              ids : ids.join(","),
              rId : rId
            },
            url : PATH + '/system/role/authorization',
            success : function(msg){
                var flag = msg.success;
                var icon = "error";
                if(flag){
                    icon = "info";
                }
                $.messager.alert("提示",msg.message,icon,function(){
                    if(flag){
                        $("#win_tree").window("close");
                    }
                });
            }
        });
    });

    //菜单赋权重置
    $("#reset_tree").on("click",function(){
        //获取所有选中记录
        var selectRows = grid_role.datagrid("getSelections");
        //获取选中角色ID
        var id = selectRows[0].id;
        getzTree(id);
    });

});

/**
 * 窗口打开后加载
 */
function win_open_load(){
    /**
     * 所属企业改变事件
     */
    $companyId.combobox({
        required : true,
        valueField : 'id',
        textField : 'text',
        url : PATH +'/system/company/findByCid',
        onSelect : function(record){
            var value = record.id;
            //查询部门
            findCidByDept(value);
            //查询岗位
            findCidByRole(value);
        }
    });
};

//填充表单数据
function fun_edit_role(id){
    form_role.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/system/role/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_role.form({
                onLoadSuccess : function(data){
                    if(data != null){
                        var companyId = data.companyId;
                        findCidByDept(companyId);
                        findCidByRole(companyId);
                        //选中
                        $deptId.combobox("select",data.deptId);
                        $parentId.combobox("select",data.parentId);
                    }
                }
            });
            form_role.form("load",data);
        }
    });
}

//得到树结构
function getzTree(id){
    //根据角色ID查询角色所拥有的权限
    $.ajax({
        type : 'post',
        url : PATH + '/system/resource/findRidByResource',
        data : {
            rid : id
        },
        success :function(result){
            //查询当前用户拥有的权限
            $.ajax({
                type : 'post',
                url : PATH + '/system/resource/ajax',
                success :function(data){
                    fun_zTree(data,result);
                }
            });
        }
    });

}

/**
 * 菜单树结构
 * @param menus
 */
function  fun_zTree(menus,checked){
    var zNodes = [];
    var setting = {
        treeId : "zTree",
        check: {
            enable: true
            //chkboxType: { "Y": "p", "N": "s" }
        },
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
        }
    };

    for(var i = 0;i < menus.length;i++){
        var menu = menus[i];
        var id = menu.id;
        var pId = typeof menu.parentId == 'undefined' ? 0 : menu.parentId;
        var name = menu.name;
        var zNode = '{id:"'+id+'",pId:"'+pId+'",name:"'+name+'"}';
        if(pId == 0){
            zNode = "{id:'"+id+"',pId:'0',name:'"+name+"',open:true}";
        }
        zNodes.push(eval('('+zNode+')'));
    }
    zTree = $.fn.zTree.init($("#tree"),setting,zNodes);
    // 选中已有的
    if(typeof checked != 'undefined' && checked.length > 0){
        $(checked).each(function(i, o){
            var node = zTree.getNodeByParam("id", o, null);
            if(node != null){
                zTree.checkNode(node, true, true);
            }
        });
    }
}

/**
 * 查询部门
 * @param companyId
 */
function findCidByDept(companyId){
    $deptId.combobox({
        valueField:'id',
        textField:'text',
        method : 'post',
        url : PATH + '/system/dept/findDept',
        onBeforeLoad: function(param){
            param.companyId = companyId;

        }
    });
}

/**
 * 查询岗位
 * @param companyId
 */
function findCidByRole(companyId){
    $parentId.combobox({
        valueField:'id',
        textField:'text',
        method : 'post',
        url : PATH + '/system/role/findByCid',
        onBeforeLoad: function(param){
            param.companyId = companyId;

        }
    });
}

function system_formate(value,row,index){
    var span;
    if(value == 'Y'){
        span = "<span style='color: #27ff0d;'>是</span>";
    }else{
        span = "<span style='color: red;'></span>";
    }
    return span;
}