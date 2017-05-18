package com.infrastructure.web.queryPerm;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.queryPerm.QueryPermissions;
import com.infrastructure.entity.queryPerm.QueryUserPermissions;
import com.infrastructure.entity.system.Company;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.queryPerm.IQueryPermissionsService;
import com.infrastructure.service.system.ICompanyService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询权限
 * Created by suyl on 2016/2/25
 */
@Controller
@RequestMapping("/queryPerm")
public class QueryPermissionsContoller {

    @Autowired
    private IQueryPermissionsService iQueryPermissionsService;

    @Autowired
    private ICompanyService iCompanyService;

    /**
     * 跳转到个人查询权限页面
     * @return
     */
    @RequestMapping(value = "/manage")
    public String index() {

        return "queryPerm/queryPerm_manage";
    }

    /**
     * 分页显示成本列表信息
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<QueryPermissions> load(Page pager, String stertsignDate, String endsignDate, @SearchParam(QueryPermissions.class) QueryPermissions searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(!"".equals(stertsignDate) && stertsignDate != null){
            date += "AND createTime > CONCAT('"+stertsignDate+"',' 00:00:00')";
        }
        if(!"".equals(endsignDate) && endsignDate != null){
            date += "AND createTime < CONCAT('"+endsignDate+"',' 23:59:59')";
        }
        //根据公司编号获取查询权限
        date += "AND perm1 = '"+user.getCompanyId()+"'";
        searchable.setWhere(" "+date + searchable.getWhere());
        List<QueryPermissions> list = iQueryPermissionsService.queryQueryPermissionsList(searchable,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 新增查询权限管理
     *
     * @param queryPermissions
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Json insert(@Valid QueryPermissions queryPermissions,@CurrentUser SysUser user) {
        Json json = new Json();
        try {
            //获取id
            queryPermissions.setId(IdKeyGenerator.uuid());
            queryPermissions.setPerm1(user.getCompanyId());
            int ret = iQueryPermissionsService.addQueryPermissions(queryPermissions);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 新增查询权限关联
     *
     * @param permIds
     * @return
     */
    @RequestMapping(value = "/insertUserPerm", method = RequestMethod.POST)
    @ResponseBody
    public Json insertUserPerm(@RequestParam("permIds[]") String[] permIds, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            int ret = 0;
            for(String s:permIds){
                QueryUserPermissions queryUserPermissions = new QueryUserPermissions();
                queryUserPermissions.setPermId(s);
                queryUserPermissions.setUserId(user.getId());
                ret = iQueryPermissionsService.addQueryUserPermissions(queryUserPermissions);
            }
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 根据查询权限ID查询查询权限信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        QueryPermissions queryPermissions = iQueryPermissionsService.getQueryPermissionsById(id);
        return JSON.toJSONString(queryPermissions);
    }


    /**
     * 更新查询权限信息
     *
     * @param queryPermissions
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Json contractUpdate(QueryPermissions queryPermissions) {
        Json json = new Json();
        try {
            int result = iQueryPermissionsService.modifyQueryPermissions(queryPermissions);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "更新成功":"更新失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return json;
    }
    /**
     * 删除查询权限信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(@RequestParam("ids[]") String[] ids) {
        Json json = new Json();
        try{
            int result = iQueryPermissionsService.deleteBatchQueryPermissions(ids);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 根据用户所属企业ID查询权限
     * @param user
     * @return
     */
    @RequestMapping(value = "/findQueryPermByUser",method = RequestMethod.POST)
    @ResponseBody
    public String findCidByRoles(Model model, @CurrentUser SysUser user){
        String cimId = user.getCompanyId();
        Map<String,Object> maps = new HashMap<>();
        maps.put("perm1",cimId);
        // 查询数据
        List<QueryPermissions> list = iQueryPermissionsService.queryPermissionsListByUser(maps);
        Company company = iCompanyService.findById(user.getCompanyId());
        StringBuffer sbf = new StringBuffer();
        if(CollectionUtils.isEmpty(list) || list.size() == 0){
            sbf.append("[{id:'"+company.getId()+"',text:'"+company.getName()+"'}]");
        }else {
            sbf.append("[{id:'"+company.getId()+"',text:'"+company.getName()+"',children:["+getNodes(list)+"]}]");
        }

        return JSON.toJSONString(sbf);
    }
    //递归树结构
    private StringBuffer getNodes(List<QueryPermissions> list){
        StringBuffer sbf = new StringBuffer();
        for (QueryPermissions queryPermissions : list) {
            sbf.append("{id:'"+queryPermissions.getId()+"',text:'"+queryPermissions.getPermName()+"'},");
        }
        return sbf;
    }

    /**
     * 查询权限更新
     *
     * @return
     */
    @RequestMapping(value = "/addQueryUserPermissions", method = RequestMethod.POST)
    @ResponseBody
    public Json roleUpdate(@RequestParam("roleIds") String[] roleIds,@CurrentUser SysUser user) {
        Json json = new Json();
        try {
            int ret = 0;
            for(String s:roleIds){
                QueryUserPermissions queryUserPermissions = new QueryUserPermissions();
                queryUserPermissions.setUserId(user.getId());
                queryUserPermissions.setPermId(s);
                ret = iQueryPermissionsService.addQueryUserPermissions(queryUserPermissions);
            }
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "保存成功" : "保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return json;
    }

    /**
     * 根据用户ID查询拥有的查询权限
     * @param user
     * @return
     */
    @RequestMapping(value = "/getQueryPermByUid",method = RequestMethod.POST)
    @ResponseBody
    public String getQueryPermByUid(@CurrentUser SysUser user){
        Map<String,Object> maps = new HashMap<>();
        maps.put("userId",user.getId());
        List<QueryPermissions>  list = iQueryPermissionsService.queryPermissionsListByUser(maps);
        return JSON.toJSONString(list);
    }
}
