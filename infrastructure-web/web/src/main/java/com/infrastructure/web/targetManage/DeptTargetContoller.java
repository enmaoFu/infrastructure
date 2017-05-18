package com.infrastructure.web.targetManage;

import com.alibaba.fastjson.JSON;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.system.Dept;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.targetManage.CompanyTarget;
import com.infrastructure.entity.targetManage.DeptTarget;
import com.infrastructure.service.system.IDeptService;
import com.infrastructure.service.targetManage.ICompanyTargetService;
import com.infrastructure.service.targetManage.IDeptTargetService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyl on 2016/4/1
 * 部门目标
 */
@Controller
@RequestMapping("/deptTarget")
public class DeptTargetContoller {

    @Autowired
    private IDeptTargetService iDeptTargetService;

    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private ICompanyTargetService iCompanyTargetService;

    /**
     * 跳转到部门目标页面
     * @return
     */
    @RequestMapping(value = "/manage")
    public String index(String model, RedirectAttributes attr) {
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","targetManage/dept_target_manage");
        return "redirect:/userLoginTime/manage";
    }

    /**
     * 分页显示部门目标信息
     */
    @RequestMapping(value = "/loadDeptTarget",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<DeptTarget> loadTarget(Page pager,String title, String companyId, String deptId,String targetType,String targetYear,String targetTimeSlot,String companyTargetId ,String stertsignDate, String endsignDate, @SearchParam(DeptTarget.class) DeptTarget searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";

        if(!"".equals(stertsignDate) && stertsignDate != null){
            date += "AND c.createTime > CONCAT('"+stertsignDate+"',' 00:00:00')";
        }
        if(!"".equals(endsignDate) && endsignDate != null){
            date += "AND c.createTime < CONCAT('"+endsignDate+"',' 23:59:59')";
        }
        if(!"".equals(targetType) && targetType != null){
            date += "AND c.dept_target_type = '"+targetType+"'";
        }
        if(!"".equals(targetYear) && targetYear != null){
            date += "AND c.dept_target_year = '"+targetYear+"'";
        }
        if(!"".equals(targetTimeSlot) && targetTimeSlot != null){
            date += "AND c.dept_target_time_slot = '"+targetTimeSlot+"'";
        }
        if(!"".equals(companyTargetId) && companyTargetId != null){
            date += "AND c.company_target_id = '"+companyTargetId+"'";
        }
        if(!"".equals(title) && title != null){
            date += "AND c.dept_target_title LIKE '%"+title+"%'";
        }
        if(!"".equals(deptId) && deptId != null){
            date += "AND c.dept_id = '"+deptId+"'";
        }else {
            date += "AND c.dept_id = '"+user.getDeptId()+"'";
        }
        if(companyId != null && !"".equals(companyId)){
            date += "AND c.company_id = '"+companyId+"'";
        }else {
            date += "AND c.company_id = '"+user.getCompanyId()+"'";
        }
        searchable.setWhere(" "+date + "order by createTime Desc");
        List<DeptTarget> list = iDeptTargetService.queryDeptTargetList(searchable,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }


    /**
     * 新增部门目标
     *
     * @param deptTarget
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@Valid DeptTarget deptTarget, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("targetYear",deptTarget.getDeptTargetYear());
            params.put("targetType",deptTarget.getDeptTargetType());
            params.put("targetTimeSlot",deptTarget.getDeptTargetTimeSlot());
            params.put("companyId",user.getCompanyId());
            CompanyTarget target = iCompanyTargetService.queryCompanyTargetByMap(params);
            String deptTargetTimeSlotName = "";
            String deptTargetTypeName = "";
            if(deptTarget.getDeptTargetTimeSlot().equals("slot_year")){
                deptTargetTimeSlotName = "年度";
            }else if(deptTarget.getDeptTargetTimeSlot().equals("slot_half_year")){
                deptTargetTimeSlotName = "半年度";
            }
            if(deptTarget.getDeptTargetType().equals("type_economic_goals")){
                deptTargetTypeName = "经济目标";
            }else if(deptTarget.getDeptTargetType().equals("type_management_objectives")){
                deptTargetTypeName = "管理目标";
            }
            if(target == null || "".equals(target)){
                json.setSuccess(false);
                json.setMessage(deptTarget.getDeptTargetYear()+"年公司"+deptTargetTimeSlotName+deptTargetTypeName+"未添加!");
                return JSON.toJSONString(json);
            }
            //得到所属部门
            Dept dept = iDeptService.select(user.getDeptId());
            //查看该项是否已存在
            Map<String,Object> deptParams = new HashMap<>();
            deptParams.put("deptTargetYear",deptTarget.getDeptTargetYear());
            deptParams.put("deptTargetType",deptTarget.getDeptTargetType());
            deptParams.put("deptTargetTimeSlot",deptTarget.getDeptTargetTimeSlot());
            deptParams.put("deptId",user.getDeptId());
            deptParams.put("companyId",user.getCompanyId());
            List<DeptTarget> deptT = iDeptTargetService.queryDeptTargetByMap(deptParams);
            if(!CollectionUtils.isEmpty(deptT) && deptT.size() != 0){
                json.setSuccess(false);
                json.setMessage(deptTarget.getDeptTargetYear()+"年"+dept.getName()+deptTargetTimeSlotName+deptTargetTypeName+"已添加!");
                return JSON.toJSONString(json);
            }
            //设置所属公司
            deptTarget.setCompanyTargetId(target.getId());
            //获取id
            deptTarget.setId(IdKeyGenerator.uuid());
            //设置创建人
            deptTarget.setUserId(user.getId());
            //设置部门目标标题
            deptTarget.setDeptTargetTitle(deptTarget.getDeptTargetYear()+"年"+dept.getName()+deptTargetTimeSlotName+deptTargetTypeName);
            //所属公司
            deptTarget.setCompanyId(user.getCompanyId());
            deptTarget.setDeptId(user.getDeptId());
            int ret = iDeptTargetService.insert(deptTarget);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }



    /**
     * 查询部门目标详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        DeptTarget target = iDeptTargetService.getTargetDeptById(id);
        return JSON.toJSONString(target);
    }


    /**
     * 更新部门目标
     *
     * @param deptTarget
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(DeptTarget deptTarget, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            //得到所属部门
            Dept dept = iDeptService.select(user.getDeptId());
            //查看该项是否已存在
            Map<String,Object> deptParams = new HashMap<>();
            deptParams.put("deptTargetYear",deptTarget.getDeptTargetYear());
            deptParams.put("deptTargetType",deptTarget.getDeptTargetType());
            deptParams.put("deptTargetTimeSlot",deptTarget.getDeptTargetTimeSlot());
            deptParams.put("deptId",user.getDeptId());
            deptParams.put("companyId",user.getCompanyId());
            deptParams.put("deptTargetId",deptTarget.getId());
            List<DeptTarget> deptT = iDeptTargetService.queryDeptTargetByMap(deptParams);
            String deptTargetTimeSlotName = "";
            String deptTargetTypeName = "";
            if(deptTarget.getDeptTargetTimeSlot().equals("slot_year")){
                deptTargetTimeSlotName = "年度";
            }else if(deptTarget.getDeptTargetTimeSlot().equals("slot_half_year")){
                deptTargetTimeSlotName = "半年度";
            }
            if(deptTarget.getDeptTargetType().equals("type_economic_goals")){
                deptTargetTypeName = "经济目标";
            }else if(deptTarget.getDeptTargetType().equals("type_management_objectives")){
                deptTargetTypeName = "管理目标";
            }
            if(!CollectionUtils.isEmpty(deptT) && deptT.size() != 0){
                json.setSuccess(false);
                json.setMessage(deptTarget.getDeptTargetYear()+"年"+dept.getName()+deptTargetTimeSlotName+deptTargetTypeName+"已添加!");
                return JSON.toJSONString(json);
            }
            if(deptTarget.getDeptTargetType().equals("type_management_objectives")){
                deptTarget.setDeptTargetAmount("");
            }
            deptTarget.setDeptTargetTitle(deptTarget.getDeptTargetYear()+"年"+dept.getName()+deptTargetTimeSlotName+deptTargetTypeName);
            int result = iDeptTargetService.update(deptTarget);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "更新成功":"更新失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除部门目标
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("ids[]") String[] ids) {
        Json json = new Json();
        try{
            int result = iDeptTargetService.deleteBatchDeptTarget(ids);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

}
