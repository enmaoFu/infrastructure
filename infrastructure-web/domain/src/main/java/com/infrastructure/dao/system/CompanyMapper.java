package com.infrastructure.dao.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.Company;
import com.infrastructure.entity.system.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 企业管理Mapper
 * Created by 谭永强 on 2016/1/18.
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company, String> {


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
     * 根据当前登录用户的公司获取公司及其子公司下的用户不包含自己
     * @return
     */
    List<Company> getUserByCompany(@Param("map") Map<String, String> map);
    /**
     * 根据当前登录用户的公司获取公司及其子公司下的用户包含自己
     * @return
     */
    List<Company> getUserByCompanyMy(@Param("map") Map<String, String> map);

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
     * 根据ID批量查询企业的名字集合
     * @param ids
     * @return
     */
    List<Company> getCompanyNamesByIds(@Param("ids") String[] ids);
}
