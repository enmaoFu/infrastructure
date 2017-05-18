var $win_strategy;
var $tb_strategy;
//奖惩
var $main_grid_rp;
var $main_win_rp;
//日历
var $win_event;
var $win_event_view;
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
var $win_inst_view;
//请假
var $win_main_afl;
var $main_grid_afl;
var $win_main_afl_view;
//公出
var $win_main_out;
var $main_grid_out;
var $win_main_out_view;
var $win_main_afl_out;
var $main_grid_afl_all;
var $main_grid_out_all;

$(function(){
    $tb_strategy = $("#tb_strategy");
    $win_strategy = $("#win_strategy");
    //奖惩
    $main_grid_rp = $("#main_grid_rp");
    $main_win_rp = $("#main_win_rp");
    //日历
    $win_event = $("#win_event");
    $win_event_view = $("#win_event_view");
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
    $win_inst_view = $("#win_inst_view");
    //请假
    $win_main_afl = $("#win_main_afl");
    $main_grid_afl = $("#main_grid_afl");
    $win_main_afl_view = $("#win_main_afl_view");
    //公出
    $win_main_out = $("#win_main_out");
    $main_grid_out = $("#main_grid_out");
    $win_main_out_view = $("#win_main_out_view");
    $win_main_afl_out = $("#win_main_afl_out");
    $main_grid_afl_all = $("#main_grid_afl_all");
    $main_grid_out_all = $("#main_grid_out_all");

    $('#myId').jalendar({
        color: '#00B3E8'
    });
    //获取屏幕分辨率高度
    var sh = screen.height;
    //设置分页属性的时候初始化页面大小
    var pageSize = 7;
    var rp_pageSize = 4;
    //设置分页属性的时候 初始化页面大小选择列表
    var pageList = [7,13];
    var rp_pageList = [4,8];
    switch (sh){
        case 768 :
            break;
        case 1080 :
            pageSize = 13;
            pageList = [13,20];
            rp_pageSize = 7;
            rp_pageList = [7,14];
            break;
    }
    //奖惩
    $main_grid_rp.datagrid({
        url :  PATH + "/hr/reward/loadRepub",
        queryParams : {
        },
        idField: 'id',
        pagination: true,
        height: '100%',
        border: false,
        fitColumns: false,
        autoRowHeight: false,
        singleSelect: true,
        sortName: 'repubTime',
        sortOrder: 'desc',
        pageSize : rp_pageSize,
        pageList : rp_pageList,
        columns: [[
            {field: 'type', title: '奖惩类别',width:'20%',formatter:type_formatte},
            {field: 'rewardObj',width:'25%', title: '奖惩对象'},
            {field: 'company',width:'38%', title: '所属公司'},
            {field: 'repubTime',width:'20%', title: '发布时间'}
        ]],
        onLoadSuccess : function(){
            $main_grid_rp.datagrid("resize");
        },
        onDblClickRow: function (rowIndex, rowData) {
            viewReward(rowData.id);
        }
    });
    //通知公告/会议通知
    $main_announcement.datagrid({
        url: PATH + '/main/annOrMeet',
        idField: 'id',
        pagination: true,
        height: '100%',
        border: false,
        fitColumns: false,
        autoRowHeight: false,
        singleSelect: true,
        pageSize : pageSize,
        pageList : pageList,
        columns: [[
            {field: 'title', title: '标题', width: '40%',formatter:title_formatte},
            {field: 'abbreviation', title: '所属公司', width: '20%'},
            {field: 'aType', title: '类型', width: '20%',formatter:aType_formatte},
            {field: 'releaseTime', title: '发布时间', width: '20%'}
        ]],
        onLoadSuccess : function(){
            $main_announcement.datagrid("resize");
        },
        onDblClickRow: function (rowIndex, rowData) {
            viewAnn(rowData);
        }
    });
    //制度
    $main_grid_regime.datagrid({
        url: PATH + '/regime/load',
        idField: 'id',
        pagination: true,
        height: '100%',
        border: false,
        fitColumns: false,
        autoRowHeight: false,
        singleSelect: true,
        pageSize : pageSize,
        pageList : pageList,
        columns: [[
            {field: 'content', title: '内容', width: '71%','formatter':regime_content},
            {field: 'createTime', title: '创建时间', width: '33%'}
        ]],
        onLoadSuccess : function(){
            $main_grid_regime.datagrid("resize");
        },
        onDblClickRow: function (rowIndex, rowData) {
            fn_main_regime_veiw(rowData.id);
        }
    });

    //工作台
    $main_approved.datagrid({
        url: PATH + '/workbench',
        idField: 'id',
        height: '100%',
        pagination: true,
        border: false,
        fitColumns: true,
        autoRowHeight: false,
        singleSelect: true,
        sortName: 'createDate',
        sortOrder: 'desc',
        pageSize : pageSize,
        pageList : pageList,
        columns: [[
            {field: 'content', title: '标题/请假人', width: '64%','formatter':approved_content},
            {field: 'state', title: '状态', width: '20%','formatter':state_format},
            {field: 'mark', title: '类型', width: '20%'}
        ]],
        onLoadSuccess : function(){
            $main_approved.datagrid("resize");
        },
        onDblClickRow: function (rowIndex, rowData) {
            fn_main_approve_veiw(rowData);
        }
    });
    $(".pagination-info").hide();
    //$(".pagination-page-list").hide();
    var pagers = $(".datagrid-pager");
    $.each(pagers,function(i,o){
        $(o).find("td").eq(1).hide();
    });
    var tb_strategy = $tb_strategy.children();
    var strategy = $("#strategy").text();
    if(tb_strategy.length > 0 && strategy.trim().length > 0){
        tb_strategy.eq(0).hide();
        tb_strategy.eq(1).show();
    }else {
        tb_strategy.eq(0).show();
        tb_strategy.eq(1).hide();
    }
    $("#main_cont_mb").panel("resize");
});

