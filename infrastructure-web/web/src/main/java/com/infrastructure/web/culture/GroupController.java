package com.infrastructure.web.culture;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.culture.GroupsUser;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.culture.IGroupService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.culture.Group;
import com.infrastructure.entity.culture.GroupsUserMessages;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: 谭永强
 * Date: 2016/2/14
 * Time: 11:48
 */
@Controller
@RequestMapping("/culture/group")
public class GroupController {

    @Autowired
    private IGroupService iGroupService;

    /**
     * 跳转到工作群组管理页面
     * @return
     */
    @RequestMapping
    public String index(String model, RedirectAttributes attr){
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","culture/group_manage");
        return "redirect:/userLoginTime/manage";
    }


    /**
     * 根据用户ID查询群组
     * @param pager
     * @param searchable
     * @param user
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Group> load(Page pager, Group searchable, @CurrentUser SysUser user, String state) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        // 查询数据
        List<Group> list = new ArrayList<>();
        if("my".equals(state)){
            //我的群组
            searchable.setCreateUser(user.getId());
            list = iGroupService.queryGroupPageMy(searchable, page);
        }else {
            //公司群组
            searchable.setCompanyId(user.getCompanyId());
            list = iGroupService.queryGroupPageCompany(searchable, page);
        }
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(), list);
        return dataGrid;
    }

    /**
     * 创建群组
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(@CurrentUser SysUser user,Group group, @RequestParam("userIds") String[] userIds){
        Json json = new Json();
        try{
            group.setId(IdKeyGenerator.uuid());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            group.setCreateDate(sdf.format(Calendar.getInstance().getTime()));
            group.setCreateUser(user.getId());
            group.setCompanyId(user.getCompanyId());
            List<String> list = new ArrayList<>(Arrays.asList(userIds));
            int result = iGroupService.insert(group,list);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "创建成功" : "创建失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙，请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 修改群组
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Group group, @RequestParam("userIds") String[] userIds){
        Json json = new Json();
        try{
            List<String> list = new ArrayList<>(Arrays.asList(userIds));
            int result = iGroupService.update(group,list);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功" : "保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙，请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 群组删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(@CurrentUser SysUser user,@RequestParam("ids") String[] ids){
        Json json = new Json();
        try {
            int result = iGroupService.deleteArray(user.getId(),ids);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常，请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 根据群组ID查询成员
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/findGroupIdByUser",method = RequestMethod.POST)
    @ResponseBody
    public String findGroupIdByUser(@CurrentUser SysUser user, String groupId, HttpServletRequest request){
        Group group = iGroupService.findGroupIdByUser(groupId);
        if(group == null){
            Json json = new Json();
            json.setSuccess(true);
            json.setMessage("该群组已被解散");
            return JSON.toJSONString(json);
        }
        List<GroupsUserMessages> list = iGroupService.queryMessage(groupId);
        Map<String,Object> map = new HashMap<>();
        map.put("group",group.getUserList());
        map.put("list",list);
        request.getSession().setAttribute(user.getId(),groupId);
        return JSON.toJSONString(map);
    }

    /**
     * 根据群组ID查询成员
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/findGroupUser",method = RequestMethod.POST)
    @ResponseBody
    public String findGroupUser(String groupId){
        List<GroupsUser> list = iGroupService.findGroupUser(groupId);
        return JSON.toJSONString(list);
    }
}
