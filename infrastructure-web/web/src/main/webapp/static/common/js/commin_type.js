var $grid_type;
var $win_type;
$(function(){
    $grid_type = $("#grid_type");
    $win_type = $("#win_type");
    $.extend($.fn.datagrid.methods, {
        editCell: function(jq,param){
            return jq.each(function(){
                var opts = $(this).datagrid('options');
                var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field){
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        }
    });
});

function add_type(th){
    $win_type.window("open");
    $win_type.window("setTitle","类型管理");
    $win_type.window("center");
    var s_mark = $(th).attr("lang");
    $win_type.find("[id='s_mark']").val(s_mark);
    $grid_type.datagrid({
        url : PATH + '/select/load',
        queryParams : {
            s_mark : s_mark
        },
        idField:'id',
        pagination : true,
        height : '100%',
        border : true,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        singleSelect : true,
        onClickCell: onClickCell,
        columns : [[
            {field:'id',title:'ID',hidden:true},
            {field:'s_val',title:'类型名称',width:$(window).width()*0.3,editor:'text'},
            {field:'s_remark',title:'描述',width:$(window).width()*0.4,editor:'text'},
            {field:'cz',title:'操作',width:$(window).width()*0.3,
                formatter: function(value,row,index){
                    var id = row.id;
                    var html = "<a href='javascript:del(\""+id+"\");'><img src='"+PATH+"/static/easyui/css/icons/cancel.png'></a>";
                    return html;
                }
            }
        ]]
    });
}

/**
 * 保存
 */
$("#type_save").on("click",function(){
    var $form_type = $(this).parent().parent();
    var $s_mark = $form_type.find("[id='s_mark']");
    var s_mark = $s_mark.val();
    //验证是否通过
    var ival = $form_type.form("validate");
    if(ival){
        //验证通过 请求后台
        var s_val = $form_type.find("[name='s_val']").val();
        var s_remark = $form_type.find("[name='s_remark']").val();
        $.ajax({
            type : 'post',
            url : PATH + '/select/insert',
            data : {
                s_val : s_val,
                s_remark : s_remark,
                s_mark : s_mark
            },
            success : function(data){
                var dataJson = $.parseJSON(data);
                var flag = dataJson.success;
                var icon = "error";
                if(flag){
                    icon = "info";
                }
                $.messager.alert("提示",dataJson.message,icon,function(){
                    if(dataJson.success){
                        $form_type.form("clear");
                        $s_mark.val(s_mark);
                        $grid_type.datagrid('reload');
                    }
                });
            }
        });
    }
});

function del(id){
    $.messager.confirm("提示","确认删除记录？",function(r){
        if(!r){
            return;
        }
        $.ajax({
            type : 'post',
            url : PATH + '/select/delete',
            data : {
                id : id
            },
            success : function(data){
                var icon = "error";
                if(data.success){
                    icon = "info";
                }
                $.messager.alert("提示",data.message,icon,function(){
                    if(data.success){
                        $grid_type.datagrid('reload');
                    }
                });
            }
        });
    });
}

var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($grid_type.datagrid('validateRow', editIndex)){
        $grid_type.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (endEditing()){
        $grid_type.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
    var getEditor = $grid_type.datagrid('getEditor', {index:index,field:field});
    if(getEditor == null){
        return;
    }
    var input = getEditor.target;
    $(input).on("blur",function(){
        var filed = getEditor.field;
        var value = input.val();
        var id = $grid_type.datagrid("getSelected").id;
        var param = "{"+filed+":'"+value+"',id:'"+id+"'}";
        param = eval("("+param+")");
        $.ajax({
            type : 'post',
            url : PATH + '/select/update',
            data : param,
            success : function(msg){
                var dataJson = $.parseJSON(msg);
                var flag = dataJson.success;
                var icon = "error";
                if(flag){
                    icon = "info";
                }
                if(dataJson.success){
                    $grid_type.datagrid('reload');
                }
            }
        });
    });
}

function typeClose(){
    var s_mark = $(this).find("[id='s_mark']").val();
    $("input[s_mark='"+s_mark+"']").combobox("reload");
}