/**
 * 战略-新增
 */
function strategy_tools_add(){
    $win_strategy.show();
    var $form = $win_strategy.find("form");
    $form.attr("action",PATH+"/strategy/insert");
    $form.form("clear");
    $win_strategy.window("open");
    $win_strategy.window("setTitle","新增战略");
    $win_strategy.window("center");
}
/**
 * 战略-修改
 * @param th
 */
function strategy_tools_edit(th){
    $win_strategy.show();
    var panel = $(th).parent().parent().parent();
    var value = panel.children().eq(1).html();
    $win_strategy.window("open");
    $win_strategy.window("setTitle","编辑战略");
    $win_strategy.window("center");
    var $form = $win_strategy.find("form");
    $form.attr("action",PATH+"/strategy/update");
    $form.form("clear");
    $.ajax({
        type : 'post',
        url : PATH + '/strategy/findByCid',
        success : function(data){
            data = $.parseJSON(data);
            var content = data.content;
            while (content.indexOf("br") != -1){
                content = content.replace("<br>","\r\n");
            }
            data.content = content;
            $form.form("load",data);
        }
    });
}

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
                            var tb_strategy = $tb_strategy.children();
                            tb_strategy.eq(0).hide();
                            tb_strategy.eq(1).show();
                            /*var header = $win_strategy.window("header");
                            if(header.children().eq(0).html() == '新增战略'){
                                var tb_strategy = $tb_strategy.panel("header").children().eq(1).children();
                                tb_strategy.eq(0).hide();
                                tb_strategy.eq(1).show();
                            }*/
                            $win_strategy.window("close");
                        }
                    });
                }
            });
        }
    });
}
/***************************奖惩 start****************************/
function type_formatte(value, row){
    if(value == '0'){
        return "奖励";
    }else{
        return "惩罚";
    }
}
function rp_tools_query(){
    var row = $main_grid_rp.datagrid("getSelected");
    if(row == null){
        $.messager.alert("提示","请选择数据","warning");
        return;
    }
    viewReward(row.id);
}
/**
 * 详情
 * @param id
 */
