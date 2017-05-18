package com.infrastructure.service.order;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.order.InstReceiveObjMapper;
import com.infrastructure.entity.order.InstReceiveObj;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 接受指令-接口实现
 * User: suyl
 * Date: 2016/6/22
 */
@Service
public class InstReceiveObjService extends BaseService<InstReceiveObj,String> implements IInstReceiveObjService{

    @Autowired
    private InstReceiveObjMapper instReceiveObjMapper;

    @Override
    public BaseMapper<InstReceiveObj, String> getMapper() {
        return instReceiveObjMapper;
    }

    @Override
    public int addInstReceiveObj(List<InstReceiveObj> InstReceiveObj) {
        return instReceiveObjMapper.insert(InstReceiveObj);
    }

    @Override
    public int modifyInstReceiveObj(InstReceiveObj InstReceiveObj) {
        return instReceiveObjMapper.update(InstReceiveObj);
    }

    @Override
    public int deleteBatchInstReceiveObj(String[] ids) {
        return instReceiveObjMapper.deleteBatch(ids);
    }

    @Override
    public InstReceiveObj getInstReceiveObjById(String id) {
        return instReceiveObjMapper.getById(id);
    }

    @Override
    public List<InstReceiveObj> queryInstReceiveObjList(InstReceiveObj searchable, Pagination page) {
        return instReceiveObjMapper.queryList(searchable,page);
    }
}
