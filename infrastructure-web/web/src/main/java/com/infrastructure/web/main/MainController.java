package com.infrastructure.web.main;

import com.infrastructure.entity.main.AnnOrMeet;
import com.infrastructure.entity.system.SysUser;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.system.Company;
import com.infrastructure.service.main.IAnnOrMeetService;
import com.infrastructure.service.system.ICompanyService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import net.oschina.archx.mybatis.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页查询
 * Created by 谭永强 on 2016/5/11.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private IAnnOrMeetService iAnnOrMeetService;
    @Autowired
    private ICompanyService iCompanyService;

    /**
     * 通知通告/会议通知
     * @param pager
     * @param annOrMeet
     * @param user
     * @return
     */
    @RequestMapping(value = "/annOrMeet",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<AnnOrMeet> queryMyAndAll(Page pager, AnnOrMeet annOrMeet, @CurrentUser SysUser user){
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        annOrMeet.setCompanyId(user.getCompanyId());
        annOrMeet.setUserId(user.getId());
        Company company = iCompanyService.findById(user.getCompanyId());
        if(company != null && !StringUtils.isEmpty(company.getParents())){
            annOrMeet.setParentId(company.getParents().substring(0,32));
        }else {
            annOrMeet.setParentId(user.getCompanyId());
        }
        List<AnnOrMeet> list = new ArrayList<>();
        list = iAnnOrMeetService.queryAnnOrMeet(annOrMeet,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(), list);
        return dataGrid;
    }
}