function viewReward(id){
    $main_win_rp.show();
    $main_win_rp.window("open");
    $main_win_rp.window("setTitle","奖惩查看");
    $main_win_rp.window("center");
    var $form = $main_win_rp.find("form");
    $.ajax({
        type : 'post',
        url : PATH + '/hr/reward/findById',
        data : {id : id},
        success : function(data){
            data = $.parseJSON(data);
            $form.form("load",data);
        }
    });
}
/***************************奖惩 end****************************/
/***************************日历 start****************************/
//新建记事
function add_event_fun(){
    $win_event.show();
    $win_event.window("open");
    $win_event.window("setTitle","新建记事");
    $win_event.window("center");
    var $form = $win_event.find("form");
    $form.form("reset");
    $form.attr("action",PATH + "/event/insert");
}
//记事列表
function select_event_fun(){
    var day = $("#day").val();
    $win_event_grid.window("open");
    $win_event_grid.window("setTitle","事件列表");
    $win_event_grid.window("center");
    $event_grid.datagrid({
        url: PATH + '/event/load',
        queryParams:{
            day : day
        },
        idField: 'id',
        height: '100%',
        border: false,
        fitColumns: true,
        autoRowHeight: false,
        singleSelect: true,
        columns: [[
            {field: 'dtime', title: '时间', width: '20%'},
            {field: 'title', title: '事件内容', width: '70%'},
            {field: 'cz', title: '操作', width: '10%','formatter':event_formatte}
        ]],
        onLoadSuccess:function(data){
            if(data.total == 0){
                $("#myId").find(".days").find("[data-date='"+day+"']").find("i").remove();
            }
        },
        onDblClickRow: function (rowIndex, rowData) {
            win_event_veiw(rowData);
        }
    });
}
function event_formatte(value, row){
    return "<a href='javascript:event_delete(\""+row.id+"\");'><img src='"+PATH+"/static/easyui/css/icons/clear.png'></a>"
}
var div = function (e, classN) {
    return $(document.createElement(e)).addClass(classN);
};
/**
 * 查看
 */
function win_event_veiw(data){
    $win_event_view.show();
    $win_event_view.window("open");
    $win_event_view.window("setTitle","查看");
    $win_event_view.window("center");
    var $form = $win_event_view.find("form");
    $form.form("load",data);
}
//确定
$("#ok_event").on("click",function(){
    var $this = $(this);
    $this.linkbutton('disable');
    var $form = $win_event.find("form");
    var day = $form.find("#day").val();
    var action = $form.attr("action");
    $form.form('submit',{
        url : action,
        success : function(data){
            $this.linkbutton('enable');
            data = data.substr(0,data.lastIndexOf("}")+1);
            var dataJson = $.parseJSON(data);
            var flag = dataJson.success;
            var icon = "error";
            if(flag){
                icon = "info";
            }
            $.messager.alert("提示",dataJson.message,icon,function(){
                if(dataJson.success){
                    $("#myId").find(".days").find("[data-date='"+day+"']").prepend(div('i',''));
                    $form.find("[textboxname='title']").textbox("setValue","");
                    $win_event.window("close");
                }
            });
        }
    });
});
function event_delete(id){
    $.ajax({
        type : 'post',
        url : PATH + '/event/delete',
        data : {
            id : id
        },
        success : function(data){
            data = $.parseJSON(data);
            if(data.success){
                $event_grid.datagrid("reload");
            }
        }
    });
}
/***************************日历 end****************************/
/***************************通知公告 start****************************/
function ann_tools_query(){
    //获取所有选中的行
    var selectRows = $main_announcement.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择需要查看的数据","warning");
        return;
    }
    if(selectRows.length > 1){
        $.messager.alert("提示","只能选择一条数据进行查看","warning");
        return;
    }
    viewAnn(selectRows[0]);
}
function viewAnn(row){
    var ac_view;
    var url;
    if(row.aType == '1'){
        ac_view = $main_ac_view;
        url = PATH + '/announcement/findById';
        ac_view.window("setTitle","通知公告-查看");
    }else {
        ac_view = $main_meeting_view;
        url = PATH + '/daily/meeting/findById';
        ac_view.window("setTitle","会议通知-查看");
        if(row.flg == 'N'){
            updateLookFlg(row.msId);
        }
    }
    ac_view.show();
    ac_view.window("open");
    ac_view.window("center");
    var $form = ac_view.find("form");
    $.ajax({
        type : 'post',
        url : url,
        data : {id : row.id},
        success : function(data){
            if(!row.isView){
                $.ajax({
                    type : 'post',
                    url : PATH + '/view/insert',
                    data : {viewId : row.id},
                    success : function(data){
                        data = $.parseJSON(data);
                        if(data.success){
                            $main_announcement.datagrid("reload");
                        }
                    }
                });
            }
            data = $.parseJSON(data);
            $form.form("load",data);
            var td = $form.find("[mark='fileName']");
            td.empty();
            if(!data.sysFile) return;
            var value = data.sysFile.originalFileName;
            if(!value) return;
            td.append("<a href='"+PATH+"/announcement/download/"+data.id+"'>"+value+"</a>");
        }
    });
}

