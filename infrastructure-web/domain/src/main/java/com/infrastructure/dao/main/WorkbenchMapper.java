package com.infrastructure.dao.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.main.Workbench;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/19.
 */
@Repository
public interface WorkbenchMapper extends BaseMapper<Workbench,String> {

    /**
     * 查询待审批/个人计划/请假/指令
     * @param workbench
     * @param page
     * @return
     */
    List<Workbench> workbench(Workbench workbench, Pagination page);
}
