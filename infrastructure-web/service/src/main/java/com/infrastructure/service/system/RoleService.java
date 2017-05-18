package com.infrastructure.service.system;

import com.google.common.collect.Lists;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.dao.system.ResourceMapper;
import com.infrastructure.dao.system.RoleMapper;
import com.infrastructure.entity.system.Role;
import com.infrastructure.entity.system.RoleResource;
import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.entity.system.Resource;
import net.oschina.archx.mybatis.Pagination;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * RoleService
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class RoleService extends BaseService<Role, String> implements IRoleService {

    @Autowired
    private RoleMapper rmp;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public BaseMapper<Role, String> getMapper() {
        return rmp;
    }

    /**
     * 根据企业ID查询角色
     * @param searchable
     * @param page
     * @return
     */
    @Override
    public List<Role> findAll(Searchable searchable, Pagination page) {
        return rmp.findAll(searchable,page);
    }

    @Override
    public int contactWithResources(List<RoleResource> items) {
        unContactWithResources(items.get(0).getRoleId());
        return rmp.contactWithResources(items);
    }

    @Override
    public int unContactWithResources(String id) {
        return rmp.unContactWithResources(id);
    }

    @Override
    public Set<String> getOwnedResourceIds(String id) {
        return rmp.getOwnedResourceIds(id);
    }

    @Override
    public Set<String> getPermissions(Set<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds))
            return Collections.emptySet();
        return rmp.getPermissions(roleIds.toArray(new String[roleIds.size()]));
    }

    @Override
    public int copyRole(String id, Role role) {
        // 资源列表
        Set<String> res = rmp.getOwnedResourceIds(id);
        int ret = rmp.insert(role);
        if (res != null && !res.isEmpty()) {
            List<RoleResource> items = Lists.newArrayList();
            for (String rid : res) {
                RoleResource rr = new RoleResource();
                rr.setId(IdKeyGenerator.uuid());
                rr.setRoleId(role.getId());
                rr.setResId(rid);

                items.add(rr);
            }
            ret += contactWithResources(items);
        }

        return ret;
    }

    @Override
    public int saveOrUpdate(Role role) {
        int ret = 0;
        boolean isUpdate = StringUtils.hasText(role.getId());
        if (isUpdate) {
            ret = rmp.update(role);
        } else {
            role.setId(IdKeyGenerator.uuid());
            ret = rmp.insert(role);
        }
        return ret;
    }

    @Override
    public boolean isAllowed(String name) {
        return !rmp.isExists(name);
    }

    @Override
    public int updateSystemAdminResources(String roleName) {
        Set<String> ids  = rmp.findRoleIdsByName(roleName);

        if (ids != null && ids.size() == 1) {
            String roleId = ids.iterator().next();
            List<Resource> resources = resourceMapper.findAll();
            List<RoleResource> items = new ArrayList<>();
            RoleResource rr = null;
            for (Resource res : resources) {
                rr = new RoleResource();
                rr.setId(IdKeyGenerator.uuid());
                rr.setRoleId(roleId);
                rr.setResId(res.getId());
                items.add(rr);
            }

            if (items.size() > 0) {
                rmp.unContactWithResources(roleId);
                return rmp.contactWithResources(items);
            }
        }
        return 0;
    }

    @Override
    public List<Role> findAssignRoles(Role role) {
        return rmp.findAssignRoles(role);
    }

    /**
     * 根据企业ID查询角色
     * @param companyId
     * @return
     */
    @Override
    public List<Role> findByCid(String companyId) {
        return rmp.findByCid(companyId);
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @Override
    public int deleteArray(String[] ids) {
        //删除角色权限中间表数据
        int result = rmp.deleteRoleResourceArray(ids);
        result =  rmp.deleteArray(ids);
        return result;
    }

    /**
     * 根据用户ID查询拥有的角色
     * @param uId
     * @return
     */
    @Override
    public List<String> getRoleByUid(String uId) {
        return rmp.getRoleByUid(uId);
    }

    /**
     * 根据userId查询部门编号
     * @param userId
     * @return
     */
    @Override
    public List<Role> queryRolesListByUserId(String userId) {
        return rmp.queryRolesListByUserId(userId);
    }

    /**
     * 角色赋权
     * @param ids
     * @return
     */
    @Override
    public int authorization(String[] ids,String rId) {
        //根据角色ID删除角色权限
        int result = rmp.deleteRoleResource(rId);
        List<RoleResource> list = new ArrayList<RoleResource>();
        if(ids != null &&ids.length > 0){
            for (String id : ids) {
                RoleResource resource = new RoleResource();
                resource.setId(IdKeyGenerator.uuid());
                resource.setRoleId(rId);
                resource.setResId(id);
                list.add(resource);
            }
            result = rmp.authorization(list);
        }
        return result;
    }
}
