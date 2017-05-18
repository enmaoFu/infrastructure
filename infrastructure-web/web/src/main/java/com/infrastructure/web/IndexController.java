package com.infrastructure.web;

import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.main.IWorkbenchService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.util.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 首页控制器
 *
 * @author TYQ
 * @date 2016/1/10
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private IWorkbenchService iWorkbenchService;

    /**
     * 跳转到主页
     * PC端
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest req,@CurrentUser SysUser user, Model model){

        /**********在线人数 satrt********/
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<SysUser> list = new ArrayList<>();
        for(Session session:sessions){
            SysUser u = (SysUser)session.getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
            if(u != null && !StringUtils.isEmpty(user.getCompanyId()) && user.getCompanyId().equals(u.getCompanyId())){
                if(list.size() == 0){
                    list.add(u);
                    continue;
                }
                boolean flag = false;
                for (SysUser sysUser : list) {
                    if(sysUser.getId().equals(u.getId())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    list.add(u);
                }
            }
        }
        /**********在线人数 end********/
        //在线人数
        model.addAttribute("zx_session",list.size());
        model.addAttribute("zxList",list);
        return "index";
    }
}
