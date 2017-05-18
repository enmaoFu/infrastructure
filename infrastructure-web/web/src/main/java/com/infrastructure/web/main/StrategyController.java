package com.infrastructure.web.main;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.main.Strategy;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.service.main.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 战略
 * Created by 谭永强 on 2016/4/21.
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private IStrategyService iStrategyService;

    /**
     * 根据公司ID查询战略
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCid",method = RequestMethod.POST)
    @ResponseBody
    public String findByCid(@CurrentUser SysUser user){
        Strategy strategy = iStrategyService.select(user.getCompanyId());
        return JSON.toJSONString(strategy);
    }

    /**
     * 战略添加
     * @param strategy
     * @param user
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Strategy strategy,@CurrentUser SysUser user){
        Json json = new Json();
        try {
            Strategy st = iStrategyService.select(user.getCompanyId());
            if(st != null){
                json.setMessage("本公司已有战略,无需重复添加");
            }else {
                strategy.setId(IdKeyGenerator.uuid());
                strategy.setCompanyId(user.getCompanyId());
                strategy.setContent(strategy.getContent().replaceAll("\r\n","<br>"));
                int result = iStrategyService.insert(strategy);
                json.setSuccess(result > 0);
                json.setMessage(result > 0 ? "添加成功" : "添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 战略修改
     * @param strategy
     * @param user
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Strategy strategy,@CurrentUser SysUser user){
        Json json = new Json();
        try {
            strategy.setContent(strategy.getContent().replaceAll("\r\n","<br>"));
            int result = iStrategyService.update(strategy);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "修改成功" : "修改失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }
}
