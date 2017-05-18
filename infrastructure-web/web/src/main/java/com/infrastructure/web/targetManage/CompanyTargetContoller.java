package com.infrastructure.web.targetManage;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.targetManage.CompanyTarget;
import com.infrastructure.entity.targetManage.DeptTarget;
import com.infrastructure.service.targetManage.ICompanyTargetService;
import com.infrastructure.service.targetManage.IDeptTargetService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.utils.CommonUtil;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 公司目标
 */
@Controller
@RequestMapping("/companyTarget")
public class CompanyTargetContoller {

    @Autowired
    private ICompanyTargetService iCompanyTargetService;

    @Autowired
    private IDeptTargetService iDeptTargetService;

    /**
     * 跳转到公司目标页面
     * @return
     */
    @RequestMapping(value = "/manage")
    public String index(String model, RedirectAttributes attr) {
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","targetManage/company_target_manage");
        return "redirect:/userLoginTime/manage";
    }

    /**
     * 分页显示公司目标信息
     */
    @RequestMapping(value = "/loadCompanyTarget",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<CompanyTarget> loadTarget(Page pager, String title, String companyId, String stertsignDate, String endsignDate, @SearchParam(CompanyTarget.class) CompanyTarget searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(CommonUtil.isNotEmpty(stertsignDate)){
            date += "AND c.createTime > CONCAT('"+stertsignDate+"',' 00:00:00')";
        }
        if(CommonUtil.isNotEmpty(endsignDate)){
            date += "AND c.createTime < CONCAT('"+endsignDate+"',' 23:59:59')";
        }
        if(CommonUtil.isNotEmpty(title)){
            date += "AND c.target_title LIKE '%"+title+"%'";
        }
        if(CommonUtil.isNotEmpty(companyId)){
            date += "AND c.company_id = '"+companyId+"'";
        }else {
            date += "AND c.company_id = '"+user.getCompanyId()+"'";
        }
        searchable.setWhere(" "+date + "order by createTime Desc");
        List<CompanyTarget> list = iCompanyTargetService.queryCompanyTargetList(searchable,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }


    /**
     * 新增公司目标
     *
     * @param companyTarget
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@Valid CompanyTarget companyTarget, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("targetYear",companyTarget.getTargetYear());
            params.put("targetType",companyTarget.getTargetType());
            params.put("targetTimeSlot",companyTarget.getTargetTimeSlot());
            params.put("companyId",user.getCompanyId());
            CompanyTarget target = iCompanyTargetService.queryCompanyTargetByMap(params);
            //设置公司目标标题
            String targetTimeSlotName = "";
            String targetTypeName = "";
            if(companyTarget.getTargetTimeSlot().equals("slot_year")){
                targetTimeSlotName = "年度";
            }else if(companyTarget.getTargetTimeSlot().equals("slot_half_year")){
                targetTimeSlotName = "半年度";
            }
            if(companyTarget.getTargetType().equals("type_economic_goals")){
                targetTypeName = "经济目标";
            }else if(companyTarget.getTargetType().equals("type_management_objectives")){
                targetTypeName = "管理目标";
            }
            if(target != null && !"".equals(target)){
                json.setSuccess(false);
                json.setMessage(companyTarget.getTargetYear()+"年公司"+targetTimeSlotName+targetTypeName+"已添加!");
                return JSON.toJSONString(json);
            }
            //获取id
            companyTarget.setId(IdKeyGenerator.uuid());
            //设置创建人
            companyTarget.setUserId(user.getId());
            companyTarget.setTargetTitle(companyTarget.getTargetYear()+"年公司"+targetTimeSlotName+targetTypeName);
            //所属公司
            companyTarget.setCompanyId(user.getCompanyId());
            int ret = iCompanyTargetService.insert(companyTarget);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }



    /**
     * 查询公司目标详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        CompanyTarget target = iCompanyTargetService.getTargetCompanyById(id);
        return JSON.toJSONString(target);
    }


    /**
     * 更新公司目标
     *
     * @param companyTarget
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(CompanyTarget companyTarget, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("targetYear",companyTarget.getTargetYear());
            params.put("targetType",companyTarget.getTargetType());
            params.put("targetTimeSlot",companyTarget.getTargetTimeSlot());
            params.put("companyId",user.getCompanyId());
            params.put("companyTargetId",companyTarget.getId());
            CompanyTarget target = iCompanyTargetService.queryCompanyTargetByMap(params);
            //设置公司目标标题
            String targetTimeSlotName = "";
            String targetTypeName = "";
            if(companyTarget.getTargetTimeSlot().equals("slot_year")){
                targetTimeSlotName = "年度";
            }else if(companyTarget.getTargetTimeSlot().equals("slot_half_year")){
                targetTimeSlotName = "半年度";
            }
            if(companyTarget.getTargetType().equals("type_economic_goals")){
                targetTypeName = "经济目标";
            }else if(companyTarget.getTargetType().equals("type_management_objectives")){
                targetTypeName = "管理目标";
            }
            if(target != null && !"".equals(target)){
                json.setSuccess(false);
                json.setMessage(companyTarget.getTargetYear()+"年公司"+targetTimeSlotName+targetTypeName+"已添加!");
                return JSON.toJSONString(json);
            }
            if(companyTarget.getTargetType().equals("type_management_objectives")){
                companyTarget.setTargetAmount("");
            }
            companyTarget.setTargetTitle(companyTarget.getTargetYear()+"年公司"+targetTimeSlotName+targetTypeName);
            int result = iCompanyTargetService.update(companyTarget);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "更新成功":"更新失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除公司目标
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("ids[]") String[] ids) {
        Json json = new Json();
        try{
            int result = iCompanyTargetService.deleteBatchCompanyTarget(ids);
            //删除部门目标
            if(result > 0){
                Map<String,Object> maps = new HashMap<>();
                maps.put("ids",ids);
                List<DeptTarget> dept = iDeptTargetService.queryDeptTargetByMap(maps);
                if(dept.size() != 0) {
                    result = iDeptTargetService.deleteBathByCompanyTargetId(ids);
                }
            }
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

}
