package com.infrastructure.dao.targetManage;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.targetManage.DeptTarget;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by suyl on 2016/4/1.
 * 部门目标
 */
@Repository
public interface DeptTargetMapper extends BaseMapper<DeptTarget, String> {

    /**
     * 新增
     * @param deptTarget
     * @return
     */
    int insert(DeptTarget deptTarget);

    /**
     * 修改
     * @param deptTarget
     * @return
     */
    int update(DeptTarget deptTarget);

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
     * 根据公司目标编号批量删除
     * @param ids
     * @return
     */
    int deleteBathByCompanyTargetId(String[] ids);

    /**
     * 得到详情
     * @param deptTargetId
     * @return
     */
    DeptTarget getById(String deptTargetId);

    /**
     * 分页
     * @return
     */
    List<DeptTarget> queryList(Searchable searchable, Pagination page);

    /**
     * 根据条件查询
     * @param map
     * @return
     */
    List<DeptTarget> queryDeptTargetByMap(Map<String, Object> map);

}
