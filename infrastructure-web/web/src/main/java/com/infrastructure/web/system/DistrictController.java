package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.system.District;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.IDistrictService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zj on 2016/4/5.
 */
@Controller
@RequestMapping(value = "/district")
public class DistrictController {

    @Autowired
    private IDistrictService iDistrictService;


    /**
     * 根据 地区等级 和 上级地区代码 查询下拉列表数据
     * @param district
     * @param user
     * @return
     */
    @RequestMapping(value = "/findChildrens")
    @ResponseBody
    public String findChildrens(District district, @CurrentUser SysUser user){
        List<District> list = iDistrictService.findChildrens(district.getCode());
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (District district1 : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",district1.getCode());
            map.put("text",district1.getDistrict());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }


}
