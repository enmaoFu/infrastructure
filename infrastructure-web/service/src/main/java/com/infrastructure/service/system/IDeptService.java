package com.infrastructure.service.system;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.Dept;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/1/28
 * Time: 16:15
 */
public interface IDeptService extends IBaseService<Dept,String> {

    /**
     * 查询部门
     * @param searchable
     * @param page
     * @return
     */
    List<Dept> findAll(Dept searchable, Pagination page);

    /**
     * 根据公司ID查询部门
     * @param companyId
     * @return
     */
    List<Dept> findByCompanyId(String companyId);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteArray(String[] ids);

    /**
     * 根据公司ID查询部门及用户
     * @param companyId
     * @return
     */
    List<Dept> findUserByCid(String companyId);
    /**
     * 根据用户的Id集合查询部门信息
     *
     * @param list
     * @return
     */
    List<Dept> queryDeptByIds(List<String> list);
    /**
     * 根据ID批量查询部门的名字集合
     * @param ids
     * @return
     */
    List<Dept> getDeptNamesByIds(String[] ids);
}