/**
 * 更改会议通知查阅状态
 * @param sid
 */
function updateLookFlg(id){
    $.ajax({
        type : 'post',
        url : PATH + '/daily/meeting/updateLook',
        data : {
            id : id
        },
        success : function(data){
            meeting_grid.datagrid('reload');
        }
    });
}

function title_formatte(value,row,index){
    value = value.substr(0,11);
    var html = '<span>'+value+'</span>';
    if(!row.isView){
        var path = PATH + "/static/common/images/new.png";
        html += "<img src='"+path+"'>";
    }
    return "<a href='javascript:void(0);' style='color: black;text-decoration: underline;' onclick='getById(\""+row.id+"\",\""+row.aType+"\");'>"+html+"</a>";
}
function aType_formatte(value,row,index){
    var html = "通知公告";
    if(value == '2'){
        html = "会议通知";
    }
    return html;
}
function getById(id,aType){
    var url = PATH;
    var aTypeObj = {};
    if(aType == '1'){
        //通知公告
        url += "/announcement/findById";
        aTypeObj = {aType:'1'};
    }
    if(aType == '2'){
        //会议通知
        url += "/daily/meeting/findById";
        aTypeObj = {aType:'2'};
    }
    $.ajax({
        type : 'post',
        url : url,
        data : {
            id : id
        },
        success : function(data){
            data = $.parseJSON(data);
            $.extend(data,aTypeObj);
            viewAnn(data);
        }
    });
}
/***************************通知公告 end****************************/
/***************************制度 start*************************/
function regime_content(value,row,index){
    var html = "<a href='javascript:void(0);' style='color: black;text-decoration: underline;' onclick='fn_main_regime_veiw(\""+row.id+"\");'>"+value+"</a>";
    return html;
}
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
    $main_regime_view.show();
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

