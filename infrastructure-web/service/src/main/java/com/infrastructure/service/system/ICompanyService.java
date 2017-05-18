package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.Company;
import com.infrastructure.entity.system.SysUser;

import java.util.List;

/**
 * 企业管理 接口
 * Created by 谭永强 on 2016/1/18.
 */
public interface ICompanyService extends IBaseService<Company, String> {

    /**
     * 根据企业ID查询企业及子企业
     * @param searchable
     * @return
     */
    List<Company> findSysCompanys(Searchable searchable);

    /**
     * 根据企业ID查询企业信息
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 根据用户所属企业ID查询角色
     * @param companyId
     * @return
     */
    List<Company> findCidByRoles(String companyId);

    /**
     * 查询所有企业
     * @return
     */
    List<Company> findAll();
    /**
     * 根据当前登录用户的公司获取公司及其子公司下的用户
     * @param user 当前用户
     * @param myFlg 判断是否需要查询当前用户  Y  需要  N不需要
     * @return
     */
    List<Company> getUserByCompany(SysUser user, String myFlg);
    /**
     * 根据当前登录用户查询部门
     * @param user 当前登录用户
     * @return
     */
    List<Company> getDeptByComany(SysUser user);

    /**
     * 根据当前登录用户的ID查询上级公司、同级公司和下级公司
     * @param id
     * @return
     */
    List<Company> getCompanyByCurentCom(String id);
    /**
     * 根据用户的Id集合查询企业信息
     * @param list
     * @return
     */
    List<Company> queryCompanyByIds(List<String> list);

    /**
     * 添加上级
     * @param company
     * @return
     */
    int insertParent(Company company);
    /**
     * 根据ID批量查询企业的名字集合
     * @param ids
     * @return
     */
    List<Company> getCompanyNamesByIds(String[] ids);
}
