//制度列表
var $grid_regime;
var $add_regime;
var $edit_regime;
var $remove_regime;
var $ok_regime;
var $win_regime;
var $file;
var $win_regime_view;
$(function(){
    $grid_regime = $("#grid_regime");
    $add_regime = $("#add_regime");
    $edit_regime = $("#edit_regime");
    $remove_regime = $("#remove_regime");
    $ok_regime = $("#ok_regime");
    $win_regime = $("#win_regime");
    $file = $("#regime_file");
    $win_regime_view = $("#win_regime_view");
    $grid_regime.datagrid({
        url: PATH + '/regime/load',
        idField: 'id',
        pagination: true,
        height: '100%',
        border: false,
        fitColumns: true,
        autoRowHeight: false,
        rownumbers: true,
        ctrlSelect: true,
        sortName: 'createDate',
        sortOrder: 'desc',
        toolbar: '#tb_regime',
        pageSize : pageSize,
        pageList : pageList,
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'ID', hidden: true},
            {field: 'content', title: '内容',width:$(window).width()*0.3},
            {field: 'originalFileName', title: '附件',width:$(window).width()*0.3,'formatter':file_formatte},
            {field: 'modelName', title: '所属模块',width:$(window).width()*0.2},
            {field: 'createTime', title: '创建时间',width:$(window).width()*0.1},
            {field:'cz',title:'操作',width:$(window).width()*0.1,'formatter':cz_format}
        ]],
        onDblClickRow:function(rowIndex, rowData){
            regimeVeiw(rowData.id);
        }
    });
});

/**
 * 新增
 *
 */
$add_regime.on("click",function(){
    $win_regime.window("setTitle","新增");
    $win_regime.window("open");
    $win_regime.window("center");
    var $form = $win_regime.find("form");
    $form.attr("action",PATH + "/regime/insert");
});
/**
 * 编辑
 *
 */
$edit_regime.on("click",function(){
    //获取所有选中的行
    var selectRows = $grid_regime.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要编辑的数据","warning");
        return;
    }
    if(selectRows.length > 1){
        $.messager.alert("提示","只能选择一条数据进行编辑","warning");
        return;
    }
    $win_regime.window("setTitle","编辑");
    $win_regime.window("open");
    $win_regime.window("center");
    var $form = $win_regime.find("form");
    $form.attr("action",PATH + "/regime/update");
    $form.form("load",selectRows[0]);
    if(typeof selectRows[0].sysFile != 'undefined'){
        $file.filebox("setText",selectRows[0].sysFile.originalFileName);
    }
});
/**
 * 删除
 *
 */
$remove_regime.on("click",function(){
    //获取所有选中的行
    var selectRows = $grid_regime.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要删除的数据","warning");
        return;
    }
    var ids = [];
    $(selectRows).each(function(index,row){
        ids.push(row.id);
    });
    $.messager.confirm("提示","您确认要删除记录吗？",function(r){
        if(!r){
            return;
        }
        $.ajax({
            type : 'post',
            url : PATH + '/regime/delete',
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
                        $grid_regime.datagrid('reload');
                        $grid_regime.datagrid("clearSelections");
                    }
                });
            }
        });
    });
});
/**
 * 确定
 */
$ok_regime.linkbutton({
        onClick:function() {
            var $this = $(this);
            var $form = $(this).parent().parent().parent();
            //验证是否通过
            var ival = $form.form("validate");
            if (ival) {
                $this.linkbutton('disable');
                $this.linkbutton({
                    iconCls : 'icon-loding'
                });
                var action = $form.attr("action");
                var fileVal = $file.filebox("getText");
                $form.form('submit', {
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
                                $form.form("clear");
                                $win_regime.window("close");
                                $grid_regime.datagrid('reload');
                            }
                        });
                    },
                    onSubmit: function (param) {
                        param.fileName = fileVal;
                    }
                });
            }
        }
});

$file.filebox({
    icons: [{
        iconCls:'icon-clear',
        handler: function(e){
            $(e.data.target).textbox('setValue', '');
        }
    }]
});

function file_formatte(value,row,index){
    if(typeof row.sysFile == 'undefined') return;
    value = row.sysFile.originalFileName;
    if(typeof value == 'undefined') return;
    return "<a href='"+PATH+"/regime/download/"+row.id+"'>"+value+"</a>";
}
function cz_format(value,row,index){
    return "<a href='javascript:regimeVeiw(\""+row.id+"\");'>查看</a>";
}

function regimeVeiw(id){
    $win_regime_view.window("open");
    $win_regime_view.window("setTitle","查看");
    $win_regime_view.window("center");
    var form = $win_regime_view.find("form");
    $.ajax({
        type : 'post',
        url : PATH + '/regime/findById',
        data :{
            id : id
        },
        success : function(data){
            data = $.parseJSON(data);
            form.form("load",data);
            var td = form.find("[mark='fileName']");
            td.empty();
            if(typeof data.sysFile == 'undefined') return;
            var value = data.sysFile.originalFileName;
            if(typeof value == 'undefined') return;
            td.append("<a href='"+PATH+"/regime/download/"+data.id+"'>"+value+"</a>");
        }
    });
}