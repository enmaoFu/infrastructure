package com.infrastructure.dao.system;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.CustomerFeedback;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suyl on 2016/6/1.
 */
@Repository
public interface CustomerFeedbackMapper extends BaseMapper<CustomerFeedback, String> {

    /**
     * 新增
     * @param customerFeedback
     * @return
     */
    int insert(CustomerFeedback customerFeedback);

    /**
     * 得到详情
     * @param customerFeedbackId
     * @return
     */
    CustomerFeedback getById(String customerFeedbackId);

    /**
     * 分页
     * @return
     */
    List<CustomerFeedback> queryList(Searchable searchable, Pagination page);

}
