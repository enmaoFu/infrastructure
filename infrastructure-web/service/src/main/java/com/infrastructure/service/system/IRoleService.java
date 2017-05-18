package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.RoleResource;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Set;

/**
 * IRoleService
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface IRoleService extends IBaseService<Role, String> {

    /**
     * 根据企业ID查询角色
     *
     * @return
     */
    List<Role> findAll(Searchable searchable, Pagination page);

    /**
     * 角色关联资源菜单
     *
     * @param items
     * @return
     */
    int contactWithResources(List<RoleResource> items);

    /**
     * 清空角色资源菜单关联
     *
     * @param id
     * @return
     */
    int unContactWithResources(String id);

    /**
     * 获取拥有的资源标识
     *
     * @param id
     * @return
     */
    Set<String> getOwnedResourceIds(String id);

    /**
     * 根据角色标识关联查询出拥有的权限标识符
     *
     * @param roleIds
     * @return
     */
    Set<String> getPermissions(Set<String> roleIds);

    /**
     * 复制角色
     *
     * @param id   源角色标识
     * @param role 新角色信息
     * @return
     */
    int copyRole(String id, Role role);

    /**
     * 保存或更新角色
     *
     * @param role
     * @return
     */
    int saveOrUpdate(Role role);

    /**
     * 是否允许
     *
     * @param name
     * @return
     */
    boolean isAllowed(String name);

    /**
     * 更新系统管理员资源
     *
     * @param roleName 角色名称
     * @return
     */
    int updateSystemAdminResources(String roleName);

    /**
     * 查找指派角色
     *
     * @param role
     * @return
     */
    List<Role> findAssignRoles(Role role);

    /**
     * 根据企业ID查询角色
     * @param companyId
     * @return
     */
    List<Role> findByCid(String companyId);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int deleteArray(String[] ids);

    /**
     * 角色赋权
     * @param ids
     * @return
     */
    int authorization(String[] ids, String rId);

    /**
     * 根据用户ID查询拥有的角色
     * @param uId
     * @return
     */
    List<String> getRoleByUid(String uId);

    /**
     * 根据userId查询部门编号
     * @param userId
     * @return
     */
    List<Role> queryRolesListByUserId(String userId);
}
