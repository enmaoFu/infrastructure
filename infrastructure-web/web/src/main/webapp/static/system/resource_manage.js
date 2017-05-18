//表格对象
var grid_resource;
//获取表单
var form_resource;
//获取窗口
var win_resource;
var parentId;
//初始化
$(function(){
    parentId = $("#parentId");
    grid_resource = $("#grid_resource");
    form_resource = $("#form_resource");
    win_resource = $("#win_resource");
    grid_resource.treegrid({
        //title : '资源管理',
        url : PATH + '/system/resource/load',
        idField:'id',
        treeField: 'name',
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        toolbar : '#tb_resource',
        columns : [[
            {field:'id',hidden:true},
            {field:'parentId',hidden:true},
            {field:'name',title:'资源名称'},
            {field:'type',title:'资源类型'},
            {field:'icon',title:'图标'},
            {field:'permission',title:'权限标志符'},
            {field:'url',title:'请求地址'},
            {field:'sorted',title:'排序编号'}
        ]],
        onClickRow : function(row){
            var btn = $("#add_resource");
            if(row.type == 'BUTTON'){
                btn.hide();
            }else {
                btn.show();
            }
        }
    });

    /*$(document).on("keyup",function(e){
        var message_window = $(".messager-window");
        if(message_window.length == 0 && e.keyCode == 13){
            $("#ok_resource").click();
        }
    });*/

});

/**
 * 添加下级按钮点击事件
 */
$("#add_resource").on("click",function(){
    //设置请求路径
    form_resource.attr("action",PATH + "/system/resource/insert");
    //打开窗口
    win_resource.window('open');
    form_resource.form("clear");
    //获取选中记录
    var node = grid_resource.treegrid("getSelected");
    parentId.val(node.id);
    //选中radio
    $($("input[name='type']")[0]).prop("checked",true);
    win_resource.window("setTitle","添加下级");
    //将窗口绝对居中
    win_resource.window("center");
});

/**
 *编辑按钮点击事件
 *
 */
$("#edit_resource").on("click",function(){
    //设置保存请求路径
    form_resource.attr("action",PATH + "/system/resource/update");
    //获取选中记录
    var node = grid_resource.treegrid("getSelected");
    if(node.length == 0){
        $.messager.alert("提示","请选择需要编辑的数据","warning");
        return;
    }
    //填充表单数据
    fun_edit_resource(node);
    //打开窗口
    win_resource.window('open');
    win_resource.window("setTitle","编辑");
    //将窗口绝对居中
    win_resource.window("center");
});

/**
 * 删除按钮点击事件
 */
$("#remove_resource").on("click",function(){
    //获取选中记录
    var node = grid_resource.treegrid("getSelected");
    if(node.length == 0){
        $.messager.alert("提示","请选择需要删除的数据","warning");
        return;
    }
    $.messager.confirm("提示","确认删除该菜单及其子菜单",function(r){
        if(!r){
            return;
        }
        $.ajax({
            type : 'post',
            url : PATH + '/system/resource/delete',
            data : {
                id : node.id
            },
            success : function(data){
                var icon = "error";
                if(data.success){
                    icon = "info";
                }
                $.messager.alert("提示",data.message,icon,function(){
                    if(data.success){
                        //属性表格
                        grid_resource.treegrid('reload');
                    }
                });
            }
        });
    });
});

/**
 * 确认按钮点击事件
 */
$("#ok_resource").on("click",function(){
    form_resource = $(this).parent().parent().parent();
    //验证是否通过
    var ival = form_resource.form("validate");
    if(ival){
        var action = form_resource.attr("action");
        //验证通过 请求后台
        form_resource.form('submit',{
            url : action,
            success : function(data){
                data = data.substr(0,data.lastIndexOf("}")+1);
                var dataJson = $.parseJSON(data);
                var flag = dataJson.success;
                var icon = "error";
                if(flag){
                    icon = "info";
                }
                $.messager.alert("提示",dataJson.message,icon,function(){
                    if(dataJson.success){
                        form_resource.form("clear");
                        win_resource.window("close");
                        //刷新表格
                        grid_resource.treegrid('reload');
                    }
                });
            }
        });
    }
});

/**
 * 重置按钮
 */
$("#reset_resource").on("click",function(){
    var title = win_resource.panel("options").title;
    if(title == '添加下级'){
        form_resource.form("clear");
        //选中radio
        $($("input[name='type']")[0]).prop("checked",true);
    }else {
        //获取选中记录
        var node = grid_resource.treegrid("getSelected");
        //重置表单数据
        fun_edit_resource(node);
    }
});

//填充表单数据
function fun_edit_resource(node){
    form_resource.form("reset");
    form_resource.form("load",node);
}