package com.infrastructure.service.queryPerm;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.queryPerm.QueryPermissions;
import com.infrastructure.entity.queryPerm.QueryUserPermissions;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 查询权限-接口
 * User: suyl
 * Date: 2016/2/25
 */
public interface IQueryPermissionsService extends IBaseService<QueryPermissions,String> {

    /**
     * 新增查询权限
     * @param queryPermissions
     * @return
     */
    int addQueryPermissions(QueryPermissions queryPermissions);

    /**
     * 修改查询权限
     * @param queryPermissions
     * @return
     */
    int modifyQueryPermissions(QueryPermissions queryPermissions);

    /**
     * 删除查询权限
     * @param id
     * @return
     */
    int deleteQueryPermissions(String id);

    /**
     * 批量删除查询权限
     * @param ids
     * @return
     */
    int deleteBatchQueryPermissions(String[] ids);

    /**
     * 得到查询权限详情
     * @param id
     * @return
     */
    QueryPermissions getQueryPermissionsById(String id);
    /**
     * 查询查询权限
     * @param searchable
     * @param page
     * @return
     */
    List<QueryPermissions> queryQueryPermissionsList(QueryPermissions searchable, Pagination page);

    /***
     * 用户查询权限查询
     * @param maps
     * @return
     */
    List<QueryPermissions> queryPermissionsListByUser(Map<String, Object> maps);

    int addQueryUserPermissions(QueryUserPermissions queryUserPermissions);

}
