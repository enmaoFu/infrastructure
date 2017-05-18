package com.infrastructure.dao.culture;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.culture.Group;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 16:43
 */
@Repository
public interface GroupMapper extends BaseMapper<Group,String> {

    /**
     * 根据企业ID查询群组+分页
     * @param searchable
     * @param page
     * @return
     */
    List<Group> queryGroupPageMy(Group searchable, Pagination page);

    /**
     * 查询公司群组
     * @param searchable
     * @param page
     * @return
     */
    List<Group> queryGroupPageCompany(Group searchable, Pagination page);

    /**
     * 根据群组ID查询成员
     * @param groupId
     * @return
     */
    Group findGroupIdByUser(String groupId);


}
