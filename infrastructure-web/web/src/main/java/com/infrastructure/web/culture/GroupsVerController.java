package com.infrastructure.web.culture;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.infrastructure.entity.system.SysUser;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.culture.GroupsVerification;
import com.infrastructure.service.culture.IGroupsVerService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 群组验证消息控制器
 * Created by tyq on 16/3/12.
 */
@Controller
@RequestMapping("/group/verification")
public class GroupsVerController {

    @Autowired
    private IGroupsVerService iGroupsVerService;

    /**
     * 根据群组创建人查询群组消息+分页
     * @param pager
     * @param searchable
     * @param user
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<GroupsVerification> load(Page pager, GroupsVerification searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        searchable.setApplyUser(user.getId());
        // 查询数据
        List<GroupsVerification> list = new ArrayList<>();
        list = iGroupsVerService.queryPage(searchable,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(), list);
        return dataGrid;
    }

    /**
     * 查询待处理验证消息
     * @return
     */
    @RequestMapping(value = "/queryPending",method = RequestMethod.POST)
    @ResponseBody
    public String pending(@CurrentUser SysUser user){
        Json json = new Json();
        List<GroupsVerification> list = new ArrayList<>();
        list = iGroupsVerService.queryPending(user.getId());
        json.setSuccess(list.size() > 0);
        json.setObj(list.size());
        return JSON.toJSONString(json);

    }

    /**
     * 申请加入群
     * @param gv
     * @param user
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(GroupsVerification gv, @CurrentUser SysUser user){
        Json json = new Json();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            gv.setId(IdKeyGenerator.uuid());
            gv.setCreateTime(sdf.format(Calendar.getInstance().getTime()));
            gv.setApplyUser(user.getId());
            int result = iGroupsVerService.insert(gv);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "请求成功" : "请求失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 修改验证消息
     * @param gv
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(GroupsVerification gv, @CurrentUser SysUser user){
        Json json = new Json();
        try {
            gv.setApplyUser(user.getId());
            int result = iGroupsVerService.update(gv);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "操作成功" : "操作失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 查询申请记录
     * @param gv
     * @param user
     * @return
     */
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public String select(GroupsVerification gv, @CurrentUser SysUser user){
        Json json = new Json();
        gv.setApplyUser(user.getId());
        List<GroupsVerification> list = iGroupsVerService.findByGidAndUid(gv);
        Boolean flag = false;
        for (GroupsVerification grv : list) {
            if("P".equals(grv.getIsPass())){
                flag = true;
                break;
            }
        }
        json.setSuccess(flag);
        return JSON.toJSONString(json);
    }

    /**
     * 删除验证信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteArray",method = RequestMethod.POST)
    @ResponseBody
    public String deleteArray(@RequestParam("ids") String[] ids){
        Json json = new Json();
        try {
            int result = iGroupsVerService.deleteArray(ids);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "操作成功" : "操作失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }
}
