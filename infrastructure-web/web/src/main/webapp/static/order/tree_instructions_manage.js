//获取接收对象的tree的url
var allReceivedTreeUl;
var chooseReceivedTreeUl;

//获取接收对象的tree
var allReceivedTree = null;
var chooseReceivedTree = null;

//获取转发对象的tree的ul（转发框中需要用的）
var allForwardOtherTreeUl;
var chooseForwardOtherTreeUl;

//获取接收对象的tree（转发框中需要用的）
var allForwardOtherTree = null;
var chooseForwardOtherTree = null;

var instructionsCompanyIdQuery;
var instructionsDeptQuery;
var forwardCompanyIdByQuery;
var forwardDeptQuery;
$(function(){

    allReceivedTreeUl = $('#adviseUserTree');
    chooseReceivedTreeUl = $('#adviseTree');
    instructionsCompanyIdQuery= $("#instructionsCompanyIdQuery");
    instructionsDeptQuery = $("#instructionsDeptQuery");
    forwardCompanyIdByQuery= $("#forwardCompanyIdByQuery");
    forwardDeptQuery= $("#forwardDeptQuery");
    allForwardOtherTreeUl = $("#forwardOtherUserTree");
    chooseForwardOtherTreeUl = $('#forwardOtherTree');
    /**
     * 建议对象企业部门联动
     */
    instructionsCompanyIdQuery.combobox({
        valueField : 'id',
        textField : 'text',
        method : 'post',
        url : PATH +'/system/company/findByCurrentCom',
        onSelect : function(record){
            var value = record.id;
            getTreeNodes(value, "", "received","");

            //var deptIdQuery = $("#receivedDeptIdQuery");
            instructionsDeptQuery.combobox({
                valueField:'id',
                textField:'text',
                method : 'post',
                url : PATH + '/system/dept/findDept',
                onBeforeLoad: function(param){
                    param.companyId = value;

                },
                onSelect : function(dept){
                    var deptId = dept.id;
                    getTreeNodes(value, deptId, "received","");
                },
                icons : [
                    {
                        iconCls:'icon-clear',
                        handler : function(){
                            $(this).parent().parent().prev().combobox("clear");
                        }
                    }
                ]
            });
        },
        icons : [
            {
                iconCls:'icon-clear',
                handler : function(){
                    $(this).parent().parent().prev().combobox("clear");
                }
            }
        ]
    });

    forwardCompanyIdByQuery.combobox({
        valueField : 'id',
        textField : 'text',
        method : 'post',
        url : PATH +'/system/company/findByCurrentCom',
        onSelect : function(record){
            var value = record.id;
            getTreeNodes(value, "", "forwardOther","");

            //var deptIdQuery = $("#receivedDeptIdQuery");
            forwardDeptQuery.combobox({
                valueField:'id',
                textField:'text',
                method : 'post',
                url : PATH + '/system/dept/findDept',
                onBeforeLoad: function(param){
                    param.companyId = value;

                },
                onSelect : function(dept){
                    var deptId = dept.id;
                    getTreeNodes(value, deptId, "forwardOther","");
                },
                icons : [
                    {
                        iconCls:'icon-clear',
                        handler : function(){
                            $(this).parent().parent().prev().combobox("clear");
                        }
                    }
                ]
            });
        },
        icons : [
            {
                iconCls:'icon-clear',
                handler : function(){
                    $(this).parent().parent().prev().combobox("clear");
                }
            }
        ]
    });
});

