//获取目标表格
var target_grid;
//获取计划表格
var plan_grid;
//获取目标表单
var target_form;
//获取计划表单
var plan_form;
//获取目标窗口
var target_window;
//获取计划窗口
var plan_window;
$(function(){
    plan_form = $("#planForm");
    plan_window = $("#plan_window");
});

//新增计划按钮点击事件
$("#planAdd").on("click",function(){
    //给表单设置提交路径
    plan_form.attr("action", PATH + "/plan/plan/insert");
    //把窗口打开
    plan_window.window('open');
    //清空表单中的内容
    plan_form.form('clear');
    //给窗口设置标题
    plan_window.window("setTitle","新增工作计划");
    //是窗口绝对居中
    plan_window.window("center");
});