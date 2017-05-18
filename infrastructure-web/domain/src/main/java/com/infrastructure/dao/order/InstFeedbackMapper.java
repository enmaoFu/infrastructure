package com.infrastructure.dao.order;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.order.InstFeedback;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstFeedbackMapper extends BaseMapper<InstFeedback, String> {

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(InstFeedback record);

    /**
     * 修改
     * @param record
     * @return
     */
    int update(InstFeedback record);

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
    InstFeedback getById(String recordId);

    /**
     * 分页
     * @param searchable
     * @param page
     * @return
     */
    List<InstFeedback> queryList(Searchable searchable, Pagination page);

}