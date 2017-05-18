package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.common.utils.IdKeyGenerator;
import com.infrastructure.dao.system.CompanyMapper;
import com.infrastructure.entity.system.Company;
import com.infrastructure.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 谭永强 on 2016/1/18.
 */
@Service
public class CompanyService extends BaseService<Company,String> implements ICompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public BaseMapper<Company, String> getMapper() {
        return companyMapper;
    }

    /**
     * 根据企业ID查询企业及子企业
     * @param searchable
     * @return
     */
    @Override
    public List<Company> findSysCompanys(Searchable searchable) {
        return companyMapper.findSysCompanys(searchable);
    }

    /**
     * 根据企业ID查询企业
     * @param id
     * @return
     */
    @Override
    public Company findById(String id) {
        return companyMapper.findById(id);
    }

    /**
     * 根据用户所属企业ID查询角色
     * @param companyId
     * @return
     */
    @Override
    public List<Company> findCidByRoles(String companyId) {
        return companyMapper.findCidByRoles(companyId);
    }

    /**
     * 查询所有企业
     * @return
     */
    @Override
    public List<Company> findAll() {
        return companyMapper.findAll();
    }

    /**
     * 根据当前登录用户的公司获取公司及其子公司下的用户
     * @param user 当前用户
     * @param myFlg 判断是否需要查询当前用户  Y  需要  N不需要
     * @return
     */
    @Override
    public List<Company> getUserByCompany(SysUser user, String myFlg){
        Map<String,String> map = new HashMap<>();
        map.put("companyId",user.getCompanyId());
        map.put("id",user.getId());
        List<Company> list = null;
        if(myFlg.equals("Y"))
            list = companyMapper.getUserByCompanyMy(map);
        else
            list = companyMapper.getUserByCompany(map);
        return list;
    }

    /**
     * 根据当前登录用户查询部门
     * @param user 当前登录用户
     * @return
     */
    public List<Company> getDeptByComany(SysUser user){
        return companyMapper.getDeptByComany(user);
    }
    /**
     * 根据当前登录用户的ID查询上级公司、同级公司和下级公司
     * @param id
     * @return
     */
    @Override
    public List<Company> getCompanyByCurentCom(String id){
        return companyMapper.getCompanyByCurentCom(id);
    }
    /**
     * 根据用户的Id集合查询企业信息
     * @param list
     * @return
     */
    @Override
    public List<Company> queryCompanyByIds(List<String> list){
        return companyMapper.queryCompanyByIds(list);
    }

    /**
     * 添加上级
     * @param company
     * @return
     */
    @Override
    public int insertParent(Company company) {
        String pId = IdKeyGenerator.uuid();
        List<Company> list = companyMapper.findSysCompanys(company);
        for (Company company1 : list) {
            String parents = company1.getParents();
            if(!StringUtils.isEmpty(parents)){
                company1.setParents(pId+","+parents);
            }else {
                company1.setParent(pId);
                company1.setParents(pId);
            }
            companyMapper.update(company1);
        }
        company.setId(pId);
        return super.insert(company);
    }
    /**
     * 根据ID批量查询企业的名字集合
     * @param ids
     * @return
     */
    @Override
    public List<Company> getCompanyNamesByIds(String[] ids){
        return companyMapper.getCompanyNamesByIds(ids);
    }
}
