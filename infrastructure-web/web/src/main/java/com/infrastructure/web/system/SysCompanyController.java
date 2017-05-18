package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.extra.jquery.model.JQueryDataGrid;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.entity.system.Company;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.system.ICompanyService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 企业控制器
 * Created by 谭永强 on 2016/1/18.
 */
@Controller
@RequestMapping("/system/company")
public class SysCompanyController {

    @Autowired
    private ICompanyService iCompanyService;

    /**
     * 跳转到企业管理页面
     * @return
     */
    @RequestMapping
    public String companyManage(){
        return "system/company_manage";
    }

    /**
     * 加载企业数据
     * @param searchable 查询数据
     * @param user 获取session中的用户信息
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid load(@SearchParam(Company.class) Company searchable, @CurrentUser SysUser user) {
        Company comp = iCompanyService.findById(user.getCompanyId());
        if(comp != null && !StringUtils.isEmpty(comp.getParents())){
            searchable.setId(comp.getParents().substring(0,32));
        }else {
            searchable.setId(user.getCompanyId());
        }
        // 查询数据
        List<Company> list = iCompanyService.findSysCompanys(searchable);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Company company : list) {
            Map<String,Object> map = new HashMap<>();
            String _parentId = "";
            String parentId = company.getParent();
            boolean flag = false;
            if(parentId.trim().length() > 0){
                for (Company com : list) {
                    //判断公司有无上级
                    if(com.getId().equals(parentId)){
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                _parentId = parentId;
            }
            map.put("id",company.getId());
            map.put("parent",company.getParent());
            map.put("parents", company.getParents());
            map.put("name",company.getName());
            map.put("abbreviation",company.getAbbreviation());
            map.put("address",company.getAddress());
            map.put("motto",company.getMotto());
            map.put("master",company.getMaster());
            map.put("createDate",company.getCreateDate());
            map.put("_parentId",_parentId);
            mapList.add(map);
        }
        JQueryDataGrid dataGrid = new JQueryDataGrid(Long.getLong("0"),mapList);
        return dataGrid;
    }

    /**
     * 根据用户所属企业ID查询企业及子企业
     * @param searchable
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCid",method = RequestMethod.POST)
    @ResponseBody
    public String findByCid(@SearchParam(Company.class) Company searchable, @CurrentUser SysUser user){
        // 查询数据
        List<Company> list = new ArrayList<>();
        if("administrator".equals(user.getLogin())){
            list = iCompanyService.findAll();
        }else {
            searchable.setId(user.getCompanyId());
            list = iCompanyService.findSysCompanys(searchable);
        }
        List<Map<String,String>> mapList = new ArrayList<>();
        for (Company company : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",company.getId());
            map.put("text",company.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }
    /**
     * 根据用户所属企业ID查询企业及子企业
     * @param user
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCompanyid",method = RequestMethod.POST)
    @ResponseBody
    public String findByCompanyid(@CurrentUser SysUser user){
        // 查询数据
        List<Company> list = new ArrayList<>();
        if("administrator".equals(user.getLogin())){
            list = iCompanyService.findAll();
        }else {
            list = iCompanyService.getUserByCompany(user,"Y");
        }
        List<Map<String,String>> mapList = new ArrayList<>();
        for (Company company : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",company.getId());
            map.put("text",company.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }
    /**
     * 根据用户所属企业ID查询角色
     * @param user
     * @return
     */
    @RequestMapping(value = "/findCidByRoles",method = RequestMethod.POST)
    @ResponseBody
    public String findCidByRoles(@CurrentUser SysUser user,String companyId){
        String cimId = user.getCompanyId();
        if(StringUtils.isEmpty(cimId)){
            cimId = companyId;
        }
        // 查询数据
        List<Company> list = iCompanyService.findCidByRoles(cimId);
        StringBuffer sbf = new StringBuffer();
        String cid = null;
        for (Company company : list) {
            if("".equals(company.getParent())){
                cid = company.getId();
            }
        }
        if(cid == null){
            for (Company company : list) {
                sbf.append("[{id:'"+company.getId()+"',text:'"+company.getName()+"',children:["+getNodes(list,company)+"]}]");
            }
        }else {
            for (Company company : list) {
                if(cid.equals(company.getId())){
                    sbf.append("[{id:'"+company.getId()+"',text:'"+company.getName()+"',children:["+getNodes(list,company)+"]}]");
                }
            }
        }
        return JSON.toJSONString(sbf);
    }

    /**
     * 根据用户所属企业ID查询角色
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByRoles",method = RequestMethod.POST)
    @ResponseBody
    public String findByRoles(@CurrentUser SysUser user){
        // 查询数据
        List<Company> list = iCompanyService.findCidByRoles(user.getCompanyId());
        return JSON.toJSONString(list);
    }

    //递归树结构
    private StringBuffer getNodes(List<Company> list,Company com){
        StringBuffer sbf = new StringBuffer();
        List<Role> roleList = com.getRoleList();
        for (Company company : list) {
            if(com.getId().equals(company.getParent())){
                sbf.append("{id:'"+company.getId()+"',text:'"+company.getName()+"',children:["+getNodes(list,company)+"]},");
            }
        }
        for (int i = 0; i < roleList.size(); i++) {
            Role role = roleList.get(i);
            sbf.append("{id:'"+role.getId()+"',text:'"+role.getName()+"'},");
        }
        return sbf;
    }

    /**
     * 根据企业ID查询企业信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        try {
            Company company = iCompanyService.findById(id);
            return JSON.toJSONString(company);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 企业新增
     * @param company 企业信息
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Company company){
        Json json = new Json();
        try {
            int result = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(!StringUtils.isEmpty(company.getId()) && StringUtils.isEmpty(company.getParent())){
                //添加上级
                result = iCompanyService.insertParent(company);
            }else {
                //添加下级
                //设置主键
                company.setId(IdKeyGenerator.uuid());
                //设置创建时间
                company.setCreateDate(sdf.format(Calendar.getInstance().getTime()));
                result = iCompanyService.insert(company);
            }
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功" : "保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 修改
     * @param company 企业数据
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Company company){
        Json json = new Json();
        try {
            int result = iCompanyService.update(company);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "修改成功" : "修改失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 企业删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(String id){
        Json json = new Json();
        try {
            int result = iCompanyService.delete(id);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 根据当前登录的用户得到企业
     * @param user
     * @return
     */
    @RequestMapping(value = "getCompanyByCurrentUser", method = RequestMethod.POST)
    @ResponseBody
    public String getCompanyByCurrentUser(@CurrentUser SysUser user){
        try {
            Company com = iCompanyService.findById(user.getCompanyId());
            String json = "[";
            json += JSON.toJSONString(com);
            json += "]";
            return json;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据当前用户查询上级公司、同级公司、下级公司
     * @param user
     * @return
     */
    @RequestMapping(value = "/findByCurrentCom",method = RequestMethod.POST)
    @ResponseBody
    public String findByCurrentCom(@CurrentUser SysUser user){

        //查询数据
        List<Company> list = new ArrayList<Company>();
        if(user.getLogin().equals("administrator")){
            list = iCompanyService.findAll();
        }else{
            list = iCompanyService.getCompanyByCurentCom(user.getCompanyId());
        }
        List<Map<String,String>> mapList = new ArrayList<>();
        for (Company company : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",company.getId());
            map.put("text",company.getName());
            mapList.add(map);
        }
        return JSON.toJSONString(mapList);
    }
}
