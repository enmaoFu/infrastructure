menus = $.parseJSON(menus);
var lis = "";
for(var i = 0;i < menus.length;i++){
    var menu = menus[i];
    var id = menu.id;
    var pId = typeof menu.parentId == 'undefined' ? 0 : menu.parentId;
    if(pId == 0){
        for(var k = 0;k < menus.length;k++){
            //一级
            var mk = menus[k];
            var id_1 = mk.id;
            if(id == mk.parentId){
                //一级菜单
                var lis_2 = "";
                for(var j = 0;j < menus.length;j++){
                    var mj = menus[j];
                    var id_2 = mj.id;
                    if(id_1 == mj.parentId){
                        //二级菜单
                        var lis_3 = "";
                        for(var p = 0;p < menus.length;p++){
                            //三级菜单
                            var mp = menus[p];
                            if(id_2 == mp.parentId){
                                lis_3 += "<li><a id='"+mp.id+"' url='"+mp.url+"' href='javascript:void(0);' onclick='tab_open(this);'><i class='fa fa-file-o'></i><t>"+mp.name+"</t></a></li>";
                            }
                        }
                        if(lis_3 != ""){
                            lis_2 += "<li><a href='javascript:void(0);'><img src='"+PATH+"/static/common/images/"+mj.icon+".png'><t>"+mj.name+"</t></a>";
                            lis_2 += "<ul class='submenu'>"+lis_3+"</ul>";
                        }else {
                            lis_2 += "<li><a id='"+mj.id+"' url='"+mj.url+"' href='javascript:void(0);' onclick='tab_open(this);'><i class='fa fa-file-o'></i><t>"+mj.name+"</t></a>";
                        }
                        lis_2 += "</li>";
                    }
                }
                lis += "<li><a href='javascript:void(0);'><img src='"+PATH+"/static/common/images/"+mk.icon+".png'><t>"+mk.name+"</t></a>";
                if(lis_2 != ""){
                    lis += "<ul class='submenu'>"+lis_2+"</ul>";
                }
                lis += "</li>";
            }
        }
    }
}
$("#demo-list").empty();
$("#demo-list").append(lis);

jQuery("#jquery-accordion-menu").jqueryAccordionMenu();
$(function(){
    $("#demo-list li").on("click",function(){
        $("#demo-list li.active").removeClass("active");
        $(this).addClass("active");
    });
    function show_cur_times(){
        //获取当前日期
        var date_time = new Date();
        //定义星期
        var week;
        //switch判断
        switch (date_time.getDay()){
            case 1: week="星期一"; break;
            case 2: week="星期二"; break;
            case 3: week="星期三"; break;
            case 4: week="星期四"; break;
            case 5: week="星期五"; break;
            case 6: week="星期六"; break;
            default:week="星期天"; break;
        }
        //年
        var year = date_time.getFullYear();
        //判断小于10，前面补0
        if(year<10){
            year="0"+year;
        }
        //月
        var month = date_time.getMonth()+1;
        //判断小于10，前面补0
        if(month<10){
            month="0"+month;
        }
        //日
        var day = date_time.getDate();
        //判断小于10，前面补0
        if(day<10){
            day="0"+day;
        }
        //时
        var hours =date_time.getHours();
        //判断小于10，前面补0
        if(hours<10){
            hours="0"+hours;
        }
        //分
        var minutes =date_time.getMinutes();
        //判断小于10，前面补0
        if(minutes<10){
            minutes="0"+minutes;
        }
        //秒
        var seconds=date_time.getSeconds();
        //判断小于10，前面补0
        if(seconds<10){
            seconds="0"+seconds;
        }
        //拼接年月日时分秒
        var date_str = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds+" "+week;
        //显示在id为showtimes的容器里
        document.getElementById("menu_time").innerHTML= date_str;
    }
    //设置1秒调用一次show_cur_times函数
    setInterval(show_cur_times,100);
 });

function tab_open(th){
    th = $(th);
    var th_id = th.attr("id");
    var url = th.attr("url");
    var tabName = th.text();
    var $tabs = $('#tabs');
    var tabs = $tabs.tabs('tabs');
    for (var i = 0;i < tabs.length;i++){
        var id = tabs[i].panel("options").id;
        if(id == th_id){
            $tabs.tabs("select",tabName);
            return;
        }
    }
    $tabs.tabs('add',{
        method:'post',
        id : th_id,
        title : tabName,
        href : PATH+url,
        closable : true,
        loadingMessage : '',
        cache : true,
        queryParams:{
            model : th_id
        }
        /*tools : [{
            iconCls:'icon-mini-refresh',
            handler:function(){
                $tabs.tabs('getSelected').panel("refresh");
            }
        }]*/
    });
}