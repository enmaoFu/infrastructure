package com.infrastructure.service.daily;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.daily.AnnouncementMapper;
import com.infrastructure.dao.system.SysFileMapper;
import com.infrastructure.entity.daily.Announcement;
import com.infrastructure.entity.system.SysFile;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 通知公告
 * Created by 谭永强 on 2016/4/26.
 */
@Service
public class AnnouncementService extends BaseService<Announcement,String> implements IAnnouncementService{

    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public BaseMapper<Announcement, String> getMapper() {
        return announcementMapper;
    }

    /**
     * 根据企业ID查询通知公司
     * @param announcement
     * @param page
     * @return
     */
    @Override
    public List<Announcement> queryPage(Announcement announcement, Pagination page) {
        return announcementMapper.queryPage(announcement,page);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public int deleteArray(String[] ids,String path) {
        for (String id : ids) {
            //查询申请数据
            Announcement ann = super.select(id);
            //查询附件
            SysFile sysFile = sysFileMapper.select(ann.getAttachment());
            if(sysFile != null){
                File file = new File(path,sysFile.getNowFileName());
                //删除附件数据
                sysFileMapper.delete(ann.getAttachment());
                file.delete();
            }
        }
        return announcementMapper.deleteArray(ids);
    }

    /**
     * 发布
     * @param list
     * @return
     */
    @Override
    public int release(List<Announcement> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            result = super.update(list.get(i));
        }
        return result;
    }

    /**
     * 查询本公司公告和关联公司公告
     * @param announcement
     * @param page
     * @return
     */
    @Override
    public List<Announcement> queryMyAndAll(Announcement announcement, Pagination page) {
        return announcementMapper.queryMyAndAll(announcement,page);
    }

    /**
     * 通知公告添加
     * @param announcement
     * @param sysFile
     * @return
     */
    @Override
    public int insert(Announcement announcement, SysFile sysFile) {
        if(sysFile != null && sysFile.getId() != null){
            //保存文件信息
            sysFileMapper.insert(sysFile);
        }
        return super.insert(announcement);
    }

    /**
     * 修改
     * @param announcement
     * @param sysFile
     * @param path
     * @return
     */
    @Override
    public int update(Announcement announcement, SysFile sysFile, String path) {
        Announcement ann = super.select(announcement.getId());
        String attachmentId = ann.getAttachment();
        if(!StringUtils.isEmpty(sysFile.getId())){
            if(!StringUtils.isEmpty(attachmentId) && !"1".equals(attachmentId)){
                SysFile sFile = sysFileMapper.select(attachmentId);
                File file = new File(path,sFile.getNowFileName());
                if(file.exists()) file.delete();
                sysFileMapper.delete(attachmentId);
            }
            if(!"1".equals(sysFile.getId())){
                sysFileMapper.insert(sysFile);
            }
        }
        return super.update(announcement);
    }

    /**
     * 根据模块ID查询通知公告
     * model 模块ID
     * companyId 公司ID
     * @param map
     * @return
     */
    @Override
    public Announcement queryByModel(Map<String,Object> map) {
        return announcementMapper.queryByModel(map);
    }
}
