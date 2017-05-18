package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.entity.system.Select;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.ISelectService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tyq on 2015/8/26.
 */
@Controller
@RequestMapping(value = "/select")
public class SelectController {

    @Autowired
    private ISelectService iSelectService;


    /**
     * 根据标识和公司ID查询下拉列表数据
     * @param select
     * @param user
     * @return
     */
    @RequestMapping(value = "/querySelect")
    @ResponseBody
    public String querySelect(Select select,@CurrentUser SysUser user){
        select.setCompanyId(user.getCompanyId());
        List<Select> list = iSelectService.querySelect(select);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Select select1 : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",select1.getS_key());
            map.put("text",select1.getS_val());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }

    /**
     * 新增
     * @param select
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Select select, @CurrentUser SysUser user){
        Json json = new Json();
        try{
            select.setId(IdKeyGenerator.uuid());
            select.setS_key(IdKeyGenerator.uuid());
            select.setCompanyId(user.getCompanyId());
            int result = iSelectService.insert(select);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功":"保存失败");
        }catch (Exception e){
            json.setSuccess(false);
            json.setMessage("系统异常，建议重新登录");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(HttpServletRequest request,String id){
        Json json = new Json();
        try{
            int result = iSelectService.delete(id);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功":"删除失败");
        }catch (Exception e){
            json.setSuccess(false);
            json.setMessage("系统异常，建议重新登录");
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 编辑
     * @param request
     * @param select
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,Select select){
        Json json = new Json();
        try{
            int result = iSelectService.update(select);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "修改成功":"修改失败");
        }catch (Exception e){
            json.setSuccess(false);
            json.setMessage("系统异常，建议重新登录");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
}
