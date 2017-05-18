package com.infrastructure.web.daily;

import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.system.SysFile;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.daily.IAnnouncementService;
import com.infrastructure.service.daily.IRegimeService;
import com.infrastructure.service.system.ISysFileService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.utils.FileUtils;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.util.Constants;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 制度-控制器
 * Created by 谭永强 on 2016/4/25.
 */
@Controller
@RequestMapping("/regime")
public class RegimeController {

    @Autowired
    private IRegimeService iRegimeService;
    @Autowired
    private ISysFileService iSysFileService;
    @Autowired
    private IAnnouncementService iAnnouncementService;

    /**
     * 跳转到制度管理页面
     * @return
     */
    @RequestMapping
    public String index(String model, RedirectAttributes attr){
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","/daily/regime_manage");
        return "redirect:/userLoginTime/manage";
    }

    /**
     * 制度查询
     * @param pager
     * @param regime
     * @param user
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Regime> load(Page pager, Regime regime, @CurrentUser SysUser user){
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        List<Regime> list = new ArrayList<>();
        regime.setCompanyId(user.getCompanyId());
        list = iRegimeService.load(regime,page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
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
        Regime regime = iRegimeService.select(id);
        return JSON.toJSONString(regime);
    }

    /**
     * 新增
     * @param regime
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insert(Regime regime, HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Json json = new Json();
        SysFile sysFile = new SysFile();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("model",regime.getModel());
            map.put("companyId",user.getCompanyId());
            if(!StringUtils.isEmpty(regime.getModel())){
                Regime reg = iRegimeService.findByModel(map);
                if(reg != null){
                    json.setSuccess(false);
                    json.setMessage("该模块下已存在制度");
                    return JSON.toJSONString(json);
                }
            }
            FileUtils.setSysFile(sysFile,file,path);
            if(sysFile != null && sysFile.getNowFileName() != null){
                sysFile.setId(IdKeyGenerator.uuid());
                sysFile.setAttachmentId(IdKeyGenerator.uuid());
                regime.setAttachment(sysFile.getAttachmentId());
            }
            regime.setId(IdKeyGenerator.uuid());
            regime.setCreateTime(sdf.format(Calendar.getInstance().getTime()));
            regime.setCompanyId(user.getCompanyId());
            int result = iRegimeService.insert(regime,sysFile);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            if(sysFile != null){
                File f = new File(path+"/"+sysFile.getNowFileName());
                f.delete();
            }
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 修改
     * @param regime
     * @param fileName 文件名称
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Regime regime, HttpServletRequest request, @RequestParam("file") MultipartFile file, String fileName){
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.DEFAULT_USER_INFO_SESSION);
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        Json json = new Json();
        SysFile sysFile = new SysFile();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("model",regime.getModel());
            map.put("companyId",user.getCompanyId());
            Regime reg = iRegimeService.findByModel(map);
            if(reg != null){
                reg = iRegimeService.select(regime.getId());
                if(!reg.getModel().equals(regime.getModel())){
                    json.setSuccess(false);
                    json.setMessage("该模块下已存在制度");
                    return JSON.toJSONString(json);
                }
            }

            if(file.getSize() > 0){
                FileUtils.setSysFile(sysFile,file,path);
                if(sysFile != null && sysFile.getNowFileName() != null){
                    sysFile.setId(IdKeyGenerator.uuid());
                    sysFile.setAttachmentId(IdKeyGenerator.uuid());
                    regime.setAttachment(sysFile.getAttachmentId());
                }
            }
            if(StringUtils.isEmpty(fileName)){
                sysFile.setId("1");
                regime.setAttachment("1");
            }
            int result = iRegimeService.update(regime,sysFile,path);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "保存成功" : "保存失败");
        }catch (Exception e){
            e.printStackTrace();
            File file1 = new File(path,sysFile.getNowFileName());
            if(file1.exists()) file1.delete();
            json.setMessage("系统异常，请稍后再试");
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
            int result = iRegimeService.deleteArray(ids,path);
            json.setSuccess(result > 0);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统异常,请稍后再试");
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
        Regime regime = iRegimeService.select(id);
        SysFile sysFile = iSysFileService.select(regime.getAttachment());
        String fileName = sysFile.getOriginalFileName();
        /**
         * 文件下载
         */
        FileUtils.download(response,path+"/"+sysFile.getNowFileName(),fileName);
    }
}
