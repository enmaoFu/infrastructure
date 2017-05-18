package com.infrastructure.dao.daily;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.daily.Announcement;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 通知公告
 * Created by 谭永强 on 2016/4/26.
 */
@Repository
public interface AnnouncementMapper extends BaseMapper<Announcement,String> {

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
    int deleteArray(String[] ids);

    /**
     * 查询本公司公告和关联公司公告
     * @param announcement
     * @param page
     * @return
     */
    List<Announcement> queryMyAndAll(Announcement announcement, Pagination page);

    /**
     * 根据模块id查询列表信息
     * model 模块ID
     * companyId 公司ID
     * @return
     */
    Announcement queryByModel(Map<String, Object> map);
}
