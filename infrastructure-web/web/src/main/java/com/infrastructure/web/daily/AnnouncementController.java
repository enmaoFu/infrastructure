package com.infrastructure.web.daily;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.daily.Announcement;
import com.infrastructure.entity.system.SysFile;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.service.daily.IAnnouncementService;
import com.infrastructure.service.system.ISysFileService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.util.Constants;
import com.infrastructure.utils.FileUtils;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通知公告
 * Created by 谭永强 on 2016/4/26.
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private IAnnouncementService iAnnouncementService;
    @Autowired
    private ISysFileService iSysFileService;


    /**
     * 跳转到公告管理页面
     * @param model
     * @param attr
     * @return
     */
    @RequestMapping
    public String manage(String model, RedirectAttributes attr){
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","/daily/announcement_manage");
        return "redirect:/userLoginTime/manage";
    }

    /**
     * 查询公司所有通知公告
     * @param pager
     * @param announcement
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Announcement> load(Page pager, Announcement announcement, @CurrentUser SysUser user){
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        //设置企业ID
        announcement.setCompanyId(user.getCompanyId());
        // 查询数据
        List<Announcement> list = iAnnouncementService.queryPage(announcement, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(), list);
        return dataGrid;
    }

    /**
     * 查询本公司公告和关联公司公告
     * @param pager
     * @param announcement
     * @param user
     * @return
     */
    @RequestMapping(value = "/queryMyAndAll",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Announcement> queryMyAndAll(Page pager,Announcement announcement,@CurrentUser SysUser user){
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        //设置企业ID
        announcement.setCompanyId(user.getCompanyId());
        //设置当前用户ID
        announcement.setUserId(user.getId());
        // 查询数据
        List<Announcement> list = iAnnouncementService.queryMyAndAll(announcement, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(), list);
        return dataGrid;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        Announcement announcement = iAnnouncementService.select(id);
        return JSON.toJSONString(announcement);
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Announcement announcement, HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        Json json = new Json();
        SysFile sysFile = new SysFile();
        try {
            FileUtils.setSysFile(sysFile,file,path);
            if(sysFile != null && sysFile.getNowFileName() != null){
                sysFile.setId(IdKeyGenerator.uuid());
                sysFile.setAttachmentId(IdKeyGenerator.uuid());
                announcement.setAttachment(sysFile.getAttachmentId());
            }
            announcement.setId(IdKeyGenerator.uuid());
            announcement.setCreateTime(sdf.format(Calendar.getInstance().getTime()));
            announcement.setCompanyId(user.getCompanyId());
            int result = iAnnouncementService.insert(announcement,sysFile);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Announcement announcement, HttpServletRequest request, @RequestParam("file") MultipartFile file, String fileName){
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        Json json = new Json();
        SysFile sysFile = new SysFile();
        try {
            if(file.getSize() > 0){
                FileUtils.setSysFile(sysFile,file,path);
                if(sysFile != null && sysFile.getNowFileName() != null){
                    sysFile.setId(IdKeyGenerator.uuid());
                    sysFile.setAttachmentId(IdKeyGenerator.uuid());
                    announcement.setAttachment(sysFile.getAttachmentId());
                }
            }
            if(StringUtils.isEmpty(fileName)){
                sysFile.setId("1");
                announcement.setAttachment("1");
            }
            int result = iAnnouncementService.update(announcement,sysFile,path);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功":"保存失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request,@RequestParam("ids") String[] ids){
        Json json = new Json();
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        try {
            int result = iAnnouncementService.deleteArray(ids,path);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 发布
     * @param ids
     * @param arange
     * @return
     */
    @RequestMapping(value = "/release",method = RequestMethod.POST)
    @ResponseBody
    public String release(@RequestParam("ids") String[] ids,String arange){
        Json json = new Json();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<Announcement> list = new ArrayList<>();
            for (String id : ids) {
                Announcement an = new Announcement();
                an.setId(id);
                an.setArange(arange);
                an.setReleaseTime(sdf.format(Calendar.getInstance().getTime()));
                list.add(an);
            }
            int result = iAnnouncementService.release(list);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "发布成功" : "发布失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 文件下载
     * @param id
     */
    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id){
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/")+"upload/"+user.getCompanyId();
        response.setCharacterEncoding("UTF-8");
        Announcement an = iAnnouncementService.select(id);
        SysFile sysFile = iSysFileService.select(an.getAttachment());
        String fileName = sysFile.getOriginalFileName();
        /**
         * 文件下载
         */
        FileUtils.download(response,path+"/"+sysFile.getNowFileName(),fileName);
    }
}
