var $grid_announcement;
var $add_ac;
var $edit_ac;
var $remove_ac;
var $release_ac;
var $win_ac;
var $ok_ac;
var $ac_search;
var $win_ac_view;
var $dlg_release;
var $file;

$(function(){
    $grid_announcement = $("#grid_announcement");
    $add_ac = $("#add_ac");
    $edit_ac = $("#edit_ac");
    $remove_ac = $("#remove_ac");
    $release_ac = $("#release_ac");
    $win_ac = $("#win_ac");
    $ok_ac = $("#ok_ac");
    $ac_search = $("#ac_search");
    $win_ac_view = $("#win_ac_view");
    $dlg_release = $("#dlg_release");
    $file = $("#ann_file");

    $grid_announcement.datagrid({
        url: PATH + '/announcement/load',
        idField: 'id',
        pagination: true,
        height: '100%',
        border: false,
        fitColumns: true,
        autoRowHeight: false,
        rownumbers: true,
        ctrlSelect: true,
        sortOrder: 'desc',
        toolbar: '#tb_ac',
        pageSize : twoPageSize,
        pageList : twoPageList,
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'ID', hidden: true},
            {field: 'title', title: '标题',width:$(window).width()*0.2},
            {field: 'content', title: '内容',width:$(window).width()*0.25},
            {field: 'originalFileName', title: '附件',width:$(window).width()*0.15,'formatter':file_formatte},
            {field: 'arange', title: '发布状态',width:$(window).width()*0.1,'formatter':arange_formatte},
            {field: 'modelName', title: '所属模块',width:$(window).width()*0.1},
            {field: 'releaseTime', title: '发布时间',width:$(window).width()*0.1},
            {field: 'createTime', title: '创建时间',width:$(window).width()*0.1},
            {field:'cz',title:'操作',width:$(window).width()*0.1,'formatter':cz_format}
        ]],
        onDblClickRow:function(rowIndex, rowData){
            annVeiw(rowData.id);
        }
    });
});

/**
 * 新增
 */
$add_ac.on("click",function(){
    $win_ac.window("setTitle","新增");
    $win_ac.window("open");
    $win_ac.window("center");
    var $form = $win_ac.find("form");
    $form.form("clear");
    $form.attr("action",PATH + "/announcement/insert");
});
/**
 * 编辑
 */
$edit_ac.on("click",function(){
    //获取所有选中的行
    var selectRows = $grid_announcement.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要编辑的数据","warning");
        return;
    }
    if(selectRows.length > 1){
        $.messager.alert("提示","只能选择一条数据进行编辑","warning");
        return;
    }
    var row = selectRows[0];
    if(!!row.arange){
        $.messager.alert("提示","已发布，不能修改","warning");
        return;
    }
    $win_ac.window("setTitle","新增");
    $win_ac.window("open");
    $win_ac.window("center");
    var $form = $win_ac.find("form");
    $form.attr("action",PATH + "/announcement/update");
    $form.form("clear");
    $form.form("load",row);
    if(!!row.sysFile){
        $file.filebox("setText",row.sysFile.originalFileName);
    }
});
/**
 * 删除
 */
$remove_ac.on("click",function(){
    //获取所有选中的行
    var selectRows = $grid_announcement.datagrid("getSelections");
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
            url : PATH + '/announcement/delete',
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
                        $grid_announcement.datagrid('reload');
                        $grid_announcement.datagrid("clearSelections");
                    }
                });
            }
        });
    });
});

$file.filebox({
    icons: [{
        iconCls:'icon-clear',
        handler: function(e){
            $(e.data.target).textbox('setValue', '');
        }
    }]
});

/**
 * 发布
 */
$release_ac.on("click",function(){
    //获取所有选中的行
    var selectRows = $grid_announcement.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要发布的数据","warning");
        return;
    }
    var ids = [];
    $(selectRows).each(function(index,row){
        if(!row.arange) ids.push(row.id);
    });
    if(ids.length == 0){
        $.messager.alert("提示","无需重复发布","warning");
        return;
    }
    $dlg_release.dialog("open");
    $dlg_release.dialog("setTitle","发布");
    $dlg_release.dialog("center");
});
/**
 * 搜索
 */
$ac_search.on("click",function(){
    var title = $(this).prev().prev().textbox("getValue");
    $grid_announcement.datagrid('load',{
        title : title
    });
});


/**
 * 确定
 */
$ok_ac.linkbutton({
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
                                $win_ac.window("close");
                                $grid_announcement.datagrid('reload');
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

/**
 * 查看
 * @param id
 */
function annVeiw(id){
    $win_ac_view.window("setTitle","查看");
    $win_ac_view.window("open");
    $win_ac_view.window("center");
    var $form = $win_ac_view.find("form");
    $form.form("clear");
    $.ajax({
        type : 'post',
        url : PATH + '/announcement/findById',
        data : {
            id : id
        },
        success :function(data){
            data = $.parseJSON(data);
            $form.form("load",data);
            var td = $form.find("[mark='fileName']");
            td.empty();
            if(!data.sysFile) return;
            var value = data.sysFile.originalFileName;
            if(typeof value == 'undefined') return;
            td.append("<a href='"+PATH+"/announcement/download/"+data.id+"'>"+value+"</a>");
        }
    });
}

/**
 * 确认发布
 */
function release_ok(){
    //获取所有选中的行
    var selectRows = $grid_announcement.datagrid("getSelections");
    var ids = [];
    $(selectRows).each(function(index,row){
        if(!row.arange) ids.push(row.id);
    });
    if(ids.length == 0){
        $.messager.alert("提示","无需重复发布","warning");
        return;
    }
    var arange = 'my';
    var $state_radio = $(this).parent().prev().find("[type='radio']");
    $state_radio.each(function(index,obj){
        var flag = $(obj).prop("checked");
        if(flag){
            arange = obj.value;
        }
    });
    $.ajax({
        type: 'post',
        url: PATH + '/announcement/release',
        data: {
            ids: ids.join(","),
            arange: arange
        },
        success: function (data) {
            data = $.parseJSON(data);
            var icon = "error";
            if (data.success) {
                icon = "info";
            }
            $.messager.alert("提示", data.message, icon, function () {
                if (data.success) {
                    $dlg_release.dialog("close");
                    $grid_announcement.datagrid('reload');
                }
            });
        }
    });
}

function cz_format(value,row,index){
    return "<a href='javascript:annVeiw(\""+row.id+"\");'>查看</a>";
}
function arange_formatte(value,row,index){
    var html = '<div style="color: red">未发布</div>';
    if(value == 'my'){
        html = '<div style="color: #9ACD32;">已发布 本公司</div>';
    } else if(value == 'all'){
        html = '<div style="color: #9ACD32;">已发布 关联企业</div>';
    }
    return html;
}
function file_formatte(value,row,index){
    if(typeof row.sysFile == 'undefined') return;
    value = row.sysFile.originalFileName;
    if(typeof value == 'undefined') return;
    return "<a href='"+PATH+"/announcement/download/"+row.id+"'>"+value+"</a>";
}