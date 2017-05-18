package com.infrastructure.dao.order;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.order.InstReceiveObj;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstReceiveObjMapper extends BaseMapper<InstReceiveObj, String> {

    /** 新增
    * @param record
    * @return
    */
    int insert(List<InstReceiveObj> record);

    /**
     * 修改
     * @param record
     * @return
     */
    int update(InstReceiveObj record);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(String[] ids);

    /**
     * 查看详情
     * @param recordId
     * @return
     */
    InstReceiveObj getById(String recordId);

    /**
     * 分页
     * @param searchable
     * @param page
     * @return
     */
    List<InstReceiveObj> queryList(Searchable searchable, Pagination page);
}