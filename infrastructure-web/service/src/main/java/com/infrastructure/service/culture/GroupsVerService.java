package com.infrastructure.service.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.dao.culture.GroupsUserMapper;
import com.infrastructure.dao.culture.GroupsVerMapper;
import com.infrastructure.entity.culture.GroupsUser;
import com.infrastructure.entity.culture.GroupsVerification;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyq on 16/3/12.
 */
@Service
public class GroupsVerService extends BaseService<GroupsVerification,String> implements IGroupsVerService {

    @Autowired
    private GroupsVerMapper groupsVerMapper;
    @Autowired
    private GroupsUserMapper groupsUserMapper;

    @Override
    public BaseMapper<GroupsVerification, String> getMapper() {
        return groupsVerMapper;
    }

    /**
     * 申请入群消息查询+分页
     * @param searchable
     * @param page
     * @return
     */
    @Override
    public List<GroupsVerification> queryPage(GroupsVerification searchable, Pagination page) {
        return groupsVerMapper.queryPage(searchable,page);
    }

    /**
     * 根据群组ID和用户ID查询申请记录
     * @param gv
     * @return
     */
    @Override
    public List<GroupsVerification> findByGidAndUid(GroupsVerification gv) {
        return groupsVerMapper.findByGidAndUid(gv);
    }


    /**
     * 删除验证信息
     * @param ids
     * @return
     */
    @Override
    public int deleteArray(String[] ids) {
        return groupsVerMapper.deleteArray(ids);
    }

    /**
     * 查询待处理验证消息
     * @param userId
     * @return
     */
    @Override
    public List<GroupsVerification> queryPending(String userId) {
        return groupsVerMapper.queryPending(userId);
    }

    /**
     * 修改验证信息
     * @param gv
     * @return
     */
    @Override
    public int update(GroupsVerification gv) {
        GroupsVerification grv = groupsVerMapper.select(gv.getId());
        GroupsUser gu = new GroupsUser();
        gu.setId(IdKeyGenerator.uuid());
        gu.setGroupId(grv.getGroupId());
        gu.setUserId(grv.getApplyUser());
        int result = groupsVerMapper.update(gv);
        if("Y".equals(gv.getIsPass())){
            GroupsUser groupsUser = groupsUserMapper.findGroupIdAndUserId(gu.getGroupId(),gu.getUserId());
            if(groupsUser != null){
                result = groupsUserMapper.update(groupsUser);
            }else {
                result = groupsUserMapper.insert(gu);
            }
        }
        return result;
    }
}
