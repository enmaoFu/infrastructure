package com.infrastructure.dao.queryPerm;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.queryPerm.QueryUserPermissions;
import org.springframework.stereotype.Repository;

/**
 * Created by suyl on 2016/2/25.
 */
@Repository
public interface QueryUserPermissionsMapper extends BaseMapper<QueryUserPermissions, String> {

    /**
     * 新增
     * @param queryUserPermissions
     * @return
     */
    int insert(QueryUserPermissions queryUserPermissions);

}
