package com.infrastructure.service.targetManage;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.targetManage.DeptTargetMapper;
import com.infrastructure.entity.targetManage.DeptTarget;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 部门目标-接口实现
 * User: suyl
 * Date: 2016/4/1
 */
@Service
public class DeptTargetService extends BaseService<DeptTarget,String> implements IDeptTargetService{

    @Autowired
    private DeptTargetMapper deptTargetMapper;


    @Override
    public BaseMapper<DeptTarget, String> getMapper() {
        return deptTargetMapper;
    }

    @Override
    public int addDeptTarget(DeptTarget deptTarget) {
        return deptTargetMapper.insert(deptTarget);
    }

    @Override
    public int modifyDeptTarget(DeptTarget deptTarget) {
        return deptTargetMapper.update(deptTarget);
    }

    @Override
    public int deleteDeptTarget(String id) {
        return deptTargetMapper.delete(id);
    }

    @Override
    public int deleteBatchDeptTarget(String[] ids) {
        return deptTargetMapper.deleteBatch(ids);
    }

    @Override
    public DeptTarget getTargetDeptById(String id) {
        return deptTargetMapper.getById(id);
    }

    @Override
    public List<DeptTarget> queryDeptTargetList(DeptTarget searchable, Pagination page) {
        return deptTargetMapper.queryList(searchable, page);
    }

    @Override
    public List<DeptTarget> queryDeptTargetByMap(Map<String, Object> map) {
        return deptTargetMapper.queryDeptTargetByMap(map);
    }

    @Override
    public int deleteBathByCompanyTargetId(String[] ids) {
        return deptTargetMapper.deleteBathByCompanyTargetId(ids);
    }
}
