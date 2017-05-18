package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.RoleResource;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.util.Constants;
import com.infrastructure.service.system.IRoleService;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * 角色控制器
 *
 * @author tyq
 * @date 2016/1/14
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private IRoleService rsv;

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping
    public String index() {
        return "system/role_manage";
    }

    /**
     * 加载角色列表
     * @param pager
     * @param searchable
     * @param user
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Role> load(Page pager, @SearchParam(Role.class) Role searchable, @CurrentUser SysUser user){
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        //设置企业ID
        searchable.setCompanyId(user.getCompanyId());
        //根据企业ID查询角色
        List<Role> list = rsv.findAll(searchable, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 根据角色ID查询角色信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        Role select = rsv.select(id);
        return JSON.toJSONString(select);
    }

    /**
     * 返回页面之前将用户信息放入 Request
     *
     * @param session
     * @return
     */
    @ModelAttribute("currentUser")
    public SysUser currentUser(HttpSession session) {
        return (SysUser) session.getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
    }


    /**
     * 根据公司ID查询角色
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCid",method = RequestMethod.POST)
    @ResponseBody
    public String findByCid(@CurrentUser SysUser user){
        List<Map<String,String>> mapList = new ArrayList<>();
        List<Role> roles = rsv.findByCid(user.getCompanyId());
        for (Role role : roles) {
            Map<String,String> map = new HashMap<>();
            map.put("id",role.getId());
            map.put("text",role.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }

    /**
     * 根据用户ID查询拥有的角色
     * @param uId
     * @return
     */
    @RequestMapping(value = "/getRoleByUid",method = RequestMethod.POST)
    @ResponseBody
    public String getRoleByUid(String uId){
        List<String>  list = rsv.getRoleByUid(uId);
        return JSON.toJSONString(list);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@Valid Role role) {
        Json json = new Json();
        try {
            role.setId(IdKeyGenerator.uuid());
            int ret = rsv.insert(role);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "角色新增成功":"角色新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String roleUpdate(Role role) {
        Json json = new Json();
        try {
            int result = rsv.update(role);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "角色更新成功":"角色更新失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 角色赋权
     * @param ids
     * @return
     */
    @RequestMapping(value = "/authorization",method = RequestMethod.POST)
    @ResponseBody
    public Json authorization(@RequestParam("ids") String[] ids,@RequestParam("rId") String rId){
        Json json = new Json();
        try {
            int result = rsv.authorization(ids,rId);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功":"保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }


    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(@RequestParam("ids[]") String[] ids) {
        Json json = new Json();
        try{
            int result = rsv.deleteArray(ids);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 角色菜单授权视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.GET)
    public String roleResourceView(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        Set<String> resIds = rsv.getOwnedResourceIds(id);
        model.addAttribute("resIds", resIds);
        return "system/role_resource";
    }

    /**
     * 角色菜单授权
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.POST)
    @ResponseBody
    public Json roleResource(@PathVariable("id") String id, @RequestParam("ids") String[] ids) {
        Json json = new Json();
        List<RoleResource> items = Lists.newArrayList();
        for (String resId : ids) {
            RoleResource rr = new RoleResource();
            rr.setRoleId(id);
            rr.setResId(resId);
            items.add(rr);
        }
        int ret = rsv.contactWithResources(items);
        if (ret > 0) {
            json.setMessage("角色菜单更新成功");
            json.setSuccess(true);
        } else {
            json.setMessage("角色菜单更新失败");
        }

        return json;
    }
}
