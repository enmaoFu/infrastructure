package com.infrastructure.service.targetManage;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.targetManage.DeptTarget;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 部门目标-接口
 * User: suyl
 * Date: 2016/4/1
 */
public interface IDeptTargetService extends IBaseService<DeptTarget,String> {

    /**
     * 新增部门目标
     * @param deptTarget
     * @return
     */
    int addDeptTarget(DeptTarget deptTarget);

    /**
     * 修改部门目标
     * @param deptTarget
     * @return
     */
    int modifyDeptTarget(DeptTarget deptTarget);

    /**
     * 删除部门目标
     * @param id
     * @return
     */
    int deleteDeptTarget(String id);

    /**
     * 批量删除部门目标
     * @param ids
     * @return
     */
    int deleteBatchDeptTarget(String[] ids);

    /**
     * 得到部门目标详情
     * @param id
     * @return
     */
    DeptTarget getTargetDeptById(String id);
    /**
     * 查询部门目标
     * @param searchable
     * @param page
     * @return
     */
    List<DeptTarget> queryDeptTargetList(DeptTarget searchable, Pagination page);

    /**
     * 根据条件查询
     * @param map
     * @return
     */
    List<DeptTarget> queryDeptTargetByMap(Map<String, Object> map);

    /**
     * 根据公司目标编号批量删除
     * @param ids
     * @return
     */
    int deleteBathByCompanyTargetId(String[] ids);

}
