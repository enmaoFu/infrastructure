package com.infrastructure.web.order;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.infrastructure.entity.order.InstFeedback;
import com.infrastructure.entity.order.InstReceiveObj;
import com.infrastructure.entity.system.SysFile;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.service.order.IInstFeedbackService;
import com.infrastructure.service.order.IInstReceiveObjService;
import com.infrastructure.spring.bind.annotation.SearchParam;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.common.utils.Json;
import com.extra.jquery.model.JQueryDataGrid;
import com.extra.jquery.model.Page;
import com.infrastructure.entity.order.Instructions;
import com.infrastructure.service.order.IInstructionsService;
import com.infrastructure.service.system.ISysFileService;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import com.infrastructure.utils.FileUtils;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/**
 * 指令
 * Created by suyl on 2016/6/22
 */

@Controller
@RequestMapping("/instructions")
public class InstructionsContoller {

    @Autowired
    private IInstFeedbackService iInstFeedbackService;

    @Autowired
    private IInstReceiveObjService iInstReceiveObjService;

    @Autowired
    private IInstructionsService iInstructionsService;

    @Autowired
    private ISysFileService iSysFileService;

    /**
     * 合同管理页面跳转
     * @return
     */
    @RequestMapping(value = "/manage")
    public String index(String model, RedirectAttributes attr){
        //新增记录用户登录/模块点击时间
        attr.addAttribute("model",model);
        attr.addAttribute("url","order/instructions_manage");
        return "redirect:/userLoginTime/manage";
    }


