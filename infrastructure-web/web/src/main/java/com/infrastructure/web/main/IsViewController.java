package com.infrastructure.web.main;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.entity.main.IsView;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.main.IIsViewService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 是否已查看
 * Created by 谭永强 on 2016/5/6.
 */
@Controller
@RequestMapping("/view")
public class IsViewController {

    @Autowired
    private IIsViewService iIsViewService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(IsView isView,@CurrentUser SysUser user){
        Json json = new Json();
        try {
            isView.setUserId(user.getId());
            IsView view = iIsViewService.findByView(isView);
            if(view != null){
                json.setSuccess(false);
            }else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                isView.setId(IdKeyGenerator.uuid());
                isView.setCreateTime(sdf.format(Calendar.getInstance().getTime()));
                isView.setIsView("Y");
                int result = iIsViewService.insert(isView);
                json.setSuccess(result > 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
}
