package com.infrastructure.service.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.DeptMapper;
import com.infrastructure.entity.system.Dept;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/1/28
 * Time: 16:16
 */
@Service
public class DeptService extends BaseService<Dept,String> implements IDeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public BaseMapper<Dept, String> getMapper() {
        return deptMapper;
    }


    /**
     * 部门分页
     * @param searchable
     * @param page
     * @return
     */
    @Override
    public List<Dept> findAll(Dept searchable, Pagination page) {
        return deptMapper.findAll(searchable,page);
    }

    /**
     * 根据公司ID查询部门
     * @param companyId
     * @return
     */
    @Override
    public List<Dept> findByCompanyId(String companyId) {
        return deptMapper.findByCompanyId(companyId);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public int deleteArray(String[] ids) {
        return deptMapper.deleteArray(ids);
    }

    /**
     * 根据公司ID查询部门及用户
     * @param companyId
     * @return
     */
    @Override
    public List<Dept> findUserByCid(String companyId) {
        return deptMapper.findUserByCid(companyId);
    }

    /**
     *
     * @return
     */
    public DeptMapper getDeptMapper() {
        return deptMapper;
    }

    public void setDeptMapper(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    /**
     * 根据用户的Id集合查询部门信息
     *
     * @param list
     * @return
     */
    @Override
    public List<Dept> queryDeptByIds(List<String> list){
        return deptMapper.queryDeptByIds(list);
    }
    /**
     * 根据ID批量查询部门的名字集合
     * @param ids
     * @return
     */
    @Override
    public List<Dept> getDeptNamesByIds(String[] ids){
        return deptMapper.getDeptNamesByIds(ids);
    }
}
