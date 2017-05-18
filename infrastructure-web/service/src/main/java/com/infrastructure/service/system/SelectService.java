package com.infrastructure.service.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.SelectMapper;
import com.infrastructure.entity.system.Select;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyq on 2015/8/26.
 */
@Service
public class SelectService extends BaseService<Select,String> implements ISelectService{

    @Autowired
    private SelectMapper selectMapper;

    @Override
    public BaseMapper<Select, String> getMapper() {
        return selectMapper;
    }

    /**
     * 分页查询
     * @param search
     * @param page
     * @return
     */
    @Override
    public List<Select> queryPage(Searchable search, Pagination page) {
        return selectMapper.queryPage(search,page);
    }

    /**
     * 根据标识和公司ID查询下拉数据
     * @param select
     * @return
     */
    @Override
    public List<Select> querySelect(Select select) {
        return selectMapper.querySelect(select);
    }

}
