//表格对象
var grid_queryPerm;
//表单
var form_queryPerm;
//请求远程数据时发送的额外参数
var queryParams = {

};
$(function(){
    //获取表格对象
    grid_queryPerm = $("#grid_queryPerm");
    //获取表单数据
    form_queryPerm= $("#form_queryPerm");
    //获取窗口
    var win_queryPerm = $("#win_queryPerm");
    /**
     * 初始化grid_queryPerm
     */
    grid_queryPerm.datagrid({
        url : PATH + '/queryPerm/load',
        //title : '成本管理',
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
        toolbar : '#queryPerm_toolbar',
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'permName',title:'查询权限名称'},
            {field:'permIdentification',title:'查询权限标识'},
            {field:'createTime',title:'创建时间',sortable:true},
            {field:'coName',title:'所属公司'},
            {field:'remark',title:'备注'}
        ]]
    });



    //监听enter事件
    $(document).on("keyup",function(e){
        var message_window = $(".messager-window");
        if(message_window.length == 0 && e.keyCode == 13){
            $("#ok_queryPerm").click();
        }
    });

    /**
     * 新增按钮点击事件
     */
    $("#add_queryPerm").on("click",function(){
        //设置请求路径
        form_queryPerm.attr("action",PATH + "/queryPerm/insert");
        //打开窗口
        win_queryPerm.window('open');
        form_queryPerm.form("reset");
        win_queryPerm.window("setTitle","新增");
        //将窗口绝对居中
        win_queryPerm.window("center");
    });


    /**
     * 确认按钮点击事件
     */
    $("#ok_queryPerm").on("click",function(){
        //验证是否通过
        var ival = form_queryPerm.form("validate");
        if(ival){
            var action = form_queryPerm.attr("action");
            //验证通过 请求后台
            form_queryPerm.form('submit',{
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
                            form_queryPerm.form("clear");
                            win_queryPerm.window("close");
                            //属性表格
                            grid_queryPerm.datagrid('reload');
                        }
                    });
                }
            });
        }
    });


    /**
     * 重置按钮
     */
    $("#reset_queryPerm").on("click",function(){
        var title = win_queryPerm.panel("options").title;
        if(title == '新增'){
            form_queryPerm.form("reset");
        }else {
            //获取选中记录
            var selectRows = grid_queryPerm.datagrid("getSelections");
            //重置表单数据
            fun_edit_queryPerm(selectRows[0].id);
        }
    });

    /**
     *编辑按钮点击事件
     */
    $("#edit_queryPerm").on("click",function(){
        //设置保存请求路径
        form_queryPerm.attr("action",PATH + "/queryPerm/update");
        //获取所有选中记录
        var selectRows = grid_queryPerm.datagrid("getSelections");
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
        fun_edit_queryPerm(id);
        //打开窗口
        win_queryPerm.window('open');
        win_queryPerm.window("setTitle","编辑");
        //将窗口绝对居中
        win_queryPerm.window("center");
    });



    /**
     * 删除按钮点击事件
     */
    $("#remove_queryPerm").on("click",function(){
        var ids = [];
        //获取选中记录
        var selectRows = grid_queryPerm.datagrid("getSelections");
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
                url : PATH + '/queryPerm/delete',
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
                            grid_queryPerm.datagrid('reload');
                        }
                    });
                }
            });
        });
    });

    /**
     * 搜索按钮事件
     */
    $("#search_btn").on("click",fun_search);

    //填充表单数据
    function fun_edit_queryPerm(id){
        form_queryPerm.form("reset");
        $.ajax({
            type : 'post',
            url : PATH + '/queryPerm/findById',
            data : {
                id : id
            },
            success : function(data){
                data = eval("("+data+")");
                form_queryPerm.form({
                    onLoadSuccess : function(data){
                        if(data != null){

                        }
                    }
                });
                form_queryPerm.form("load",data);
            }
        });
    }
});

/**
 * 搜索
 */
function fun_search(){
    var permName = $("#search_user").val();
    //创建时间：
    var search_startsignDate = $("#search_startsignDate").datebox("getValue");
    var search_endsignDate = $("#search_endsignDate").datebox("getValue");
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para = '{"perm_name":"'+permName+'"}';
    queryParams={
        perm_name: permName,
        stertsignDate: search_startsignDate,
        endsignDate: search_endsignDate
    }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_queryPerm.datagrid("load",para);
}
