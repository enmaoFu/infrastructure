package com.infrastructure.dao.targetManage;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.targetManage.CompanyTarget;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by suyl on 2016/4/1.
 * 公司目标
 */
@Repository
public interface CompanyTargetMapper extends BaseMapper<CompanyTarget, String> {

    /**
     * 新增
     * @param companyTarget
     * @return
     */
    int insert(CompanyTarget companyTarget);

    /**
     * 修改
     * @param companyTarget
     * @return
     */
    int update(CompanyTarget companyTarget);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(String[] ids);

    /**
     * 得到详情
     * @param companyTargetId
     * @return
     */
    CompanyTarget getById(String companyTargetId);

    /**
     * 分页
     * @return
     */
    List<CompanyTarget> queryList(Searchable searchable, Pagination page);

    /**
     * 根据条件查询
     * @param map
     * @return
     */
    CompanyTarget queryCompanyTargetByMap(Map<String, Object> map);

}