/**
*接收对象的树的操作
* @param all_zNodes 选择的所有的节点
* @param choose_zNodes  已选择的节点
*/
function to_chooseReceived(all_zNodes,choose_zNodes,checked){
    var all_setting = {
        treeId : "received_zTree",
        edit : {
            showRemoveBtn : false,
            showRenameBtn : false,
            drag : {
                isCopy : false,
                isMove : false,
                prev : false,
                next : false,
                inner : false
            }
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        view : {
            showLine : false
        },
        callback:{
            onClick: function(event, treeId, treeNode){
                if(treeNode == null) return;
                var node = chooseReceivedTree.getNodeByParam("id",treeNode.id,null);
                if(node != null){
                    return;
                }

                chooseReceivedTree.addNodes(null, treeNode);
            }
        }
    };
    var choose_setting = {
        treeId : "received_choose_zTree",
        edit: {
            enable: true,
            showRemoveBtn: true,
            showRenameBtn: false,
            drag: {
                isCopy: false,
                isMove: false,
                prev: true,
                next: true,
                inner: true
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeRemove: function(treeId, treeNode){
            }
        }
    };
    allReceivedTree = $.fn.zTree.init(allReceivedTreeUl, all_setting, all_zNodes);
    chooseReceivedTree = $.fn.zTree.init(chooseReceivedTreeUl, choose_setting,choose_zNodes);



    //选中已有的
    if(checked!=null && checked!=""){
        var arr = checked.split(",");
        for(var i=0;i<arr.length;i++){
            var treeNode = allReceivedTree.getNodeByParam("id",arr[i],null);
            if(treeNode != null && treeNode != "" && treeNode != "undefined"){
                var node = chooseReceivedTree.getNodeByParam("id",treeNode.id,null);
                if(node != null){
                    return;
                }
                chooseReceivedTree.addNodes(null, treeNode);
            }
        }
    }
}
/**
 *接收对象的树的操作
 * @param all_zNodes 选择的所有的节点
 * @param choose_zNodes  已选择的节点
 */
function to_chooseForwardOther(all_zNodes,choose_zNodes){
    var all_setting = {
        treeId : "forwardOther_zTree",
        edit : {
            showRemoveBtn : false,
            showRenameBtn : false,
            drag : {
                isCopy : false,
                isMove : false,
                prev : false,
                next : false,
                inner : false
            }
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        view : {
            showLine : false
        },
        callback:{
            onClick: function(event, treeId, treeNode){
                if(treeNode == null) return;
                var node = chooseForwardOtherTree.getNodeByParam("id",treeNode.id,null);
                if(node != null){
                    return;
                }

                chooseForwardOtherTree.addNodes(null, treeNode);
            }
        }
    };
    var choose_setting = {
        treeId : "forwardOther_choose_zTree",
        edit: {
            enable: true,
            showRemoveBtn: true,
            showRenameBtn: false,
            drag: {
                isCopy: false,
                isMove: false,
                prev: true,
                next: true,
                inner: true
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeRemove: function(treeId, treeNode){
            }
        }
    };
    allForwardOtherTree = $.fn.zTree.init(allForwardOtherTreeUl, all_setting, all_zNodes);
    chooseForwardOtherTree = $.fn.zTree.init(chooseForwardOtherTreeUl, choose_setting,choose_zNodes);
}

/**
 * 获取节点数据
 * @param companyId
 * @param deptId
 * @param flg
 */
function getTreeNodes(companyId, deptId, flg, received, forward){
    $.ajax({
        //得到公司下面的人
        type: 'post',
        url: PATH + "/system/sysuser/getUserByCompanyDept",
        data :{
            myFlg : 'N',
            companyId : companyId,
            deptId : deptId
        },
        success: function (users) {
            users = $.parseJSON(users);
            var all_zNodes = [];
            var choose_zNodes = [];
            var node = "";
            $.each(users,function(index,obj){
                node = "{'id':'"+obj.id+"','name':'"+obj.username+"'}";
                all_zNodes.push(eval("("+node+")"));
            });

            var chooseNode = "";
            if(flg == 'received'){
                //获取右边树的节点
                if(chooseReceivedTree!=null && chooseReceivedTree!="undefined"){
                    var chooseNodes = chooseReceivedTree.getNodes();
                    if(chooseNodes!=null && chooseNodes.length>0){
                        $.each(chooseNodes,function(index,obj){
                            chooseNode = "{'id':'"+obj.id+"','name':'"+obj.name+"'}";
                            choose_zNodes.push(eval("("+chooseNode+")"));
                        });
                    }
                }

                to_chooseReceived(all_zNodes, choose_zNodes,"");
            } else if(flg == "forwardOther") {
                //获取右边树的节点
                if(chooseForwardOtherTree!=null && chooseForwardOtherTree!="undefined"){
                    var chooseNodes = chooseForwardOtherTree.getNodes();
                    if(chooseNodes!=null && chooseNodes.length>0){
                        $.each(chooseNodes,function(index,obj){
                            chooseNode = "{'id':'"+obj.id+"','name':'"+obj.name+"'}";
                            choose_zNodes.push(eval("("+chooseNode+")"));
                        });
                    }
                }

                to_chooseForwardOther(all_zNodes, choose_zNodes);
            }else{
                to_chooseReceived(all_zNodes, choose_zNodes, received);
                to_chooseForwardOther(all_zNodes, choose_zNodes, forward);
            }

        }
    });
}
