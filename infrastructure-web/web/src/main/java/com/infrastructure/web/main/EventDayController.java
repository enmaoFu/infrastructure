package com.infrastructure.web.main;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.main.EventDay;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.main.IEventDayService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 事件
 * Created by 谭永强 on 2016/4/27.
 */
@Controller
@RequestMapping("/event")
public class EventDayController {

    @Autowired
    private IEventDayService iEventDayService;


    /**
     * 查询事件列表
     * @param eventDay
     * @param user
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public String load(EventDay eventDay, @CurrentUser SysUser user){
        eventDay.setUserId(user.getId());
        List<Regime> list = new ArrayList<>();
        list = iEventDayService.load(eventDay);
        return JSON.toJSONString(list);
    }

    /**
     * 新增
     * @param eventDay
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(EventDay eventDay,@CurrentUser SysUser user){
        Json json = new Json();
        try {
            eventDay.setId(IdKeyGenerator.uuid());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            eventDay.setUserId(user.getId());
            eventDay.setCreateTime(sdf.format(Calendar.getInstance().getTime()));
            int result = iEventDayService.insert(eventDay);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "添加成功" : "添加失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    private String delete(String id){
        Json json = new Json();
        try {
            int result = iEventDayService.delete(id);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
}
