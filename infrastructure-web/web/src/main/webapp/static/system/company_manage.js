//表格对象
var company_grid;
//获取表单
var form_company;
//获取窗口
var win_company;
var $add_company;
var $add_company_children;
var $add_company_parent;
//初始化
$(function(){
    company_grid = $("#company_grid");
    form_company = $("#form_company");
    win_company = $("#win_company");
    $add_company = $("#add_company");
    $add_company_children = $("#add_company_children");
    $add_company_parent = $("#add_company_parent");
    /**
     * 初始化company_grid
     */
    company_grid.treegrid({
        url : PATH + '/system/company/load',
        //title : '企业管理',
        idField:'id',
        treeField: 'name',
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        toolbar : '#tb_company',
        columns : [[
            {field:'ck',checkbox:true},
            {field:'name',title:'企业名称',width:$(window).width()*0.2},
            {field:'abbreviation',title:'简称',width:$(window).width()*0.1},
            {field:'address',title:'地址',width:$(window).width()*0.2},
            {field:'motto',title:'经营范围',width:$(window).width()*0.2},
            {field:'master',title:'法定代表',width:$(window).width()*0.2},
            {field:'createDate',title:'创建时间',width:$(window).width()*0.1}
        ]],
        onLoadSuccess : function(row,data){
            if(login != 'administrator'){
                $add_company.hide();
            }
        },
        onClickRow : function(row){
            if(row.parent == null || row.parent == ''){
                $add_company_parent.show();
            }else {
                $add_company_parent.hide();
            }
        }
    });

    /**
     * 新增按钮点击事件
     */
    $add_company.on("click",function(){
        //设置请求路径
        form_company.attr("action",PATH + "/system/company/insert");
        //打开窗口
        win_company.window('open');
        form_company.form("clear");
        win_company.window("setTitle","新增");
        //将窗口绝对居中
        win_company.window("center");
    });

    /**
     * 添加上级
     */
    $add_company_parent.on("click",function(){
        //获取选中记录
        var selectRows = company_grid.treegrid("getSelections")[0];
        if(!selectRows){
            $.messager.alert("提示","请选择一条数据作为下级企业","warning");
            return;
        }
        //设置请求路径
        form_company.attr("action",PATH + "/system/company/insert");
        win_company.window("open");
        form_company.form("clear");
        win_company.window("setTitle","添加上级级");
        win_company.window("center");
        var id = selectRows.id;
        $("#id").val(id);
        $("#parent").val("");
    });

    /**
     * 添加下级点击事件
     */
    $add_company_children.on("click",function(){
        //获取选中记录
        var selectRows = company_grid.treegrid("getSelections")[0];
        if(!selectRows){
            $.messager.alert("提示","请选择一条数据作为上级企业","warning");
            return;
        }
        //设置请求路径
        form_company.attr("action",PATH + "/system/company/insert");
        win_company.window("open");
        form_company.form("clear");
        win_company.window("setTitle","添加下级");
        //将窗口绝对居中
        win_company.window("center");
        var id = selectRows.id;
        var parents = selectRows.parents;
        $("#parent").val(id);
        if(parents){
            $("#parents").val(parents+","+id);
        }else {
            $("#parents").val(id);
        }
    });

    /**
     *编辑按钮点击事件
     */
    $("#edit_company").on("click",function(){
        //设置保存请求路径
        form_company.attr("action",PATH + "/system/company/update");
        //获取所有选中记录
        var selectRows = company_grid.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要编辑的数据","warning");
            return;
        }
        //ID
        var id = selectRows[0].id;
        //填充表单数据
        fun_edit(id);
        //打开窗口
        win_company.window('open');
        win_company.window("setTitle","编辑");
        //将窗口绝对居中
        win_company.window("center");
    });

    /**
     * 删除按钮点击事件
     */
    $("#remove_company").on("click",function(){
        //获取选中记录
        var selectRows = company_grid.datagrid("getSelections");
        if(selectRows.length == 0){
            $.messager.alert("提示","请选择需要删除的数据","warning");
            return;
        }
        $.messager.confirm("提示","确认删除该企业及其子企业",function(r){
            if(!r){
                return;
            }
            $.ajax({
                type : 'post',
                url : PATH + '/system/company/delete',
                data : {
                    id : selectRows[0].id
                },
                success : function(data){
                    var icon = "error";
                    if(data.success){
                        icon = "info";
                    }
                    $.messager.alert("提示",data.message,icon,function(){
                        if(data.success){
                            company_grid.treegrid('reload');
                            company_grid.datagrid("clearSelections");
                        }
                    });
                }
            });
        });
    });

    /**
     * 确认按钮点击事件
     */
    $("#ok_company").linkbutton({
        onClick:function() {
            var $this = $(this);
            form_company = $this.parent().parent().parent();
            var ival = form_company.form("validate");
            if(ival){
                $this.linkbutton('disable');
                $this.linkbutton({
                    iconCls : 'icon-loding'
                });
                //获取表单action
                var action = form_company.attr("action");
                //验证通过 请求后台
                form_company.form('submit',{
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
                                form_company.form("clear");
                                win_company.window("close");
                                //属性表格
                                company_grid.treegrid('reload');
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
    $("#reset_company").on("click",function(){
        var title = win_company.panel("options").title;
        if(title == '编辑'){
            //获取选中记录
            var selectRows = company_grid.treegrid("getSelections");
            //重置表单数据
            //重置表单数据
            fun_edit(selectRows[0].id);
        }else {
            form_company.form("clear");
        }
    });
});

//填充表单数据
function fun_edit(id){
    form_company.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/system/company/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            //填充记录到表单中
            form_company.form("load",data);
        }
    });
}