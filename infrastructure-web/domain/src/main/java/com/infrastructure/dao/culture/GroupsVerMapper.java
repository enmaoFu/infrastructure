package com.infrastructure.dao.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.culture.GroupsVerification;
import net.oschina.archx.mybatis.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tyq on 16/3/12.
 */
@Repository
public interface GroupsVerMapper extends BaseMapper<GroupsVerification,String> {

    /**
     * 申请入群消息查询+分页
     * @param searchable
     * @param page
     * @return
     */
    List<GroupsVerification> queryPage(GroupsVerification searchable, Pagination page);

    /**
     * 根据群组ID和用户ID查询申请记录
     * @param gv
     * @return
     */
    List<GroupsVerification> findByGidAndUid(GroupsVerification gv);

    /**
     * 删除验证信息
     * @param ids
     * @return
     */
    int deleteArray(@Param("ids") String[] ids);

    /**
     * 查询待处理验证消息
     * @param userId
     * @return
     */
    List<GroupsVerification> queryPending(@Param("userId") String userId);
}
