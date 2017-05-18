package com.infrastructure.web.system;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.system.CustomerFeedback;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.spring.bind.annotation.SearchParam;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.service.targetManage.ICustomerFeedbackService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 客户反馈
 * Created by suyl on 2016/6/1
 */

@Controller
@RequestMapping("/customerFeedback")
public class CustomerFeedbackContoller {

    @Autowired
   private ICustomerFeedbackService iCustomerFeedbackService;

    /**
     * 跳转到客户反馈页面
     * @return
     */
    @RequestMapping(value = "/manage")
    public String index(String model,RedirectAttributes attr) {
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","system/customer_feedback_manage");
        return "redirect:/userLoginTime/manage";
    }

    /**
     * 分页显示客户反馈列表信息
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<CustomerFeedback> load(Page pager, String order, String stertsignDate, String endsignDate, @SearchParam(CustomerFeedback.class) CustomerFeedback searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(!"".equals(stertsignDate) && stertsignDate != null){
            date +=" AND c.createTime > CONCAT('"+stertsignDate+"')";
        }
        if(!"".equals(endsignDate) && endsignDate != null){
            date += " AND c.createTime < CONCAT('"+endsignDate+"')";
        }
        searchable.setWhere(" "+date + " order by createTime "+order+"");
        List<CustomerFeedback> list = iCustomerFeedbackService.queryCustomerFeedbackList(searchable,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }


    /**
     * 新增客户反馈
     *
     * @param customerFeedback
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@Valid CustomerFeedback customerFeedback,@CurrentUser SysUser user) {
        Json json = new Json();
        try {
            //获取id
            customerFeedback.setId(IdKeyGenerator.uuid());
            customerFeedback.setUserId(user.getId());
            customerFeedback.setCompanyId(user.getCompanyId());
            int ret = iCustomerFeedbackService.addCustomerFeedback(customerFeedback);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }


    /**
     * 根据客户反馈ID查询客户反馈信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        CustomerFeedback customerFeedback = iCustomerFeedbackService.getCustomerFeedbackById(id);
        return JSON.toJSONString(customerFeedback);
    }
}
