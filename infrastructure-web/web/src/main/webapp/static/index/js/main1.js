var $win_strategy;
var $tb_strategy;
//奖惩
var $main_grid_rp;
var $main_win_rp;
//日历
var $win_event;
var $win_event_grid;
var $event_grid;
//通知公告
var $main_announcement;
var $main_ac_view;
var $main_meeting_view;
//制度
var $main_grid_regime;
//工作台(待审批)
var $main_approved;
var $main_approve_view;
var $main_approve;
var $main_app_option;
var $main_regime_view;
var $man_plan_view;
//请假
var $win_main_afl;
var $main_grid_afl;
var $win_main_afl_view;
//公出
var $win_main_out;
var $main_grid_out;
var $win_main_out_view;

$(function(){
    $tb_strategy = $("#tb_strategy");
    $win_strategy = $("#win_strategy");
    //奖惩
    $main_grid_rp = $("#main_grid_rp");
    $main_win_rp = $("#main_win_rp");
    //日历
    $win_event = $("#win_event");
    $win_event_grid = $("#win_event_grid");
    $event_grid = $("#event_grid");
    //通知公告
    $main_announcement = $("#main_announcement");
    $main_ac_view = $("#main_ac_view");
    $main_meeting_view = $("#main_meeting_view");
    //制度
    $main_grid_regime = $("#main_grid_regime");
    //工作台
    $main_approved = $("#main_approved");
    $main_approve_view = $("#main_approve_view");
    $main_approve = $("#main_approve");
    $main_app_option = $("#main_app_option");
    $main_regime_view = $("#main_regime_view");
    $man_plan_view = $("#man_plan_view");
    //请假
    $win_main_afl = $("#win_main_afl");
    $main_grid_afl = $("#main_grid_afl");
    $win_main_afl_view = $("#win_main_afl_view");
    //公出
    $win_main_out = $("#win_main_out");
    $main_grid_out = $("#main_grid_out");
    $win_main_out_view = $("#win_main_out_view");

    //制度
    $main_grid_regime.datagrid({
        url: PATH + '/regime/load',
        idField: 'id',
        fit: true,
        doSize : true,
        pagination: true,
        border: false,
        fitColumns: true,
        autoRowHeight: false,
        singleSelect: true,
        pageSize : 9,
        pageList : [9],
        columns: [[
            {field: 'content', title: '内容', width: '70%'},
            {field: 'createTime', title: '创建时间', width: '30%'}
        ]],
        onLoadSuccess : function(){
            $main_grid_regime.datagrid("resize");
        },
        onDblClickRow: function (rowIndex, rowData) {
            fn_main_regime_veiw(rowData.id);
        }
    });

    $(".pagination-info").hide();
    $(".pagination-page-list").hide();
    var pagers = $(".datagrid-pager");
    $.each(pagers,function(i,o){
        $(o).find("td").eq(1).hide();
    });
    var tb_strategy = $tb_strategy.panel("header").children().eq(1).children();
    if(strategy != ''){
        tb_strategy.eq(0).hide();
        tb_strategy.eq(1).show();
    }
});

/***************************制度 start*************************/
function regime_tools_query(){
    //获取所有选中的行
    var selectRows = $main_grid_regime.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要查看的数据","warning");
        return;
    }
    if(selectRows.length > 1){
        $.messager.alert("提示","只能选择一条数据进行查看","warning");
        return;
    }
    fn_main_regime_veiw(selectRows[0].id);
}
function fn_main_regime_veiw(id){
    $main_regime_view.window("open");
    $main_regime_view.window("setTitle","查看");
    $main_regime_view.window("center");
    var form = $main_regime_view.find("form");
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
function regime_file_formatte(value,row,index){
    if(typeof row.sysFile == 'undefined') return;
    value = row.sysFile.originalFileName;
    if(typeof value == 'undefined') return;
    return "<a href='"+PATH+"/regime/download/"+row.id+"'>"+value+"</a>";
}
/**************************制度 end**************************/
/**
 * 战略-确定
 */
function fun_ok(){
    var th = $(this);
    var $form = th.parent().prev().children();
    var action = $form.attr("action");
    $form.form("submit",{
        url : action,
        success : function(data){
            data = data.substr(0,data.lastIndexOf("}")+1);
            data = $.parseJSON(data);
            var flag = data.success;
            var icon = "error";
            if(flag){
                icon = "info";
            }
            $.messager.alert("提示",data.message,icon,function(){
                if(flag){
                    $.ajax({
                        type : 'post',
                        url : PATH + '/strategy/findByCid',
                        success : function(data){
                            data = $.parseJSON(data);
                            $("#strategy").html(data.content);
                            var header = $win_strategy.window("header");
                            if(header.children().eq(0).html() == '新增战略'){
                                var tb_strategy = $tb_strategy.panel("header").children().eq(1).children();
                                tb_strategy.eq(0).hide();
                                tb_strategy.eq(1).show();
                            }
                            $win_strategy.window("close");
                        }
                    });
                }
            });
        }
    });
}

function approve_ok(){
    var opinion = $main_app_option.textbox("getValue");
    var apState = $("#ma_state").val();
    var apId = $("#ma_id").val();
    $.ajax({
        type : 'post',
        url : PATH + '/approve/pricess/update',
        data : {
            id : apId,
            apState : apState,
            opinion : opinion
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
                    $main_approve.dialog("close");
                    $main_approve_view.window("close");
                    $main_app_option.textbox("setValue","");
                    $main_approved.datagrid('reload');
                }
            });
        }
    });
}