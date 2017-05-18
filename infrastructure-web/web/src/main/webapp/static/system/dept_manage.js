//表格对象
var grid_dept;
//窗口
var win_dept;
//表单
var form_dept;
var parentId;
$(function(){
    parentId = $("#parentId");
    grid_dept = $("#grid_dept");
    win_dept = $("#win_dept");
    form_dept = $("#form_dept");
    /**
     * 初始化grid_role
     */
    grid_dept.datagrid({
        url : PATH + '/system/dept/load',
        //title : '角色管理',
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        sortName : 'name',
        sortOrder : 'asc',
        toolbar : '#tb_dept',
        pageSize : pageSize,
        pageList : pageList,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'name',title:'部门名称',sortable:true,width:$(window).width()*0.2},
            {field:'code',title:'部门编号',width:$(window).width()*0.3},
            {field:'companyName',title:'所属企业',width:$(window).width()*0.2},
            {field:'remark',title:'备注',width:$(window).width()*0.3}
        ]]
    });

    //监听enter事件
    /*$(document).on("keyup",function(e){
        var message_window = $(".messager-window");
        if(message_window.length == 0 && e.keyCode == 13){
            $("#ok_dept").click();
        }
    });*/

    /**
     * 所属企业
     */
    $("#companyId").combobox({
        url : PATH + '/system/company/findByCid',
        onSelect : function(record){
            var value = record.id;
            findCidByDept(value);
        }
    });

    /**
     * 新增按钮点击事件
     */
    $("#add_dept").on("click",function(){
        //设置提交地址
        form_dept.attr("action",PATH + "/system/dept/insert");
        form_dept.form("reset");
        win_dept.window("open");
        win_dept.window("setTitle","新增");
        //将窗口绝对居中
        win_dept.window("center");
    });

    /**
     *编辑按钮点击事件
     */
    $("#edit_dept").on("click",function(){
        //设置保存请求路径
        form_dept.attr("action",PATH + "/system/dept/update");
        //获取所有选中记录
        var selectRows = grid_dept.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要编辑的数据","warning");
            return;
        }
        if(selectRows.length > 1){
            $.messager.alert("提示","只能选择一条数据","warning");
            return;
        }
        form_dept.form("clear");
        form_dept.form({
            onLoadSuccess : function(data){
                if(data != null){
                    var companyId = data.companyId;
                    findCidByDept(companyId);
                    //选中
                    parentId.combobox("select",data.parentId);
                }
            }
        });
        //填充表单数据
        form_dept.form("load",selectRows[0]);
        //打开窗口
        win_dept.window('open');
        win_dept.window("setTitle","编辑");
        //将窗口绝对居中
        win_dept.window("center");
    });

    /**
     * 删除按钮点击事件
     */
    $("#remove_dept").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = grid_dept.datagrid("getSelections");
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
                url : PATH + '/system/dept/delete',
                data : {
                    ids : ids.join(",")
                },
                success : function(data){
                    var icon = "error";
                    if(data.success){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(data.success){
                            grid_dept.datagrid('reload');
                            grid_dept.datagrid("clearSelections");
                        }
                    });
                }
            });
        });
    });

    /**
     * 确认按钮点击事件
     */
    $("#ok_dept").linkbutton({
        onClick:function() {
            var $this = $(this);
            form_dept = $this.parent().parent().parent();
            var ival = form_dept.form("validate");
            if(ival){
                $this.linkbutton('disable');
                $this.linkbutton({
                    iconCls : 'icon-loding'
                });
                //获取表单action
                var action = form_dept.attr("action");
                //验证通过 请求后台
                form_dept.form('submit',{
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
                                form_dept.form("clear");
                                win_dept.window("close");
                                grid_dept.datagrid('reload');
                            }
                        });
                    }
                });
            }
        }
    });

    /**
     * 重置按钮点击时间
     */
    $("#reset_dept").on("click",function(){
        var title = win_dept.panel("options").title;
        if(title == '新增'){
            form_dept.form("reset");
        }else {
            //获取选中记录
            var selectRows = grid_dept.datagrid("getSelections");
            //重置表单数据
            form_dept.form("load",selectRows[0]);
        }
    });
});

/**
 * 查询部门
 * @param companyId
 */
function findCidByDept(companyId){
    parentId.combobox({
        valueField:'id',
        textField:'text',
        method : 'post',
        url : PATH + '/system/dept/findDept',
        onBeforeLoad: function(param){
            param.companyId = companyId;
        },
        onSelect : function(record){
            //获取所有选中记录
            var selectRows = grid_dept.datagrid("getSelections");
            if(selectRows.length == 0){
                return;
            }
            //选中数据的ID
            var id = selectRows[0].id;
            //获取选中数据的上级ID
            var pid = selectRows[0].parentId;
            if(record && record.id == id){
                $.messager.alert("提示","不能选择本部门为上级部门","warning",function(){
                    if(pid){
                        parentId.combobox("select",pid);
                    }else{
                        parentId.combobox("setValue","");
                    }
                });
            }
        }
    });
}