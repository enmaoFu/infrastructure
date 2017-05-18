package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.extra.jquery.model.JQueryDataGrid;
import com.google.common.collect.Lists;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.IResourceService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.entity.system.Resource;
import com.infrastructure.service.system.IRoleService;
import com.infrastructure.service.system.ISysUserService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 资源控制器
 *
 * @author tyq
 * @date 2016/1/14
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceController {

    /**
     * 系统管理员角色名称
     */
    private static final String SYSTEM_ADMIN_ROLE_NAME = "ROLE_SYSADMIN";

    @Autowired
    private IResourceService rsv;
    @Autowired
    private ISysUserService usv;
    @Autowired
    private IRoleService roleService;

    /**
     * 跳转到资源管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping
    public String index(Model model) {
        return "system/resource_manage";
    }

    /**
     * 加载资源数据
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid load() {
        // 查询数据
        List<Resource> list = rsv.findAll();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Resource resource : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",resource.getId());
            map.put("name",resource.getName());
            map.put("type",resource.getType());
            map.put("permission",resource.getPermission());
            map.put("icon",resource.getIcon());
            map.put("url",resource.getUrl());
            map.put("parentId",resource.getParentId());
            map.put("sorted",resource.getSorted());
            map.put("_parentId",resource.getParentId());
            mapList.add(map);
        }
        JQueryDataGrid dataGrid = new JQueryDataGrid(Long.getLong("0"),mapList);
        return dataGrid;
    }

    /**
     * 新增菜单
     *
     * @param resource
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Json addResource(Resource resource) {
        Json json = new Json();
        try {
            resource.setId(IdKeyGenerator.uuid());
            int ret = rsv.insert(resource);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "操作成功" : "操作失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 更新菜单
     *
     * @param res
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Json updateResource(Resource res) {
        Json json = new Json();
        try {
            int ret = rsv.update(res);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "操作成功" : "操作失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json deleteResource(String id) {
        Json json = new Json();
        try {
            int ret = rsv.delete(id);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            json.setMessage("系统繁忙,请稍后再试");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 请求菜单数据
     *
     * @return
     */
    @RequestMapping(value = "/ajax",method = RequestMethod.POST)
    @ResponseBody
    public List<Resource> ajax(@CurrentUser SysUser user) {
        // 获取当前登录用户名
        String login = (String) SecurityUtils.getSubject().getPrincipal();
        Set<String> permissions = usv.getPermissions(login);
        //rsv.findAll(); 查询所有权限
        List<Resource> resources = new ArrayList<>();
        if(StringUtils.isEmpty(user.getCompanyId())){
            resources = rsv.findAll();
        }else {
            resources = rsv.getMenus(permissions, true);
        }
        if (resources == null)
            resources = Lists.newArrayList();
        return resources;
    }

    /**
     * 根据角色ID查询角色所拥有的权限
     * @param rid
     * @return
     */
    @RequestMapping(value = "/findRidByResource",method = RequestMethod.POST)
    @ResponseBody
    public List<String> findRidByResource(String rid){
        List<String> list = rsv.findRidByResource(rid);
        return list;
    }

    /**
     * 更新系统管理员角色菜单
     *
     * @return
     */
    @RequestMapping("/updateSystemAdminResources")
    @ResponseBody
    public Json updateSystemAdminResources() {
        Json json = new Json();
        try {
            int ret = roleService.updateSystemAdminResources(SYSTEM_ADMIN_ROLE_NAME);
            if (ret > 0) {
                json.setSuccess(true);
                json.setMessage("更新成功");
            } else {
                json.setMessage("更新失败");
                json.setSuccess(false);
            }
        } catch (RuntimeException e) {
            json.setMessage("发生异常 " + e.getMessage());
            json.setSuccess(false);
        }

        return json;
    }

    /**
     * 根据用户ID查询url非空的菜单
     * @param user
     * @return
     */
    @RequestMapping(value = "/queryUrlNotNull",method = RequestMethod.POST)
    @ResponseBody
    public String queryUrlNotNull(@CurrentUser SysUser user){
        List<Resource> list = rsv.queryUrlNotNull(user.getId());
        List<Map<String,String>> mapList = new ArrayList<>();
        for (Resource resource : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",resource.getId());
            map.put("text",resource.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }
}
