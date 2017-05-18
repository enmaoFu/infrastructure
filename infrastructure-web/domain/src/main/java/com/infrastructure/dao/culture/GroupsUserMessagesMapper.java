package com.infrastructure.dao.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.culture.GroupsUserMessages;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/2/19
 * Time: 12:37
 */
@Repository
public interface GroupsUserMessagesMapper extends BaseMapper<GroupsUserMessages,String> {

    /**
     * 查询群组消息记录
     * @param groupId
     * @return
     */
    List<GroupsUserMessages> queryMessage(String groupId);

    /**
     * 根据群组用户ID删除消息记录
     * @param list
     * @return
     */
    int deleteArray(List<String> list);
}
