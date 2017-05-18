package com.infrastructure.service.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.dao.culture.GroupMapper;
import com.infrastructure.dao.culture.GroupsUserMapper;
import com.infrastructure.dao.culture.GroupsUserMessagesMapper;
import com.infrastructure.entity.culture.Group;
import com.infrastructure.entity.culture.GroupsUser;
import com.infrastructure.entity.culture.GroupsUserMessages;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 16:42
 */
@Service
public class GroupService extends BaseService<Group, String> implements IGroupService {

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupsUserMapper groupsUserMapper;
    @Autowired
    private GroupsUserMessagesMapper groupsUserMessagesMapper;

    @Override
    public BaseMapper<Group, String> getMapper() {
        return groupMapper;
    }

    /**
     * 根据企业ID查询群组+分页
     *
     * @param searchable
     * @param page
     * @return
     */
    @Override
    public List<Group> queryGroupPageMy(Group searchable, Pagination page) {
        return groupMapper.queryGroupPageMy(searchable, page);
    }

    /**
     * 查询公司群组
     * @param searchable
     * @param page
     * @return
     */
    @Override
    public List<Group> queryGroupPageCompany(Group searchable, Pagination page) {
        return groupMapper.queryGroupPageCompany(searchable, page);
    }

    /**
     * 创建群组
     *
     * @param group
     * @param userIds
     * @return
     */
    @Override
    public int insert(Group group, List<String> userIds) {
        int result = groupMapper.insert(group);
        List<GroupsUser> list = new ArrayList<GroupsUser>();
        for (String userId : userIds) {
            GroupsUser groupsUser = new GroupsUser();
            groupsUser.setId(IdKeyGenerator.uuid());
            groupsUser.setGroupId(group.getId());
            groupsUser.setUserId(userId);
            list.add(groupsUser);
        }
        result = groupsUserMapper.insertArray(list);
        return result;
    }

    /**
     * 群组删除
     *
     * @param uId 当前用户ID
     * @param ids 待删除数据ID
     * @return
     */
    @Override
    public int deleteArray(String uId,String[] ids) {
        int result = -1;
        for (String id : ids) {
            Group group = groupMapper.select(id);
            if (group.getCreateUser().equals(uId)){
                //此群组是当前用户创建-物理删除
                result = groupMapper.delete(id);
                //查询群组用户ID
                List<String> list = groupsUserMapper.selectList(id);
                //删除消息记录
                result = groupsUserMessagesMapper.deleteArray(list);
                //删除群成员-物理删除
                result = groupsUserMapper.deleteByGroupId(id);
            }else {
                //当前用户是此群组的一个成员-逻辑删除
                result = groupsUserMapper.delete(uId,id);
            }
        }
        return result;
    }

    /**
     * 根据群组ID查询成员
     * @param groupId
     * @return
     */
    @Override
    public Group findGroupIdByUser(String groupId) {
        return groupMapper.findGroupIdByUser(groupId);
    }

    /**
     * 根据群组ID和用户ID查询群组用户数据
     * @param groupId
     * @param userId
     * @return
     */
    @Override
    public GroupsUser findGroupIdAndUserId(String groupId, String userId) {
        return groupsUserMapper.findGroupIdAndUserId(groupId,userId);
    }

    /**
     * 消息记录新增
     * @param id
     * @param message
     */
    @Override
    public void insertMessages(String id, String message) {
        GroupsUserMessages groupsUserMessages = new GroupsUserMessages();
        groupsUserMessages.setId(IdKeyGenerator.uuid());
        groupsUserMessages.setGroup_user_id(id);
        groupsUserMessages.setContent(message);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
        groupsUserMessages.setSendDate(sdf.format(Calendar.getInstance().getTime()));
        groupsUserMessagesMapper.insert(groupsUserMessages);
    }

    /**
     * 查询群组消息记录
     * @param groupId
     * @return
     */
    @Override
    public List<GroupsUserMessages> queryMessage(String groupId) {
        return groupsUserMessagesMapper.queryMessage(groupId);
    }

    /**
     * 根据群组ID查询群组成员
     * @param groupId
     * @return
     */
    @Override
    public List<GroupsUser> findGroupUser(String groupId) {
        return groupsUserMapper.findGroupUser(groupId);
    }

    /**
     * 群组编辑
     * @param group
     * @param list
     * @return
     */
    @Override
    public int update(Group group, List<String> list) {
        int result = groupMapper.update(group);
        List<String> groupUserId = groupsUserMapper.selectUserId(group.getId());
        //待新增
        List<String> addId = new ArrayList<>();
        //待删除
        List<String> delId = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        for (String s : groupUserId) {
            map.put(s,1);
        }
        for (String s : list) {
            if(map.get(s) != null){
                //不变
                map.put(s,2);
                continue;
            }
            addId.add(s);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue() == 1){
                delId.add(entry.getKey());
            }
        }
        if(delId.size() > 0){
            delId = groupsUserMapper.queryGroupUserId(group.getId(),delId);
            //删除群组用户数据
            result = groupsUserMapper.deleteArray(delId);
            //根据群组用户ID删除消息记录
            //result = groupsUserMessagesMapper.deleteArray(delId);
        }
        if(addId.size() > 0){
            List<GroupsUser> groupsUserList = new ArrayList<>();
            for (String userId : addId) {
                GroupsUser gu = groupsUserMapper.findGroupIdAndUserId(group.getId(),userId);
                if(gu != null){
                    //此成员已被标记为逻辑删除成员
                    result = groupsUserMapper.update(gu);
                }else {
                    gu = new GroupsUser();
                    gu.setId(IdKeyGenerator.uuid());
                    gu.setGroupId(group.getId());
                    gu.setUserId(userId);
                    groupsUserList.add(gu);
                }
            }
            if(groupsUserList.size() > 0){
                result = groupsUserMapper.insertArray(groupsUserList);
            }
        }
        return result;
    }

}
