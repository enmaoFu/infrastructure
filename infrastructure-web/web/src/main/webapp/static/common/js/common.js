//获取屏幕分辨率高度
var sh = screen.height;
//设置分页属性的时候初始化页面大小
var pageSize = 20;
var twoPageSize = 8;
//设置分页属性的时候 初始化页面大小选择列表
var pageList = [20,40,60];
var twoPageList = [8,16,24];
switch (sh){
    case 768 :
        break;
    case 1080 :
        pageSize = 30;
        pageList = [30,60,90];
        twoPageSize = 13;
        twoPageList = [13,26,39];
        break;
}