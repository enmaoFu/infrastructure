var $tabs = $('#tabs');
var $main_body = $("#main_body");
var expandFlag = false;
$(function(){
    if(!jQuery.browser.mozilla){
        $main_body.layout("add",{
            region:'north',
            height : 30,
            border : false,
            href : PATH + '/jsp/include/main_prompt.jsp'
        });
    }
});
function main_close_prompt(){
    $main_body.layout("remove","north");
}
$tabs.tabs({
    onAdd : function(title,index){
        expandFlag = true;
    },
    onSelect : function(title,index){
        /*var id = $tabs.tabs('getSelected').panel("options").id;
        if(!!zTree) {
            if(expandFlag) {
                expandFlag = false;
            }else {
                zTree.expandAll(false);
            }
            var node = zTree.getNodesByParam("id",id);
            if(node.length == 0){
                id = zTree.getNodes()[0].id;
                node = zTree.getNodesByParam("id",id);
            }
            zTree.selectNode(node[0],false);
        }*/
    }
});
var $ul = $tabs.find("ul");
$ul.on("mouseup",function(e){
    if(e.which == 3){
        $('#tabs_right_menu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    }
});
$("#tabs_close").on("click",function(){
    $ul.find("li").each(function(i, o){
        if(0 == i) return true;
        var title = $(o).children().eq(0).find("span").html();
        $tabs.tabs('close',title);
    });
});