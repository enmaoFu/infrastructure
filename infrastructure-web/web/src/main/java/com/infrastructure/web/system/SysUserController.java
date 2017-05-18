package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.IRoleService;
import com.infrastructure.service.system.ISysUserService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import net.oschina.archx.mybatis.Pagination;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统用户控制器
 *
 * @author tyq
 * @date 2016/1/14
 */
@Controller
@RequestMapping("/system/sysuser")
public class SysUserController {

    @Autowired
    private ISysUserService usv;

    @Autowired
    private IRoleService iRoleService;

    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping
    public String index() {
        return "system/sysuser_manage";
    }

    /**
     * 加载用户数据
     *
     * @param pager
     * @param searchable
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<SysUser> load(Page pager, @SearchParam(SysUser.class) SysUser searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        //设置企业ID
        searchable.setCompanyId(user.getCompanyId());
        // 查询数据
        List<SysUser> list = usv.findSysUsers(searchable, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        try{
            SysUser user = usv.select(id);
            return JSON.toJSONString(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据角色ID查询用户
     * @param rid
     * @return
     */
    @RequestMapping(value = "/findByRid",method = RequestMethod.POST)
    @ResponseBody
    public String findByRid(String rid){
        List<SysUser> list = usv.findByRid(rid);
        List<Map<String,String>> mapList = getMapList(list);
        return JSON.toJSONString(mapList);
    }

    /**
     * 根据部门ID查询用户
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/findByDeptId",method = RequestMethod.POST)
    @ResponseBody
    public String findByDeptId(String deptId){
        List<SysUser> list = usv.findByDeptId(deptId);
        List<Map<String,String>> mapList = getMapList(list);
        return JSON.toJSONString(mapList);
    }

    /**
     * 根据部门ID查询用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/findWorkLogByDeptId",method = RequestMethod.POST)
    @ResponseBody
    public String findWorkLogByDeptId(@CurrentUser SysUser user){
        //部门编号
        List<Role> roleList = iRoleService.queryRolesListByUserId(user.getId());
        List<SysUser> list = usv.findByDeptId(roleList.get(0).getDeptId());
        List<Map<String,String>> mapList = getMapList(list);
        return JSON.toJSONString(mapList);
    }

    /**
     * 根据公司ID查询员工
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCompanyId",method = RequestMethod.POST)
    @ResponseBody
    public String findByCompanyId( @CurrentUser SysUser user){
        List<SysUser> list = usv.findByCid(user.getCompanyId());
        List<Map<String,String>> mapList = getMapList(list);
        return JSON.toJSONString(mapList);
    }

    private List<Map<String,String>> getMapList(List<SysUser> list){
        List<Map<String,String>> mapList = new ArrayList<>();
        for (SysUser user : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("text",user.getUsername());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 添加用户
     * @param user
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(SysUser user){
        Json json = new Json();
        try{
            //设置主键
            user.setId(IdKeyGenerator.uuid());
            String[] pwds = user.getPassword().split(",");
            //设置密码
            user.setPassword(DigestUtils.md5Hex(pwds[0]));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //设置创建时间
            user.setCreataDate(sdf.format(Calendar.getInstance().getTime()));
            String prefix = user.getPrefix();
            String login = user.getLogin();
            user.setLogin(prefix+login);
            SysUser sysUser = usv.findByLogin(user.getLogin());
            if(sysUser == null){
                int result = usv.insert(user);
                json.setSuccess(result > 0);
                json.setMessage(result > 0 ? "保存成功" : "保存失败");
            }else {
                json.setSuccess(false);
                json.setMessage("此登录名已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试!");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除用户
     * @param ids ID数组
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(@RequestParam("ids[]") String[] ids){
        Json json = new Json();
        try{
            int result = usv.deleteArray(ids);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            json.setMessage("系统繁忙,请稍后再试");
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 更新用户
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(SysUser user) {
        Json json = new Json();
        try {
            SysUser u = usv.select(user.getId());
            String[] pwds = user.getPassword().split(",");
            if(pwds[0].equals(u.getPassword())){
                user.setPassword(null);
            }else {
                user.setPassword(DigestUtils.md5Hex(pwds[0]));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //设置修改日期
            user.setUpdateDate(sdf.format(Calendar.getInstance().getTime()));
            String prefix = user.getPrefix();
            String login = user.getLogin();
            if(!StringUtils.isEmpty(prefix)){
                user.setLogin(prefix+login);
            }else {
                user.setLogin(login);
            }
            int result = 0;
            if(u.getLogin().equals(user.getLogin())){
                result = usv.update(user);
            }else {
                SysUser sysUser = usv.findByLogin(user.getLogin());
                if(sysUser == null){
                    result = usv.update(user);
                }else {
                    json.setSuccess(false);
                    json.setMessage("此登录名已存在");
                    return JSON.toJSONString(json);
                }
            }
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "更新成功" : "更新失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 角色更新
     *
     * @return
     */
    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Json roleUpdate(String uId,@RequestParam("roleIds") String[] roleIds) {
        Json json = new Json();
        try {
            int ret = usv.contactWithRoles(uId, roleIds);
            json.setSuccess(ret > -1);
            json.setMessage(ret > -1 ? "保存成功" : "保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 根据parentId查询用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByParentId",method = RequestMethod.POST)
    @ResponseBody
    public String findByParentId(@CurrentUser SysUser user){
        List<SysUser> list = new ArrayList<>();
        try{
            list = usv.searchUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(list);
    }

    /**
     * 根据企业或部门查询用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/getUserByCompanyDept",method = RequestMethod.POST)
    @ResponseBody
    public String getUserByCompanyDept(@CurrentUser SysUser u,SysUser user,String myFlg){
        try {
            if(StringUtils.isEmpty(user.getCompanyId()) && StringUtils.isEmpty(user.getDeptId())){
                user.setCompanyId(u.getCompanyId());
            }
            if("N".equals(myFlg)){
                user.setId(u.getId());
            }
            List<SysUser> list = usv.getUserByCompanyDept(user);
            return JSON.toJSONString(list);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据企业和部门查询用户,主要用于前端的combobox
     * @param user
     * @return
     */
    @RequestMapping(value = "/getUserCombobox",method = RequestMethod.POST)
    @ResponseBody
    public String getUserByCompanyDept(@CurrentUser SysUser u,SysUser user){
        List<Map<String,Object>> mapList = new ArrayList<>();
        try {
            if(StringUtils.isEmpty(user.getCompanyId()) && StringUtils.isEmpty(user.getDeptId())){
                user.setCompanyId(u.getCompanyId());
            }
            List<SysUser> list = usv.getUserByCompanyDept(user);
            for (SysUser sysUser : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",sysUser.getId());
                map.put("text",sysUser.getUsername());
                mapList.add(map);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(mapList);
    }
}
