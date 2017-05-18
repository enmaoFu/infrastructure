package com.infrastructure.service.smsSend;/*
package com.zr.oa.project.service.smsSend;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;;
import org.springframework.stereotype.Service;


*/
/**
 * Created by suyl on 2016/5/12
 * 短信发送
 *//*

@Service
public class SmsSendService implements ISmsSendService{

    */
/**
     * 短信路径
     *//*

    private static String SMS_URL = "http://gw.api.taobao.com/router/rest";

    */
/**
     * 短信类型
     *//*

    private String SMS_TYPE = "normal";

    */
/**
     * 公共回传参数
     *//*

    private String EXTEND = "123456";

    */
/**
     * App_Key
     *//*

    private String APP_KEY = "23367307";

    */
/**
     * App_Secret
     *//*

    private String APP_SECRET = "72df01246c8050a3b8e5a205daf81626";

    */
/**
     * 短信模板ID
     *//*

    private static String SMS_TEMPLATE_CODE = "SMS_9550541";//SMS_585014

    */
/**
     * 短信签名
     *//*

    private static String SIGN = "益朕软件";

    */
/**
     * 提交短信
     * @param phone
     * @param content
     * @return
     *//*

    @Override
    public boolean smsSend(String phone, String content) {
        JSONObject json = new JSONObject();
        json.put("empMsg",content);
        TaobaoClient client = new DefaultTaobaoClient(SMS_URL, APP_KEY, APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(EXTEND);
        req.setSmsType(SMS_TYPE);
        req.setSmsFreeSignName(SIGN);
        req.setSmsParamString(json.toJSONString());
        req.setRecNum(phone);
        req.setSmsTemplateCode(SMS_TEMPLATE_CODE);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        Boolean b = false;
        try{
            rsp = client.execute(req);
            b = rsp.getResult().getSuccess();
        }catch (Exception e){
            return false;
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new SmsSendService().smsSend("13657606421",
                "个人计划已到期"));
    }
}
*/
