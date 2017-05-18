package com.infrastructure.service.system;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.Resource;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Set;

/**
 * IResourceService
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface IResourceService extends IBaseService<Resource, String> {

    /**
     * 查找所有资源
     *
     * @return
     */
    List<Resource> findAll();

    /**
     * 查找所有资源
     *
     * @param page
     * @return
     */
    List<Resource> findAll(Pagination page);


    /**
     * 获取菜单
     * <p/>
     * 暂定方法
     *
     * @return
     */
    List<Resource> getMenus();

    /**
     * 根据权限获取菜单
     *
     * @param permissions
     * @return
     */
    List<Resource> getMenus(Set<String> permissions);

    /**
     * 根据权限获取菜单
     *
     * @param permissions 权限
     * @param btn 是否包含按钮
     * @return
     */
    List<Resource> getMenus(Set<String> permissions, boolean btn);

    /**
     * 根据角色ID查询角色所拥有的权限
     * @param rid
     * @return
     */
    List<String> findRidByResource(String rid);

    /**
     * 根据用户ID查询url非空的菜单
     * @param id
     * @return
     */
    List<Resource> queryUrlNotNull(String id);
}
