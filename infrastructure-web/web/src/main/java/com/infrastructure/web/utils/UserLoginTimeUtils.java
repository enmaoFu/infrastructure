package com.infrastructure.web.utils;

import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.entity.daily.Announcement;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.system.UserLoginTime;
import com.infrastructure.service.daily.IAnnouncementService;
import com.infrastructure.service.daily.IRegimeService;
import com.infrastructure.service.system.IUserLoginTimeService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/5/23.
 */
@Controller
@RequestMapping("/userLoginTime")
public class UserLoginTimeUtils {

    @Autowired
    private IAnnouncementService iAnnouncementService;
    @Autowired
    private IUserLoginTimeService iUserLoginTimeService;
    @Autowired
    private IRegimeService iRegimeService;
    /**
     * 新增记录用户登录/模块点击时间
     * @param model
     * @return
     */
    @RequestMapping(value = "/manage")
    public String addUserLoginTime(String model, Model mod, @CurrentUser SysUser user, String url) {
        try {
            //新增记录用户登录/模块点击时间
            UserLoginTime userLoginTime = new UserLoginTime();
            userLoginTime.setId(IdKeyGenerator.uuid());
            userLoginTime.setUserId(user.getId());
            userLoginTime.setModel(model);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(calendar.getTime());
            userLoginTime.setLcTime(date);
            iUserLoginTimeService.addUserLoginTime(userLoginTime);
            Map<String,Object> map = new HashMap<>();
            map.put("model",model);
            map.put("companyId",user.getCompanyId());
            Regime regime = iRegimeService.findByModel(map);
            Announcement announcement = iAnnouncementService.queryByModel(map);
            if(regime != null){
                regime.setContent(regime.getContent().replaceAll("\r\n","<br>"));
                mod.addAttribute("regime",regime);
            }
            if(announcement != null){
                mod.addAttribute("annou",announcement);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return url;
        }
    }

}