/****************工作台(待审批) start*********************/
function approved_content(value,row,index){
    var html = "<a href='javascript:void(0);' style='color: black;text-decoration: underline;' onclick='appFindById(\""+index+"\");'>"+value+"</a>";
    return html;
}
function appFindById(index){
    $main_approved.datagrid("selectRow",index);
    var row = $main_approved.datagrid("getSelected");
    fn_main_approve_veiw(row);
}
function approved_query(){
    var selectRows = $main_approved.datagrid("getSelections");
    if(selectRows.length == 0){
        $.messager.alert("提示","请选择数据","warning");
        return;
    }
    fn_main_approve_veiw(selectRows[0]);
}
function fn_main_approve_veiw(row){
    if(row.mark == '待审批' || row.mark == '我的申请'){
        $.ajax({
            type : 'post',
            url : PATH + '/approve/findById',
            data : {
                id : row.id
            },
            success : function(data){
                data = $.parseJSON(data);
                $main_approve_view.show();
                $main_approve_view.window("open");
                $main_approve_view.window("setTitle","待审批-详情");
                $main_approve_view.window("center");
                var form = $main_approve_view.find("form");
                var check_ok = $main_approve_view.find("[id='check_ok']");
                var check_reset = $main_approve_view.find("[id='check_reset']");
                if('P' != row.state || row.mark == '我的申请') {
                    check_ok.hide();
                    check_reset.hide();
                }else {
                    check_ok.show();
                    check_reset.show();
                }
                check_ok.on("click",function(){
                    operating(row.apId,"Y");
                });
                check_reset.on("click",function(){
                    operating(row.apId,"N");
                });
                form.form("load",data);
                var sysFile = data.sysFile;
                if(!!sysFile){
                    var td = form.find("[mark='fileName']");
                    td.empty();
                    td.append("<a href='"+PATH+"/approve/download/"+data.id+"'>"+sysFile.originalFileName+"</a>");
                }
            }
        });
    }
    if(row.mark == '个人计划') {
        $.ajax({
            type : 'post',
            url : PATH + '/personDetailPlan/findById',
            data : {
                id : row.id
            },
            success : function(data){
                data = $.parseJSON(data);
                $man_plan_view.show();
                $man_plan_view.window("open");
                $man_plan_view.window("setTitle","个人计划-详情");
                $man_plan_view.window("center");
                var form = $man_plan_view.find("form");
                form.form("load",data);
            }
        });
    }
    if(row.mark == '请假审批' || row.mark == '我的请假'){
        $.ajax({
            type : 'post',
            url : PATH + '/afl/findById',
            data : {
                id : row.id
            },
            success : function(data){
                data = $.parseJSON(data);
                if(!!data.success){
                    $.messager.alert("提示",data.message,'error');
                    return;
                }
                $win_main_afl_view.show();
                $win_main_afl_view.find(".easyui-layout").layout("panel","south").show();
                $win_main_afl_view.window("open");
                $win_main_afl_view.window("setTitle","请假-审批");
                $win_main_afl_view.window("center");
                var form = $win_main_afl_view.find("form");
                var $ty = $win_main_afl_view.find("[mark='ty']");
                var $bh = $win_main_afl_view.find("[mark='bh']");
                if('P' != row.state || row.mark == '我的请假') {
                    $ty.hide();
                    $bh.hide();
                }else {
                    $ty.show();
                    $bh.show();
                }
                $ty.linkbutton({
                    onClick : function(){
                        operating(row.aocId,"Y");
                   }
                });
                $bh.linkbutton({
                    onClick : function(){
                        operating(row.aocId,"N");
                    }
                });
                form.form("load",data);
                //通过/驳回
                function operating(id,state){
                    var $dlg_afl = $("#dlg_afl");
                    var val = state == 'Y' ? '同意' : "驳回";
                    $dlg_afl.show();
                    $dlg_afl.dialog("open");
                    $dlg_afl.dialog("setTitle",val);
                    $dlg_afl.dialog("center");
                    var check_user_id = $dlg_afl.find("#check_user_id");
                    check_user_id.combobox({
                        width : 155,
                        valueField:'id',
                        textField:'text',
                        method : 'post',
                        url : PATH + '/system/sysuser/findByCompanyId',
                        prompt:'审批人',
                        icons : [
                            {
                                iconCls:'icon-clear',
                                handler : function(){
                                    $(this).parent().parent().prev().combobox("clear");
                                }
                            }
                        ]
                    });
                    var zf_check = $dlg_afl.find("tr");
                    if(state == 'Y'){
                        zf_check.eq(1).show();
                        zf_check.eq(2).show();
                    }else {
                        zf_check.eq(1).hide();
                        zf_check.eq(2).hide();
                    }
                    $dlg_afl.find("#afl_id").val(id);
                    $dlg_afl.find("#afl_state").val(state);
                }
            }
        });
    }
    if(row.mark == '收到指令' || row.mark == '发送指令'){
        $win_inst_view.show();
        $win_inst_view.window("open");
        $win_inst_view.window("setTitle","指令");
        $win_inst_view.window("center");
        var form = $win_inst_view.find("form");
        form.form("load",row);
        var td = form.find("[mark='fileName']");
        td.empty();
        if(!row.fileId) return;
        var value = row.fileName;
        if(typeof value == 'undefined') return;
        td.append("<a href='"+PATH+"/instructions/download/"+row.fileId+"'>"+value+"</a>");
    }
}
//审批确认
function fun_alf_ok(){
    var $dlg_afl = $(this).parent().prev();
    var $option = $dlg_afl.find("#afl_opinion");
    var opinion = $option.textbox("getValue");
    var state = $dlg_afl.find("#afl_state").val();
    var aocId = $dlg_afl.find("#afl_id").val();
    var userId = $dlg_afl.find("#check_user_id").combobox("getValue");
    var sms = $dlg_afl.find("#sms").prop("checked");
    $.ajax({
        type : 'post',
        url : PATH + '/aflOutCheck/update',
        data : {
            id : aocId,
            state : state,
            opinion : opinion,
            userId : userId,
            sms : sms
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
                    $option.textbox("setValue","");
                    $dlg_afl.dialog("close");
                    $win_main_afl_view.window("close");
                    $main_approved.datagrid('reload');
                }
            });
        }
    });
}
function operating(id,state){
    var val = state == 'Y' ? '同意' : "驳回";
    $main_approve.show();
    $main_approve.dialog("open");
    $main_approve.dialog("setTitle",val);
    $main_approve.dialog("center");
    $("#ma_state").val(state);
    $("#ma_id").val(id);
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

function file_formatte(value,row,index){
    if(typeof value == 'undefined' || value == null) return;
    return "<a href='"+PATH+"/approve/download/"+row.id+"'>"+value+"</a>";
}
/****************工作台(待审批) end*********************/
/****************请假/公出 start*********************/
$("#main_start_hour_view,#main_end_hour_view").combobox({
    valueField:'id',
    textField:'text',
    value : 00,
    editable:false,
    data : [
        {id:'00',text:'00'}, {id:'01',text:'01'},{id:'02',text:'02'},
        {id:'03',text:'03'}, {id:'04',text:'04'},{id:'05',text:'05'},
        {id:'06',text:'06'}, {id:'07',text:'07'}, {id:'08',text:'08'},
        {id:'09',text:'09'}, {id:'10',text:'10'}, {id:'11',text:'11'},
        {id:'12',text:'12'}, {id:'13',text:'13'}, {id:'14',text:'14'},
        {id:'15',text:'15'}, {id:'16',text:'16'}, {id:'17',text:'17'},
        {id:'18',text:'18'}, {id:'19',text:'19'}, {id:'20',text:'20'},
        {id:'21',text:'21'}, {id:'22',text:'22'}, {id:'23',text:'23'}
    ]
});
$("#main_start_min_view,#main_end_min_view").combobox({
    valueField:'id',
    textField:'text',
    value : 00,
    editable:false,
    data : [
        {id:'00',text:'00'}, {id:'05',text:'05'}, {id:'10',text:'10'},
        {id:'15',text:'15'}, {id:'20',text:'20'}, {id:'25',text:'25'},
        {id:'30',text:'30'}, {id:'35',text:'35'}, {id:'40',text:'40'},
        {id:'45',text:'45'}, {id:'50',text:'50'}, {id:'55',text:'55'}
    ]
});
function queryAfl(){
    $win_main_afl.show();
    $win_main_afl.window("open");
    $win_main_afl.window("setTitle","请假列表");
    $win_main_afl.window("center");
    $main_grid_afl.datagrid({
        url : PATH + '/afl/queryAfl',
        fit: true,
        idField:'id',
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'applicant',title:'申请人',resizable:true,width:$(window).width()*0.1},
            {field:'typeVal',title:'类型',resizable:true,width:$(window).width()*0.1},
            {field:'subject',title:'请假事由',resizable:true,width:$(window).width()*0.25},
            {field:'startTime',title:'请假时间',sortable:true,width:$(window).width()*0.3,'formatter':time_fmt},
            {field:'count',title:'合计',resizable:true,width:$(window).width()*0.1,'formatter':count_fmt}
        ]],
        onDblClickRow : function(rowIndex, rowData){
            min_afl_veiw(rowData.id);
        }
    });
}
//查看
function min_afl_veiw(id){
    $win_main_afl_view.show();
    $win_main_afl_view.find(".easyui-layout").layout("panel","south").hide();
    $win_main_afl_view.window("open");
    $win_main_afl_view.window("setTitle","查看");
    $win_main_afl_view.window("center");
    var $form = $win_main_afl_view.find("form");
    $.ajax({
        type : 'post',
        url : PATH + "/afl/findById",
        data : {
            id : id
        },
        success : function(data){
            data = $.parseJSON(data);
            $form.form("load",data);
        }
    });
}
function time_fmt(value,row,index){
    return value+" 至 "+row.endTime;
}
function count_fmt(value,row,index){
    return value+" 天";
}
//公出
function queryOut(){
    $win_main_out.window("open");
    $win_main_out.window("setTitle","公出列表");
    $win_main_out.window("center");
    $main_grid_out.datagrid({
        url : PATH + '/out/queryOut',
        fit: true,
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'applicant',title:'申请人',resizable:true,width:$(window).width()*0.05},
            {field:'subject',title:'公出事由',resizable:true,width:$(window).width()*0.2},
            {field:'startTime',title:'公出时间',sortable:true,width:$(window).width()*0.2,'formatter':time_fmt},
            {field:'count',title:'合计',resizable:true,width:$(window).width()*0.1,'formatter':count_fmt},
            {field:'deptName',title:'部门',resizable:true,width:$(window).width()*0.1}
        ]],
        onDblClickRow : function(rowIndex, rowData){
            main_out_veiw(rowData.id);
        }
    });
}
//查看
function main_out_veiw(id){
    $win_main_out_view.show();
    $win_main_out_view.window("open");
    $win_main_out_view.window("setTitle","查看");
    $win_main_out_view.window("center");
    var $form = $win_main_out_view.find("form");
    $.ajax({
        type : 'post',
        url : PATH + "/out/findById",
        data : {
            id : id
        },
        success : function(data){
            data = $.parseJSON(data);
            $form.form("load",data);
        }
    });
}

