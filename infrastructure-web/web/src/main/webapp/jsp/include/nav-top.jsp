<%--
  顶部导航页面 (nav-top.jsp)
  Date: 2016/1/10
  Time: 13:12
  所有主体展示页面均可导入 静态包含
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/taglib.jsp" %>
<div style="width: 100%;width:100%;">
    <div style="margin-left: 10px;float: left;height: 17px;line-height: 17px;padding-left: 2px;padding-right: 2px;padding-top: 10px;">
        <span style="font-size: 25px;color: white;">INFRASTRUCTURE</span>
    </div>
    <span style="position: absolute;right: 10px;top: 5px;">
        <div style="margin-right: 10px;border: 1px solid yellow;float: left;height: 17px;line-height: 17px;padding-left: 2px;padding-right: 2px;">
            <a href="javascript:void(0);" id="feedback" style="color: yellow;">
                客户反馈
            </a>
        </div>
        <span style="color: #fff">欢迎您：</span>
        <span style="color:yellow;margin-right: 10px;">${userinfo_session.username}</span>
        <a href="javascript:void(0);" id="user_config" style="color: #fff;background: url('${ctx}/static/common/images/setting.png') no-repeat left center; padding-left: 18px; text-decoration:none;">
            用户设置
        </a>
        <a href="${ctx}/logout" id="logout" style="color: #fff;background: url('${ctx}/static/common/images/out.png') no-repeat left center; padding-left: 18px; text-decoration:none; margin-left: 5px;">
            退出登录
        </a>
        <br />
        <span style="margin-left: 65%;"><span style="color: #fff;">当前在线人数：</span><a href="javascript:query_zx(0);" style="text-decoration:none;color: yellow;">${zx_session}</a> <span style="color: #fff;">人</span></span>
    </span>
    <div style="position:absolute;bottom:0px;right:0px;width: 100%;font-size: 12px; color: yellow">
        <c:if test="${names != ''}">
            <marquee scrollamount="5">${names}</marquee>
        </c:if>
    </div>
</div>

<div id="win_user_fonfig" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,maximizable:false,minimizable:false,iconCls:'icon-save'" style="width:440px;height:350px;" hidden>
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_user_fonfig" action="${ctx}/system/sysuser/update" method="post">
            <div data-options="region:'center',border:false" align="center">
                <input type="hidden" name="id">
                <table cellpadding="2">
                    <tr>
                        <td>登录名:</td>
                        <td colspan="2">
                            <input class="easyui-textbox" name="login" data-options="required:true,width:200" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td colspan="2"><input class="easyui-textbox" type="password" id="pwd" name="password" data-options="required:true,width:200" /></td>
                    </tr>
                    <tr>
                        <td>重复密码:</td>
                        <td colspan="2"><input class="easyui-textbox" type="password" id="rpwd" name="password" data-options="required:true,width:200" /></td>
                    </tr>
                    <tr>
                        <td>姓名:</td>
                        <td colspan="2"><input class="easyui-textbox" name="username" data-options="required:true,width:200" /></td>
                    </tr>
                    <tr>
                        <td>性别:</td>
                        <td colspan="2">
                            <div class="easyui-radio" id="radio">
                                <label><input type="radio" name="sex" value="male" checked /> 男</label>&nbsp;&nbsp;
                                <label><input type="radio" name="sex" value="female" /> 女</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>年龄:</td>
                        <td colspan="2"><input class="easyui-textbox" data-options="width:200" name="age" /></td>
                    </tr>
                    <tr>
                        <td>生日:</td>
                        <td colspan="2"><input class="easyui-datebox" data-options="width:200,panelHeight:220" name="birthday" /></td>
                    </tr>
                    <tr>
                        <td>手机号码:</td>
                        <td colspan="2"><input class="easyui-textbox" data-options="required:true,width:200" name="phone" /></td>
                    </tr>
                    <tr>
                        <td>邮箱:</td>
                        <td colspan="2"><input class="easyui-textbox" data-options="validType:'email',width:200" data-options="width:200" name="email" /></td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_user_config">确定</a>
            </div>
        </form>
    </div>
</div>
<div id="win_top_customer_feedback" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:555px;height:210px;padding:5px;" hidden>
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_top_customer_feedback" method="post" >
            <div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
                <input type="hidden" id="id" name="id">
                <table cellpadding="5">
                    <tr>
                        <td style="vertical-align: top;">反馈意见:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" type="text" name="content" data-options="required:true,multiline:true,width:420,height:100" maxlength="1000"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false,height:35" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="ok_top_customer_feedback">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-reload'" id="reset_top_customer_feedback">重置</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var $win_user_fonfig = $("#win_user_fonfig");
    var $form_user_fonfig = $("#form_user_fonfig");
    var win_top_customer_feedback = $("#win_top_customer_feedback");
    var form_top_customer_feedback = $("#form_top_customer_feedback");
    $(function(){
        $win_user_fonfig.show();
        win_top_customer_feedback.show();
        $win_user_fonfig.panel('refresh');
    });
    /***
     * 意见反馈
     */
    $("#feedback").on("click",function(){
        //设置请求路径
        form_top_customer_feedback.attr("action",PATH + "/customerFeedback/insert");
        //打开窗口
        win_top_customer_feedback.window({
            title: "意见反馈",
            iconCls: 'icon-add',
            closed: false,
            draggable:true,
            height:205
        });
        form_top_customer_feedback.form("reset");
        //将窗口绝对居中
        win_top_customer_feedback.window("center");
    });
    /**
     * 确认按钮点击事件
     */
    $("#ok_top_customer_feedback").on("click",function(){
        var $this = $(this);
        form_top_customer_feedback = $(this).parent().parent().parent();
        //验证是否通过
        var ival = form_top_customer_feedback.form("validate");
        if(ival){
            $this.linkbutton('disable');
            $this.linkbutton({
                iconCls: 'icon-loding'
            });
            var action = form_top_customer_feedback.attr("action");
            //验证通过 请求后台
            form_top_customer_feedback.form('submit',{
                url : action,
                success : function(data){
                    $this.linkbutton('enable');
                    $this.linkbutton({
                        iconCls: 'icon-ok'
                    });
                    var dataJson = $.parseJSON(data);
                    var flag = dataJson.success;
                    var icon = "error";
                    if(flag){
                        icon = "info";
                    }
                    $.messager.alert("提示",dataJson.message,icon,function(){
                        if(dataJson.success){
                            form_top_customer_feedback.form("clear");
                            win_top_customer_feedback.window("close");
                        }
                    });
                }
            });
        }
    });
    /**
     * 重置按钮
     */
    $("#reset_top_customer_feedback").on("click",function(){
        form_top_customer_feedback.form("reset");
    });

    $("#user_config").on("click",function(){
        $form_user_fonfig.form("reset");
        //打开窗口
        $win_user_fonfig.window('open');
        $win_user_fonfig.window("setTitle","用户设置");
        //将窗口绝对居中
        $win_user_fonfig.window("center");
        var data = {
            id : '${userinfo_session.id}',
            login : '${userinfo_session.login}',
            username : '${userinfo_session.username}',
            sex : '${userinfo_session.sex}',
            age : '${userinfo_session.age}',
            birthday : '${userinfo_session.birthday}',
            phone : '${userinfo_session.phone}',
            email : '${userinfo_session.email}'
        };
        $form_user_fonfig.form("load",data);
    });

    $("#ok_user_config").linkbutton({
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
                                $win_user_fonfig.window("close");
                                window.location.href = PATH + "/logout";
                            }
                        });
                    }
                });
            }
        }
    });
</script>