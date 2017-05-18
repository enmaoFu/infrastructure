package com.infrastructure.service.targetManage;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.targetManage.CompanyTargetMapper;
import com.infrastructure.entity.targetManage.CompanyTarget;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 公司目标-接口实现
 * User: suyl
 * Date: 2016/4/1
 */
@Service
public class CompanyTargetService extends BaseService<CompanyTarget,String> implements ICompanyTargetService{

    @Autowired
    private CompanyTargetMapper companyTargetMapper;

    @Override
    public BaseMapper<CompanyTarget, String> getMapper() {
        return companyTargetMapper;
    }

    @Override
    public int addCompanyTarget(CompanyTarget companyTarget) {
        return companyTargetMapper.insert(companyTarget);
    }

    @Override
    public int modifyCompanyTarget(CompanyTarget companyTarget) {
        return companyTargetMapper.update(companyTarget);
    }

    @Override
    public int deleteCompanyTarget(String id) {
        return companyTargetMapper.delete(id);
    }

    @Override
    public int deleteBatchCompanyTarget(String[] ids) {
        return companyTargetMapper.deleteBatch(ids);
    }

    @Override
    public CompanyTarget getTargetCompanyById(String id) {
        return companyTargetMapper.getById(id);
    }

    @Override
    public List<CompanyTarget> queryCompanyTargetList(CompanyTarget searchable, Pagination page) {
        return companyTargetMapper.queryList(searchable, page);
    }

    @Override
    public CompanyTarget queryCompanyTargetByMap(Map<String, Object> map) {
        return companyTargetMapper.queryCompanyTargetByMap(map);
    }
}