function query_zx(){
    var $win_main_zx = $("#win_main_zx");
    $win_main_zx.window("open");
    $win_main_zx.window("setTitle","在线人员");
    $win_main_zx.window("center");
}

/**
 * 集团公司请假详情
 * @param th
 */
function leave_query(th){
    $win_main_afl_out.window("open");
    $win_main_afl_out.window("setTitle","关联企业请假公出详情");
    $win_main_afl_out.window("center");
    $main_grid_afl_all.datagrid({
        url : PATH + '/afl/queryAflAll',
        fit: true,
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'companyName',title:'所属公司',resizable:true,width:$(window).width()*0.2},
            {field:'deptName',title:'部门',resizable:true,width:$(window).width()*0.1},
            {field:'applicant',title:'申请人',resizable:true,width:$(window).width()*0.1},
            {field:'typeVal',title:'类型',resizable:true,width:$(window).width()*0.07},
            {field:'subject',title:'请假事由',resizable:true,width:$(window).width()*0.2},
            {field:'startTime',title:'请假时间',sortable:true,width:$(window).width()*0.23,'formatter':time_fmt},
            {field:'count',title:'合计',resizable:true,width:$(window).width()*0.1,'formatter':count_fmt}
        ]],
        onDblClickRow : function(rowIndex, rowData){
            min_afl_veiw(rowData.id);
        }
    });
    $main_grid_out_all.datagrid({
        url : PATH + '/out/queryOutAll',
        fit: true,
        idField:'id',
        pagination : true,
        height : '100%',
        border : false,
        fitColumns : true,
        autoRowHeight : false,
        rownumbers : true,
        ctrlSelect : true,
        columns : [[
            {field:'ck',checkbox:true},
            {field:'id',title:'ID',hidden:true},
            {field:'companyName',title:'所属公司',resizable:true,width:$(window).width()*0.22},
            {field:'deptName',title:'部门',resizable:true,width:$(window).width()*0.1},
            {field:'applicant',title:'申请人',resizable:true,width:$(window).width()*0.1},
            {field:'subject',title:'公出事由',resizable:true,width:$(window).width()*0.28},
            {field:'startTime',title:'公出时间',sortable:true,width:$(window).width()*0.2,'formatter':time_fmt},
            {field:'count',title:'合计',resizable:true,width:$(window).width()*0.1,'formatter':count_fmt}
        ]],
        onDblClickRow : function(rowIndex, rowData){
            main_out_veiw(rowData.id);
        }
    });
}
/****************请假/公出 end*********************/
function state_format(value,row,index){
    var html;
    if('Y' == value){
        html = "<div>已通过</div>";
    }
    if('N' == value){
        html = "<div>已驳回</div>";
    }
    if('P' == value){
        html = "<div>待审批</div>";
    }
    if(row.mark == '收到指令' && value == 'N'){
        html = "<div>待处理</div>";
    }
    if(row.mark == '发送指令' && value == 'N'){
        html = "<div>进展中</div>";
    }
    return html;
}