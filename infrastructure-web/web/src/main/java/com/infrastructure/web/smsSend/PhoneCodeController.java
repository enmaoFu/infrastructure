package com.infrastructure.web.smsSend;

import com.infrastructure.service.smsSend.ISmsSendService;
import com.infrastructure.util.PhoneCode;
import com.infrastructure.util.PhoneCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/phoneCode")
public class PhoneCodeController {

	/*@Autowired
	private ISmsSendService smsSendService;*/

	private Logger logger = LoggerFactory.getLogger(PhoneCodeController.class);

	/**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("send")
    public Object send(HttpServletRequest request,String phone){
    	logger.info("发送短信验证码入参phone:{}",phone);
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {

			if(StringUtils.isEmpty(phone)){
				map.put("code",0);
				map.put("message","手机号码不能为空！");
				return map;
			}

	    	Pattern pattern = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
			Matcher matcher = pattern.matcher(phone);
	    	if(!matcher.matches()){
	    		map.put("code",0);
			    map.put("message","手机号码不正确！");
			    return map;
	    	}
	    	PhoneCode phoneCode = (PhoneCode)request.getSession().getAttribute(PhoneCodeUtil.CODE_NAME);

			//验证码还有效,不能再发送
	    	if(PhoneCodeUtil.vaild(phoneCode)){
	    		map.put("code",0);
	            map.put("message",PhoneCodeUtil.CODE_OUT_TIME + "秒内仅能获取一次短信验证码,请稍后再试");
	    		return map;
	    	}

			phoneCode = PhoneCodeUtil.getInstance(phone);

			logger.info("短信验证码:{}",phoneCode);

	    	String content = "您本次操作的验证码为:"+phoneCode.getCode()+",为了您的账户安全,请妥善保管,勿泄漏给他人";

			//调用发送短信接口
//			smsSendService.smsSend(phone, content);

			request.getSession().setAttribute(PhoneCodeUtil.CODE_NAME, phoneCode);
			map.put("code",1);
			map.put("message","短信验证码已发送到您的手机！");

		} catch (Exception e) {
			map.put("code",0);
		    map.put("message","发送短信验证码异常！");
			e.printStackTrace();
			logger.info("发送短信异常信息:{}",e);
		}finally{
			logger.info("发送短信验证码出参map-->:{}",map);
		}
    	return map;
    }

}
