package com.infrastructure.dao.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.Resource;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ResourceMapper
 *
 * @author tyq
 * @date 2016/1/14
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource, String> {

    /**
     * 查询所有资源
     *
     * @return
     */
    List<Resource> findAll();

    /**
     * 查询所有资源
     *
     * @param page 分页模型
     * @return
     */
    List<Resource> findAll(Pagination page);

    /**
     * 根据角色ID查询角色所拥有的权限
     * @param rid
     * @return
     */
    List<String> findRidByResource(String rid);

    /**
     * 根据菜单ID删除角色菜单中间表数据
     * @param id
     * @return
     */
    int deleteRoleResource(String id);

    /**
     * 根据用户ID查询url非空的菜单
     * @param id
     * @return
     */
    List<Resource> queryUrlNotNull(String id);
}
