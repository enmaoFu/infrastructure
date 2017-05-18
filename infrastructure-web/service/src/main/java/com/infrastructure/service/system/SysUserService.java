package com.infrastructure.service.system;

import com.google.common.collect.Lists;
import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.dao.system.RoleMapper;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.entity.system.SysUserRole;
import com.infrastructure.dao.system.SysUserMapper;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * SysUserService
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class SysUserService extends BaseService<SysUser, String> implements ISysUserService {

    @Autowired
    private SysUserMapper sump;
    @Autowired
    private RoleMapper rmp;


    @Override
    public BaseMapper<SysUser, String> getMapper() {
        return sump;
    }


    @Override
    public List<SysUser> findAll() {
        return sump.findAll();
    }

    /**
     * 角色分页查询
     * @param searchable 搜索条件
     * @param page       分页条件
     * @return
     */
    @Override
    public List<SysUser> findSysUsers(Searchable searchable, Pagination page) {
        return sump.findSysUsers(searchable, page);
    }

    /**
     * 关联角色
     * @param id      用户标识
     * @param roleIds 角色标识数组
     * @return
     */
    @Override
    public int contactWithRoles(String id, String[] roleIds) {
        // 清空关联
        int ret = -1;
        ret = sump.unContactWithRoles(id);
        if (roleIds != null && roleIds.length > 0) {
            List<SysUserRole> list = Lists.newArrayList();
            SysUserRole sur;
            for (String rid : roleIds) {
                sur = new SysUserRole();
                sur.setId(IdKeyGenerator.uuid());
                sur.setUserId(id);
                sur.setRoleId(rid);
                // add
                list.add(sur);
            }
            ret = sump.contactWithRoles(list);
        }
        return ret;
    }

    /**
     * 获取关联的角色标识
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> getOwnedRoleIds(String userId) {
        return sump.getOwnedRoleIds(userId);
    }

    @Override
    public Set<String> getRoles(String login) {
        SysUser user = sump.findSysUserByLogin(login);
        if (user != null) {
            Set<String> roleIds = getOwnedRoleIds(user.getId());
            if (!CollectionUtils.isEmpty(roleIds))
                return rmp.getRoles(roleIds.toArray(new String[roleIds.size()]));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPermissions(String login) {
        SysUser user = sump.findSysUserByLogin(login);
        if (user != null) {
            Set<String> roleIds = getOwnedRoleIds(user.getId());
            if (!CollectionUtils.isEmpty(roleIds))
                return rmp.getPermissions(roleIds.toArray(new String[roleIds.size()]));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getButtonPermissions(String id) {
        SysUser user = sump.select(id);
        if (user != null) {
            Set<String> roleIds = getOwnedRoleIds(user.getId());
            if (!CollectionUtils.isEmpty(roleIds))
                return rmp.getButtonPermissions(roleIds.toArray(new String[roleIds.size()]));
        }
        return Collections.emptySet();
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param login
     * @return
     */
    @Override
    public SysUser getUser(String login) {
        return sump.findSysUserByLogin(login);
    }

    @Override
    public SysUser queryUserByPhone(SysUser sysUser) {
        return sump.queryUserByPhone(sysUser);
    }

    @Override
    public List<SysUser> queryInstallationIdByUserIds(List<String> list) {
        return sump.queryInstallationIdByUserIds(list);
    }

    @Override
    public int contactWithRoles(List<SysUserRole> list) {
        return sump.contactWithRoles(list);
    }

    @Override
    public int unContactWithRoles(String userId) {
        return sump.unContactWithRoles(userId);
    }

    @Override
    public List<SysUser> findAllSysUsersBySchId(String schId) {
        return sump.findAllSysUsersBySchId(schId);
    }

    @Override
    public List<SysUser> searchUser(SysUser sysUser) {
        return sump.searchUser(sysUser);
    }

    @Override
    public SysUser queryUserById(SysUser sysUser) {
        return sump.queryUserById(sysUser);
    }

    @Override
    public boolean isExists(String login) {
        return sump.isExists(login);
    }


    /**
     * 用户删除
     * @param ids ID数组
     * @return
     */
    @Override
    public int deleteArray(String[] ids) {
        //物理删除
        //sump.deleteArray(ids);
        //逻辑删除
        return sump.logicDeleteArray(ids);
    }

    /**
     * 根据角色ID查询用户
     * @param rid
     * @return
     */
    @Override
    public List<SysUser> findByRid(String rid) {
        return sump.findByRid(rid);
    }

    /**
     * 根据单位ID查询用户数据
     * @param companyId
     * @return
     */
    @Override
    public List<SysUser> findByCid(String companyId) {
        return sump.findByCid(companyId);
    }

    /**
     * 根据部门ID查询用户
     * @param deptId
     * @return
     */
    @Override
    public List<SysUser> findByDeptId(String deptId) {
        return sump.findByDeptId(deptId);
    }

    @Override
    public List<SysUser> searchUserByUserId(String userId) {
        return sump.searchUserByUserId(userId);
    }

    /**
     * 根据查询条件查询用户
     * @param user
     * @return
     */
    @Override
    public List<SysUser> getUserByCompanyDept(SysUser user){
        return sump.getUserByCompanyDept(user);
    }

    /**
     * 根据用户名查询用户
     * @param login
     * @return
     */
    @Override
    public SysUser findByLogin(String login) {
        return sump.findByLogin(login);
    }
    /**
     * 根据ID批量查询数据
     * @param ids
     * @return
     */
    @Override
    public List<SysUser> getSysUserByIds(String[] ids){
        return sump.getSysUserByIds(ids);
    }

    /**
     * 根据ID批量查询用户的名字集合
     * @param ids
     * @return
     */
    @Override
    public List<SysUser> getSysUserNamesByIds(String[] ids){
        return sump.getSysUserNamesByIds(ids);
    }

    /**
     * 根据公司ID查询总人数
     * @param companyId
     * @return
     */
    @Override
    public String getUserCountByCompany(String companyId){
        return sump.getUserCountByCompany(companyId);
    }
}
