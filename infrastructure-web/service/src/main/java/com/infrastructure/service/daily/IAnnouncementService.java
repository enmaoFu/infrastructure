package com.infrastructure.service.daily;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.daily.Announcement;
import com.infrastructure.entity.system.SysFile;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 通知公告
 * Created by 谭永强 on 2016/4/26.
 */
public interface IAnnouncementService extends IBaseService<Announcement,String> {


    /**
     * 根据企业ID查询通知公司
     * @param announcement
     * @param page
     * @return
     */
    List<Announcement> queryPage(Announcement announcement, Pagination page);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteArray(String[] ids, String path);

    /**
     * 发布
     * @param list
     * @return
     */
    int release(List<Announcement> list);

    /**
     * 查询本公司公告和关联公司公告
     * @param announcement
     * @param page
     * @return
     */
    List<Announcement> queryMyAndAll(Announcement announcement, Pagination page);

    /**
     * 通知公告添加
     * @param announcement
     * @param sysFile
     * @return
     */
    int insert(Announcement announcement, SysFile sysFile);

    /**
     * 修改
     * @param announcement
     * @param sysFile
     * @param path
     * @return
     */
    int update(Announcement announcement, SysFile sysFile, String path);

    /**
     * 根据模块id查询通知公告
     * model 模块ID
     * companyId 公司ID
     * @return
     */
    Announcement queryByModel(Map<String, Object> map);
}
