package com.infrastructure.dao.order;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.order.Instructions;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InstructionsMapper extends BaseMapper<Instructions, String> {

    /** 新增
     * @param record
     * @return
     */
    int insert(Instructions record);

    /**
     * 修改
     * @param record
     * @return
     */
    int update(Instructions record);

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
    Instructions getById(String recordId);

    /**
     * 分页
     * @param searchable
     * @param page
     * @return
     */
    List<Instructions> queryList(Searchable searchable, Pagination page);

    /**
     * 确认指令完成
     * @param params
     * @return
     */
    int confirm(Map<String, Object> params);

    /**
     * 收到指令列表
     * @param searchable
     * @param page
     * @return
     */
    List<Instructions> queryInstReceiveObjList(Searchable searchable, Pagination page);

    /**
     * 转发指令列表
     * @param searchable
     * @param page
     * @return
     */
    List<Instructions> queryForwardList(Searchable searchable, Pagination page);
}