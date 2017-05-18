package com.infrastructure.service.queryPerm;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.queryPerm.QueryUserPermissionsMapper;
import com.infrastructure.entity.queryPerm.QueryPermissions;
import com.infrastructure.entity.queryPerm.QueryUserPermissions;
import com.infrastructure.dao.queryPerm.QueryPermissionsMapper;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 查询权限-接口实现
 * User: suyl
 * Date: 2016/2/25
 */
@Service
public class QueryPermissionsService extends BaseService<QueryPermissions,String> implements IQueryPermissionsService{

    @Autowired
    private QueryPermissionsMapper queryPermissionsMapper;

    @Autowired
    private QueryUserPermissionsMapper queryUserPermissionsMapper;

    @Override
    public BaseMapper<QueryPermissions, String> getMapper() {
        return queryPermissionsMapper;
    }

    @Override
    public int addQueryPermissions(QueryPermissions queryPermissions) {
        return queryPermissionsMapper.insert(queryPermissions);
    }

    @Override
    public int modifyQueryPermissions(QueryPermissions queryPermissions) {
        return queryPermissionsMapper.update(queryPermissions);
    }

    @Override
    public int deleteQueryPermissions(String id) {
        return queryPermissionsMapper.delete(id);
    }

    @Override
    public int deleteBatchQueryPermissions(String[] ids) {
        return queryPermissionsMapper.deleteBatch(ids);
    }

    @Override
    public QueryPermissions getQueryPermissionsById(String id) {
        return queryPermissionsMapper.getById(id);
    }

    @Override
    public List<QueryPermissions> queryQueryPermissionsList(QueryPermissions searchable, Pagination page) {
        return queryPermissionsMapper.queryList(searchable, page);
    }

    @Override
    public List<QueryPermissions> queryPermissionsListByUser(Map<String, Object> maps) {
        return queryPermissionsMapper.queryPermissionsListByUser(maps);
    }

    @Override
    public int addQueryUserPermissions(QueryUserPermissions queryUserPermissions) {
        return queryUserPermissionsMapper.insert(queryUserPermissions);
    }
}
