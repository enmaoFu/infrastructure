package com.infrastructure.dao.queryPerm;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.queryPerm.QueryPermissions;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by suyl on 2016/2/25.
 * 查询权限
 */
@Repository
public interface QueryPermissionsMapper extends BaseMapper<QueryPermissions, String> {

    /**
     * 新增
     * @param queryPermissions
     * @return
     */
    int insert(QueryPermissions queryPermissions);

    /**
     * 修改
     * @param queryPermissions
     * @return
     */
    int update(QueryPermissions queryPermissions);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(String[] ids);

    /**
     * 得到详情
     * @param targetId
     * @return
     */
    QueryPermissions getById(String targetId);

    /**
     * 分页
     * @return
     */
    List<QueryPermissions> queryList(Searchable searchable, Pagination page);

    /***
     * 用户查询权限查询
     * @param maps
     * @return
     */
    List<QueryPermissions> queryPermissionsListByUser(Map<String, Object> maps);

}
