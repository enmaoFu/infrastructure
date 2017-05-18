package com.infrastructure.dao.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.RoleResource;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * RoleMapper
 *
 * @author tyq
 * @date 2016/1/14
 */
@Repository
public interface RoleMapper extends BaseMapper<Role, String> {

    /**
     * 根据企业ID查询角色
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
     * 根据角色标识关联查询出拥有的角色名称
     *
     * @param roleIds
     * @return
     */
    Set<String> getRoles(String[] roleIds);

    /**
     * 根据角色标识关联查询出拥有的权限标识符
     *
     * @param roleIds
     * @return
     */
    Set<String> getPermissions(String[] roleIds);

    /**
     * 根据角色标识关联查询出按钮权限标识符
     *
     * @param roleIds
     * @return
     */
    Set<String> getButtonPermissions(String[] roleIds);

    /**
     * 批量更新指定角色以外的角色状态
     *
     * @param role
     * @return
     */
    int updateSateWithOutId(Role role);

    /**
     * 是否允许
     *
     * @param name
     * @return
     */
    boolean isExists(String name);

    /**
     * 查找角色标识
     *
     * @param name
     * @return
     */
    Set<String> findRoleIdsByName(String name);

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
     * @return
     */
    int authorization(List<RoleResource> list);

    /**
     * 根据角色ID删除角色权限
     * @param rId
     * @return
     */
    int deleteRoleResource(String rId);

    /**
     * 根据用户ID查询拥有的角色
     * @param uId
     * @return
     */
    List<String> getRoleByUid(String uId);

    /**
     * 根据角色ID批量删除角色权限
     * @param rid
     * @return
     */
    int deleteRoleResourceArray(String[] rid);

    /**
     * 根据userId查询部门编号
     * @param userId
     * @return
     */
    List<Role> queryRolesListByUserId(String userId);
}
