package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.Select;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * Created by tyq on 2015/8/26.
 */
public interface ISelectService extends IBaseService<Select,String> {

    /**
     * 查询
     * @param search
     * @param page
     * @return
     */
    List<Select> queryPage(Searchable search, Pagination page);

    /**
     * 根据标识和公司ID查询
     * @param select
     * @return
     */
    List<Select> querySelect(Select select);
}
