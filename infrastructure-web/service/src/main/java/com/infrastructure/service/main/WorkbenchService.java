package com.infrastructure.service.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.main.WorkbenchMapper;
import com.infrastructure.entity.main.Workbench;

import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/19.
 */
@Service
public class WorkbenchService extends BaseService<Workbench,String> implements IWorkbenchService{

    @Autowired
    private WorkbenchMapper workbenchMapper;

    @Override
    public BaseMapper<Workbench, String> getMapper() {
        return workbenchMapper;
    }

    /**
     * 查询待审批/个人计划/请假/指令
     * @param workbench
     * @param page
     * @return
     */
    @Override
    public List<Workbench> workbench(Workbench workbench, Pagination page) {
        return workbenchMapper.workbench(workbench,page);
    }
}
