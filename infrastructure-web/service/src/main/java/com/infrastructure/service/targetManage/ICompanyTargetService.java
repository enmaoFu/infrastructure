package com.infrastructure.service.targetManage;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.targetManage.CompanyTarget;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 公司目标-接口
 * User: suyl
 * Date: 2016/4/1
 */
public interface ICompanyTargetService extends IBaseService<CompanyTarget,String> {

    /**
     * 新增公司目标
     * @param companyTarget
     * @return
     */
    int addCompanyTarget(CompanyTarget companyTarget);

    /**
     * 修改公司目标
     * @param companyTarget
     * @return
     */
    int modifyCompanyTarget(CompanyTarget companyTarget);

    /**
     * 删除公司目标
     * @param id
     * @return
     */
    int deleteCompanyTarget(String id);

    /**
     * 批量删除公司目标
     * @param ids
     * @return
     */
    int deleteBatchCompanyTarget(String[] ids);

    /**
     * 得到公司目标详情
     * @param id
     * @return
     */
    CompanyTarget getTargetCompanyById(String id);
    /**
     * 查询公司目标
     * @param searchable
     * @param page
     * @return
     */
    List<CompanyTarget> queryCompanyTargetList(CompanyTarget searchable, Pagination page);

    /**
     * 根据条件查询
     * @param map
     * @return
     */
    CompanyTarget queryCompanyTargetByMap(Map<String, Object> map);

}
