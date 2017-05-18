package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.system.Dept;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.IDeptService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import net.oschina.archx.mybatis.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 * User: 谭永强
 * Date: 2016/1/28
 * Time: 16:17
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    private IDeptService iDeptService;

    /**
     * 跳转到部门管理页面
     * @return
     */
    @RequestMapping()
    public String manage(@CurrentUser SysUser user){
        return "system/dept_manage";
    }

    /**
     * 加载部门列表
     * @param pager
     * @param searchable
     * @param user
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Role> load(Page pager, @SearchParam(Dept.class) Dept searchable, @CurrentUser SysUser user){
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        //设置企业ID
        searchable.setCompanyId(user.getCompanyId());
        //根据企业ID查询部门
        List<Dept> list = iDeptService.findAll(searchable, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 根据公司ID查询部门
     * @param user
     * @return
     */
    @RequestMapping(value = "/findDept",method = RequestMethod.POST)
    @ResponseBody
    public String findDept(@CurrentUser SysUser user,String companyId){
        List<Dept> list = iDeptService.findByCompanyId(companyId);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Dept dept : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",dept.getId());
            map.put("text",dept.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }


    /**
     * 根据公司ID查询部门
     * @param user
     * @return
     */
    @RequestMapping(value = "/findDeptbycompanyId",method = RequestMethod.POST)
    @ResponseBody
    public String findDeptbycompanyId(@CurrentUser SysUser user){
        List<Dept> list = iDeptService.findByCompanyId(user.getCompanyId());
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Dept dept : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",dept.getId());
            map.put("text",dept.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }

    /**
     * 根据公司ID查询部门及用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/findUserByCid",method = RequestMethod.POST)
    @ResponseBody
    public String findUserByCid(@CurrentUser SysUser user){
        List<Dept> list = iDeptService.findUserByCid(user.getCompanyId());
        return JSON.toJSONString(list);
    }

    /**
     * 部门新增
     * @param dept
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Dept dept,@CurrentUser SysUser user){
        Json json = new Json();
        try{
            //获取上级部门信息
            Dept dep = iDeptService.select(dept.getParentId());
            if(dep != null){
                //获取上级的部门的所有上级ID
                String parentIds = dep.getParentIds();
                if(!StringUtils.isEmpty(parentIds)){
                    dept.setParentIds(dep.getParentIds()+","+dept.getParentId());
                }else {
                    dept.setParentIds(dept.getParentId());
                }
            }
            dept.setId(IdKeyGenerator.uuid());
            int result = iDeptService.insert(dept);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "新增成功" : "新增失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常，请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 部门修改
     * @param dept
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Dept dept){
        Json json = new Json();
        try{
            //获取上级部门信息
            Dept dep = iDeptService.select(dept.getParentId());
            if(dep != null){
                //获取上级的部门的所有上级ID
                String parentIds = dep.getParentIds();
                if(!StringUtils.isEmpty(parentIds)){
                    dept.setParentIds(dep.getParentIds()+","+dept.getParentId());
                }else {
                    dept.setParentIds(dept.getParentId());
                }
            }
            int result = iDeptService.update(dept);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "修改成功" : "修改失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常，请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(@RequestParam("ids") String[] ids){
        Json json = new Json();
        try {
            int result = iDeptService.deleteArray(ids);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙，请稍后再试");
        }
        return json;
    }

    /**
     * 根据公司ID查询部门 若companyID为空，则默认查本公司的
     * @param user
     * @return
     */
    @RequestMapping(value = "/findDeptByComId",method = RequestMethod.POST)
    @ResponseBody
    public String findDeptByComId(@CurrentUser SysUser user,String companyId){
        if(companyId.isEmpty()){
            return this.findDeptbycompanyId(user);
        }else{
            return this.findDept(user, companyId);
        }
    }

    /**
     * 根据ID查询部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        try {
            Dept dept = iDeptService.select(id);
            return JSON.toJSONString(dept);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
