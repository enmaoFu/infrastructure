var browser = jQuery.browser.mozilla;
var h = $(".login_wrap").css("height");
if(h == '23px' || h == '27px'){
    window.location = window.location;
}
$(function(){
    if(!browser){
        $("#prompt").show();
    }
    //获取cookie信息
    +(function(){
        try{
            var userName="";
            var password="";
            userName=getCookieValue("0c7a74ba-1622-4f5d-b026-8c695988ffd1");
            password=getCookieValue("a4d7_1e7339_4d62a_6b8ac308_1141a");
            var $userName = $("#userName");
            var $password = $("#password");
            $userName.val($.base64.decode(userName));
            $password.val($.base64.decode(password));
        }catch(err){

        }
    })();
});

$(document).on("keyup",function(e){
    if(e.keyCode == 13){
        login();
    }
});
$('#login-button').on("click",function(event){
    login();
});
$("#close_prompt").on("click",function(){
    $("#prompt").hide();
});
function login(){
    $("#error").html("");
    var userName = $("#userName").val();
    var password = $("#password").val();
    var rememberMe = $("#rememberMe").prop("checked");
    if(userName  == null || userName == ''){
        return;
    }
    if(password  == null || password == ''){
        return;
    }
    setcookie("0c7a74ba-1622-4f5d-b026-8c695988ffd1",$.base64.encode(userName));
    if(!!rememberMe){
        setcookie("a4d7_1e7339_4d62a_6b8ac308_1141a", $.base64.encode(password));
    }else {
        clearCookie("a4d7_1e7339_4d62a_6b8ac308_1141a");
    }
    var $form = $('.form');
    $form.submit();
}
//设置cookie
function setcookie(name, value){
    //设置名称为name,值为value的Cookie
    var expdate = new Date();
    expdate.setTime(expdate.getTime() + 7 * 24 * 60 * 60 * 1000);   //时间
    document.cookie = name+"="+value+";expires="+expdate.toGMTString();
    //即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
}
//清除cookie
function clearCookie(name) {
    setcookie(name, "");
}
//获取cookie值
function getCookieValue(name) {
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    //查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);
    //如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1) { //如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length; //cookie值开始的位置
        var end = allcookies.indexOf(";", start); //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1) end = allcookies.length; //如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start, end); //提取cookie的值
        return unescape(value); //对它解码
    }
    else return ""; //搜索失败，返回空字符串
}