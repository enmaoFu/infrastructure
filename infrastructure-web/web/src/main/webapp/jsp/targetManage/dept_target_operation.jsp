<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="win_dept_target_detail_detail" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximizable:false" style="width:520px;height:325px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <form id="form_dept_target_detail" method="post">
            <div data-options="region:'center',border:false" style="padding-left:10px;padding-top: 5px;">
                <input type="hidden" id="detailId" name="id">
                <table>
                    <tr style="height: 30px;">
                        <td><span style="letter-spacing: 3em;">年</span>份:</td>
                        <td>
                            <input id="deptTargetYear" class="input easyui-numberbox" type="text" name="deptTargetYear" readonly="readonly" data-options="required:true,min:1980,max:9999,prompt:'请输入4位正确的年份'"/>
                        </td>
                        <td>时间段:</td>
                        <td>
                            <input class="easyui-combobox" readonly="readonly" id="deptTargetTimeSlot" name="deptTargetTimeSlot" data-options="
                            required:true,
                            editable: false,
                            valueField: 'label',
                            textField: 'value',
                            data: [{
                                label: 'slot_year',
                                value: '年度'
                            },{
                                label: 'slot_half_year',
                                value: '半年度'
                            }]" />
                        </td>
                    </tr>
                    <tr style="height: 30px;" id="addTargetAmountDetail">
                    </tr>
                    <tr style="height: 80px;">
                        <td style="vertical-align: top;"><span style="letter-spacing: 3em;">内</span>容:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" readonly="readonly" type="text" name="deptTargetContent" data-options="multiline:true" style="width: 400px;height: 70px;" maxlength="2000"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top;"><span style="letter-spacing: 3em;">备</span>注:</td>
                        <td colspan="4">
                            <input class="easyui-textbox" readonly="readonly" type="text" maxlength="100" name="remark" data-options="multiline:true" style="width: 400px;height: 80px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="close_dept_target_detail">关闭</a>
            </div>
        </form>
    </div>
</div>
