package com.infrastructure.service.culture;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.culture.Group;
import com.infrastructure.entity.culture.GroupsUser;
import com.infrastructure.entity.culture.GroupsUserMessages;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 16:42
 */
public interface IGroupService extends IBaseService<Group,String> {

    /**
     * 根据企业ID查询群组+分页
     * @param searchable
     * @param page
     * @return
     */
    List<Group> queryGroupPageMy(Group searchable, Pagination page);

    /**
     * 查询公司群组+分页
     * @param searchable
     * @param page
     * @return
     */
    List<Group> queryGroupPageCompany(Group searchable, Pagination page);

    /**
     * 群组创建
     * @param group
     * @param userIds
     * @return
     */
    int insert(Group group, List<String> userIds);

    /**
     * 群组删除
     * @param ids
     * @return
     */
    int deleteArray(String uId, String[] ids);

    /**
     * 根据群组ID查询成员
     * @param groupId
     * @return
     */
    Group findGroupIdByUser(String groupId);

    /**
     * 根据群组ID和用户ID查询群组用户数据
     * @param groupId
     * @param userId
     * @return
     */
    GroupsUser findGroupIdAndUserId(String groupId, String userId);

    /**
     * 新增消息记录
     * @param id
     * @param message
     */
    void insertMessages(String id, String message);

    /**
     * 查询群组消息记录
     * @param groupId
     * @return
     */
    List<GroupsUserMessages> queryMessage(String groupId);

    /**
     * 根据群组ID查询群组成员
     * @param groupId
     * @return
     */
    List<GroupsUser> findGroupUser(String groupId);

    /**
     * 群组编辑
     * @param group
     * @param list
     * @return
     */
    int update(Group group, List<String> list);

}
