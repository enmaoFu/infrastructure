package com.infrastructure.dao.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.Select;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tyq on 2015/8/26.
 */
@Repository
public interface SelectMapper extends BaseMapper<Select,String> {

    List<Select> queryPage(Searchable search, Pagination page);

    /**
     * 根据标识和公司ID查询下拉数据
     * @param select
     * @return
     */
    List<Select> querySelect(Select select);

}
