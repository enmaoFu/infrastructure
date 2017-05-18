package com.infrastructure.service.main;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.main.Workbench;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/19.
 */
public interface IWorkbenchService extends IBaseService<Workbench,String> {

    /**
     * 查询待审批/个人计划/请假/指令
     * @param workbench
     * @param page
     * @return
     */
    List<Workbench> workbench(Workbench workbench, Pagination page);
}
