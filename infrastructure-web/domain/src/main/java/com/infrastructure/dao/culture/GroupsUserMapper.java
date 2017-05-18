package com.infrastructure.dao.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.culture.GroupsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 17:24
 */
@Repository
public interface GroupsUserMapper extends BaseMapper<GroupsUser,String> {

    /**
     * 群组成员新增
     * @param list
     * @return
     */
    int insertArray(List<GroupsUser> list);

    /**
     * 群组成员删除
     * @param id
     * @return
     */
    int deleteByGroupId(String id);

    /**
     * 逻辑删除
     * 根据用户ID和群组ID删除群组成员
     * @param uId 用户ID
     * @param groupId 群组ID
     * @return
     */
    int delete(@Param("uId") String uId, @Param("groupId") String groupId);

    /**
     * 根据群组ID和用户ID查询群组用户数据
     * @param groupId
     * @param userId
     * @return
     */
    GroupsUser findGroupIdAndUserId(@Param("groupId") String groupId, @Param("userId") String userId);

    /**
     * 根据群组ID查询群组用户ID
     * @param groupId
     * @return
     */
    List<String> selectList(String groupId);

    /**
     * 根据群组ID查询群组成员
     * @param groupId
     * @return
     */
    List<GroupsUser> findGroupUser(@Param("groupId") String groupId);

    /**
     *
     * @param delId
     * @return
     */
    int deleteArray(List<String> delId);

    /**
     * 根据群组ID查询群组成员ID
     * @param id
     * @return
     */
    List<String> selectUserId(String id);

    /**
     * 根据群组ID和用户ID查询群组用户ID
     * @param groupId
     * @param userIds
     * @return
     */
    List<String> queryGroupUserId(@Param("groupId") String groupId, @Param("userIds") List<String> userIds);
}