    /**
     * 分页显示发出指令列表信息
     */
    @RequestMapping(value = "/loadInstructions",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<Instructions> loadIndex(Page pager, String order, String listState, String content, String stertsignDate, String endsignDate, @SearchParam(Instructions.class) Instructions searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(!StringUtils.isEmpty(content)){
            date+=" AND c.content LIKE '%"+content+"%'";
        }
        if(!"".equals(stertsignDate) && stertsignDate != null){
            date+=" AND c.createTime > CONCAT('"+stertsignDate+"',' 00:00:00')";
        }
        if(!"".equals(endsignDate) && endsignDate != null) {
            date += " AND c.createTime < CONCAT('" + endsignDate + "',' 23:59:59')";
        }
        List<Instructions> list = null;
        if(listState.equals("1")){
            date += " AND c.createUser = '"+user.getId()+"'";
            searchable.setWhere(" "+date + " order by c.createTime "+order+"");
            list = iInstructionsService.queryInstructionsList(searchable,page);
        }else if(listState.equals("2")){
            date += " AND obj.userId = '"+user.getId()+"'";
            searchable.setWhere(" "+date + " order by c.createTime "+order+"");
            list = iInstructionsService.queryInstReceiveObjList(searchable,page);
        }else if(listState.equals("3")){
            date += " AND obj.userId = '"+user.getId()+"' AND obj.forward = 'Y'";
            searchable.setId(user.getId());
            searchable.setWhere(" "+date + " order by c.createTime "+order+"");
            list = iInstructionsService.queryForwardList(searchable,page);
        }
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 分页显示接受指令信息
     */
    @RequestMapping(value = "/loadInstReceiveObj",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<InstReceiveObj> loadInstReceiveObj(Page pager, String order, String instId, String forward, String content, String stertsignDate, String endsignDate, @SearchParam(InstReceiveObj.class) InstReceiveObj searchable, @CurrentUser SysUser user) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(!StringUtils.isEmpty(content)){
            date+=" AND c.content LIKE '%"+content+"%'";
        }
        if(!StringUtils.isEmpty(forward)){
            date+=" AND c.forward = '"+forward+"'";
        }
        if(!StringUtils.isEmpty(instId)){
            date+=" AND c.instId = '"+instId+"'";
        }else {
            date += " AND i.userId = '"+user.getId()+"'";
        }
        if(!"".equals(stertsignDate) && stertsignDate != null){
            date+=" AND i.createTime > CONCAT('"+stertsignDate+"',' 00:00:00')";
        }
        if(!"".equals(endsignDate) && endsignDate != null) {
            date += " AND i.createTime < CONCAT('" + endsignDate + "',' 23:59:59')";
        }
        searchable.setWhere(" "+date + "  order by i.createTime "+order+"");
        List<InstReceiveObj> list = iInstReceiveObjService.queryInstReceiveObjList(searchable, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 分页显示反馈列表信息
     */
    @RequestMapping(value = "/loadInstFeedback",method = RequestMethod.POST)
    @ResponseBody
    public JQueryDataGrid<InstFeedback> loadInstFeedback(Page pager, String order, String instId, @SearchParam(InstFeedback.class) InstFeedback searchable) {
        // 准备数据
        Pagination page = new Pagination(pager.getPage(), pager.getRows());
        String date = "";
        if(!StringUtils.isEmpty(instId)){
            date+=" AND c.instid = '"+instId+"'";
        }
        searchable.setWhere(" "+date + " order by c.createTime "+order+"");
        List<InstFeedback> list = iInstFeedbackService.queryInstFeedbackList(searchable, page);
        JQueryDataGrid dataGrid = new JQueryDataGrid(page.getTotal(),list);
        return dataGrid;
    }

    /**
     * 新增发起指令
     * @param instructions
     * @param user
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@Valid Instructions instructions,@RequestParam("userIds") String[] userIds, @CurrentUser SysUser user, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        SysFile sysFile = new SysFile();
        Json json = new Json();
        try {
            FileUtils.setSysFile(sysFile,file,path);
            if(sysFile != null && sysFile.getNowFileName() != null){
                sysFile.setId(IdKeyGenerator.uuid());
                sysFile.setAttachmentId(IdKeyGenerator.uuid());
                instructions.setAttachment(sysFile.getAttachmentId());
            }
            //获取id
            instructions.setId(IdKeyGenerator.uuid());
            instructions.setCreateuser(user.getId());
            if(StringUtils.isEmpty(instructions.getCompanyid())){
                instructions.setCompanyid(user.getCompanyId());
            }
            //完成状态Y/N 已完成/未完成
            instructions.setState("N");
            int ret = iInstructionsService.addInstructions(instructions,sysFile);
            if(ret > 0){
                if(userIds != null && userIds.length != 0){
                    List<InstReceiveObj> list = new ArrayList<>();
                    for(String id:userIds){
                        InstReceiveObj instReceiveObj = new InstReceiveObj();
                        instReceiveObj.setId(IdKeyGenerator.uuid());
                        instReceiveObj.setInstid(instructions.getId());
                        instReceiveObj.setUserid(id);
                        instReceiveObj.setForward("N");
                        instReceiveObj.setParentid("");
                        list.add(instReceiveObj);
                    }
                    ret = iInstReceiveObjService.addInstReceiveObj(list);
                }
            }
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }


    /**
     * 转发
     * @param instReceiveObj
     * @param user
     * @return
     */
    @RequestMapping(value = "/insertForward", method = RequestMethod.POST)
    @ResponseBody
    public String insertForward(@Valid InstReceiveObj instReceiveObj,@RequestParam("userIds") String[] userIds, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            int ret = 0;
            if(userIds != null && userIds.length != 0){
                List<InstReceiveObj> list = new ArrayList<>();
                for(String id:userIds){
                    InstReceiveObj obj = new InstReceiveObj();
                    obj.setId(IdKeyGenerator.uuid());
                    obj.setInstid(instReceiveObj.getInstid());
                    obj.setUserid(id);
                    obj.setForward("N");
                    obj.setParentid(instReceiveObj.getParentid());
                    list.add(obj);
                }
                ret = iInstReceiveObjService.addInstReceiveObj(list);
                if(ret > 0){
                    InstReceiveObj inst = new InstReceiveObj();
                    inst.setId(instReceiveObj.getId());
                    inst.setForward("Y");
                    ret = iInstReceiveObjService.modifyInstReceiveObj(inst);
                }
            }
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "转发成功":"转发失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
    /**
     * 新增回馈
     * @param instFeedback
     * @param user
     * @return
     */
    @RequestMapping(value = "/insertInstFeedback", method = RequestMethod.POST)
    @ResponseBody
    public String insertInstFeedback(@Valid InstFeedback instFeedback, @CurrentUser SysUser user) {
        Json json = new Json();
        try {
            //获取id
            instFeedback.setId(IdKeyGenerator.uuid());
            instFeedback.setUserid(user.getId());
            int ret = iInstFeedbackService.addInstFeedback(instFeedback);
            json.setSuccess(ret > 0);
            json.setMessage(ret > 0 ? "新增成功":"新增失败");
        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
    /**
     * 更新发起指令
     *
     * @param instructions
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid Instructions instructions,@RequestParam("userIds") String[] userIds, @CurrentUser SysUser user, HttpServletRequest request, @RequestParam("file") MultipartFile file, String fileName) {
        Json json = new Json();
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        SysFile sysFile = new SysFile();
        try {
            FileUtils.setSysFile(sysFile,file,path);
            if(sysFile != null && sysFile.getNowFileName() != null){
                sysFile.setId(IdKeyGenerator.uuid());
                sysFile.setAttachmentId(IdKeyGenerator.uuid());
                instructions.setAttachment(sysFile.getAttachmentId());
            }
            if(org.springframework.util.StringUtils.isEmpty(fileName)){
                sysFile.setId("1");
                instructions.setAttachment("1");
            }
            int result = iInstructionsService.modifyInstructions(instructions,sysFile,path);
            if(result > 0){
                if(userIds != null && userIds.length != 0){
                    String[] ss = {instructions.getId()};
                    result =  iInstReceiveObjService.deleteBatchInstReceiveObj(ss);
                    if(result > 0){
                        List<InstReceiveObj> list = new ArrayList<>();
                        for(String userId:userIds){
                            InstReceiveObj instReceiveObj = new InstReceiveObj();
                            instReceiveObj.setId(IdKeyGenerator.uuid());
                            instReceiveObj.setInstid(instructions.getId());
                            instReceiveObj.setUserid(userId);
                            instReceiveObj.setParentid("");
                            instReceiveObj.setForward("N");
                            list.add(instReceiveObj);
                        }
                        result = iInstReceiveObjService.addInstReceiveObj(list);
                    }
                }
            }
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "更新成功":"更新失败");

        }catch (Exception e){
            json.setMessage("系统繁忙，请稍后再试");
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }

    /**
     * 根据项目管理ID查询实际成本信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public String findById(String id){
        Instructions instructions = iInstructionsService.getInstructionsById(id);

        return JSON.toJSONString(instructions);
    }

    /**
     * 文件下载
     * @param id
     */
    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id){
        response.setCharacterEncoding("UTF-8");
        Instructions instructions = iInstructionsService.getInstructionsById(id);
        String path = request.getServletContext().getRealPath("/")+"upload/"+instructions.getCompanyid();
        SysFile sysFile = iSysFileService.select(instructions.getAttachment());
        String fileName = sysFile.getOriginalFileName();
        /**
         * 文件下载
         */
        FileUtils.download(response,path+"/"+sysFile.getNowFileName(),fileName);
    }
    /**
     * 删除项目管理信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request, @CurrentUser SysUser user,@RequestParam("ids[]") String[] ids) {
        String path = request.getServletContext().getRealPath("/") + "upload/"+user.getCompanyId();
        Json json = new Json();
        try{
            int result = iInstructionsService.deleteBatchInstructions(ids,path);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "删除成功" : "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }

    /**
     * 删除项目管理信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    @ResponseBody
    public String confirm(@CurrentUser SysUser user,@RequestParam("ids[]") String[] ids) {
        Json json = new Json();
        try{
            Map<String,Object> maps = new HashMap<>();
            maps.put("ids",ids);
            iInstructionsService.confirm(maps);
            int result = iInstructionsService.confirm(maps);
            json.setSuccess(result > 0?true:false);
            json.setMessage(result > 0 ? "确认完成成功" : "确认完成失败");
        }catch (Exception e){
            e.printStackTrace();
            json.setMessage("系统繁忙,请稍后再试");
        }
        return JSON.toJSONString(json);
    }
}
