package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.system.SysUserRole;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Set;

/**
 * ISysUserService
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface ISysUserService extends IBaseService<SysUser, String> {

    /**
     * 获取所有系统用户
     *
     * @return
     */
    List<SysUser> findAll();

    /**
     * 查找用户
     *
     * @param searchable 搜索条件
     * @param page       分页条件
     * @return
     */
    List<SysUser> findSysUsers(Searchable searchable, Pagination page);

    /**
     * 关联角色
     *
     * @param id      用户标识
     * @param roleIds 角色标识数组
     * @return
     */
    int contactWithRoles(String id, String[] roleIds);

    /**
     * 获取拥有的角色标识
     *
     * @param userId
     * @return
     */
    Set<String> getOwnedRoleIds(String userId);

    /**
     * 根据登录名获取角色名称
     *
     * @param login
     * @return
     */
    Set<String> getRoles(String login);

    /**
     * 根据登录名获取权限字符串
     *
     * @param login
     * @return
     */
    Set<String> getPermissions(String login);

    /**
     * 根据用户标识获取按钮权限字符串
     *
     * @param id
     * @return
     */
    Set<String> getButtonPermissions(String id);

    /**
     * 根据用户名查询用户信息
     *
     * @param login
     * @return
     */
    SysUser getUser(String login);

    /**
     * 根据电话号码查询用户
     *
     * @param sysUser
     * @return
     */
    SysUser queryUserByPhone(SysUser sysUser);

    /**
     * 根据用户的Id集合查询用户信息
     *
     * @param list
     * @return
     */
    List<SysUser> queryInstallationIdByUserIds(List<String> list);

    /**
     * 关联角色
     *
     * @param list 关联列表
     * @return
     */
    int contactWithRoles(List<SysUserRole> list);

    /**
     * 清空角色关联
     *
     * @param userId
     * @return
     */
    int unContactWithRoles(String userId);

    /**
     * 根据学校标识查找系统用户
     *
     * @param schId
     * @return
     */
    List<SysUser> findAllSysUsersBySchId(String schId);

    /**
     * 查找用户
     *
     * @param sysUser
     * @return
     */
    List<SysUser> searchUser(SysUser sysUser);

    /**
     * 根据用户ID查询用户表所有数据
     *
     * @param sysUser
     * @return
     */
    SysUser queryUserById(SysUser sysUser);

    /**
     * 判断登录名是否存在
     *
     * @param login 登录名
     * @return
     */
    boolean isExists(String login);

    /**
     * 用户删除
     * @param ids ID数组
     * @return
     */
    int deleteArray(String[] ids);

    /**
     * 根据角色ID查询用户
     * @param rid
     * @return
     */
    List<SysUser> findByRid(String rid);

    /**
     * 根据单位ID查询用户
     * @param companyId
     * @return
     */
    List<SysUser> findByCid(String companyId);

    /**
     * 根据部门ID查询用户
     * @param deptId
     * @return
     */
    List<SysUser> findByDeptId(String deptId);

    /**
     * 根据父类id查询用户子类信息
     * @param userId
     * @return
     */
    List<SysUser> searchUserByUserId(String userId);

    /**
     * 根据查询条件查询用户
     * @return
     */
    List<SysUser> getUserByCompanyDept(SysUser user);

    /**
     * 根据登录名查询用户
     * @param login
     * @return
     */
    SysUser findByLogin(String login);
    /**
     * 根据ID批量查询数据
     * @param ids
     * @return
     */
    List<SysUser> getSysUserByIds(String[] ids);
    /**
     * 根据ID批量查询用户的名字集合
     * @param ids
     * @return
     */
    List<SysUser> getSysUserNamesByIds(String[] ids);

    /**
     * 根据公司ID查询总人数
     * @param companyId
     * @return
     */
    String getUserCountByCompany(String companyId);
}
