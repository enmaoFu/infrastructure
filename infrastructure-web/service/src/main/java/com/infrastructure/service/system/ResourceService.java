package com.infrastructure.service.system;

import com.google.common.collect.Lists;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.ResourceMapper;
import com.infrastructure.entity.system.Resource;
import net.oschina.archx.mybatis.Pagination;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * ResourceSerivce
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class ResourceService extends BaseService<Resource,String> implements IResourceService {

    @Autowired
    private ResourceMapper bmp;
    @Override
    public BaseMapper<Resource, String> getMapper() {
        return bmp;
    }
    @Override
    public List<Resource> findAll() {
        return bmp.findAll();
    }
    @Override
    public List<Resource> findAll(Pagination page) {
        return bmp.findAll();
    }

    @Override
    public List<Resource> getMenus() {
        List<Resource> resources = findAll();

        List<Resource> menus = Lists.newArrayList();

        for (Resource res: resources){
            // 不是菜单跳过
            if (res.getType() != Resource.ResourceType.MENU)
                continue;
            // 不可用跳过
            if (!res.getAvailable())
                continue;

            menus.add(res);
        }

        return menus;
    }

    @Override
    public List<Resource> getMenus(Set<String> permissions) {
        return getMenus(permissions, false);
    }

    @Override
    public List<Resource> getMenus(Set<String> permissions, boolean btn) {
        // 所有菜单
        List<Resource> resources = findAll();
        // 拥有菜单
        List<Resource> menus = Lists.newArrayList();

        if(!CollectionUtils.isEmpty(resources)) {
            for (Resource res : resources) {
                // 不是菜单跳过
                if (!btn && res.getType() != Resource.ResourceType.MENU)
                    continue;
                // 不可用跳过
                if (!res.getAvailable())
                    continue;
                // 没有权限跳过
                if (!hasPermission(permissions, res))
                    continue;
                menus.add(res);
            }
        }

        return menus;
    }


    /**
     * 判断是否拥有资源权限
     *
     * @param permissions
     * @param resource
     * @return
     */
    private boolean hasPermission(Set<String> permissions, Resource resource) {
        // 无权限字符串的菜单表示开放
        if (StringUtils.isEmpty(resource.getPermission()))
            return true;
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if (p1.implies(p2) && p2.implies(p1)) {
                return true;
            }
            /*if (p1.implies(p2) || p2.implies(p1)) {
                return true;
            }*/
        }
        return false;
    }

    /**
     * 根据角色ID查询角色所拥有的权限
     * @param rid
     * @return
     */
    @Override
    public List<String> findRidByResource(String rid) {
        return bmp.findRidByResource(rid);
    }

    /**
     * 菜单删除
     * @param id
     * @return
     */
    @Override
    public int delete(String id) {
        //删除角色菜单中间表数据
        int result = bmp.deleteRoleResource(id);
        //删除菜单及其子菜单
        result = super.delete(id);
        return result;
    }

    /**
     * 根据用户ID查询url非空的菜单
     * @param id
     * @return
     */
    @Override
    public List<Resource> queryUrlNotNull(String id) {
        return bmp.queryUrlNotNull(id);
    }
}
