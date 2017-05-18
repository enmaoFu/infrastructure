//表格对象
var grid_customer_feedback;
//表单
var form_customer_feedback;
//请求远程数据时发送的额外参数
var queryParams = {

};
var close_customer_feedback;
var win_customer_feedback;
$(function(){
    //获取表格对象
    grid_customer_feedback = $("#grid_customer_feedback");
    //获取表单数据
    form_customer_feedback= $("#form_customer_feedback");
    //获取窗口
    win_customer_feedback = $("#win_customer_feedback");
    close_customer_feedback = $("#close_customer_feedback");

    /**
     * grid_customer_feedback
     */
    grid_customer_feedback.datagrid({
        url : PATH + '/customerFeedback/load',
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
        pageSize : pageSize,
        pageList : pageList,
        toolbar : '#customer_feedback_toolbar',
        columns : [[
            {field:'ck',checkbox:true,width:$(window).width() * 0.1},
            {field:'content',title:'反馈意见内容',width:$(window).width() * 0.4},
            {field:'createTime',title:'创建时间',width:$(window).width() * 0.1,sortable:true,formatter:function(value,row,index){
                return value.substring(0,value.length-8);
            }},
            {field:'userName',title:'所属人',width:$(window).width() * 0.1},
            {field:'coName',title:'所属公司',width:$(window).width() * 0.2},
            {field: 'id',
                title: '操作',
                width:$(window).width() * 0.1,
                align:'center',
                formatter:function(value,row,index){
                    var str="<a href='javascript:void(0)' onclick='select_customer_feedback(\""+value+"\")' style='text-decoration: none;'><font style='color: green'>查看详情</font></a>";
                    return str;
                }}
        ]],
        onDblClickRow: function(index, row){
            select_customer_feedback(row.id);
        }
    });

    /**
     *
     * 关闭窗口
     */
    close_customer_feedback.on("click",function(){
        win_customer_feedback.window('close', true);
    });

});

//填充表单数据
function fun_edit_customer_feedback(id){
    form_customer_feedback.form("reset");
    $.ajax({
        type : 'post',
        url : PATH + '/customerFeedback/findById',
        data : {
            id : id
        },
        success : function(data){
            data = eval("("+data+")");
            form_customer_feedback.form({
                onLoadSuccess : function(data){
                    if(data != null){
                    }
                }
            });
            form_customer_feedback.form("load",data);
        }
    });
}
/**
 * 查看反馈意见
 */
function select_customer_feedback(id){
    //填充表单数据
    fun_edit_customer_feedback(id);
    //打开窗口
    win_customer_feedback.window({
        title: "查看反馈意见",
        iconCls: 'icon-search',
        closed: false,
        draggable:true,
        height:205
    });
    //将窗口绝对居中
    win_customer_feedback.window("center");
}
/**
 * 搜索
 */
/**
 * 搜索按钮事件
 */
$("#search_customer_feedback_btn").on("click",function(){
    //创建时间：
    var search_startsignDate = $("#search_customer_feedback_startsignDate").datebox("getValue");
    var search_endsignDate = $("#search_customer_feedback_endsignDate").datebox("getValue");
    //需要传人后台的参数 格式必须是{"name":"text"}
    var para = '{"stertsignDate":"'+search_startsignDate+'","endsignDate":"'+search_endsignDate+'"}';
    queryParams={
        stertsignDate: search_startsignDate,
        endsignDate: search_endsignDate
    }
    //转为JSON对象
    para = $.parseJSON(para);
    $.extend(para,queryParams);
    grid_customer_feedback.datagrid("load",para);
});