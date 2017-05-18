package com.infrastructure.service.targetManage;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.CustomerFeedback;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * 客户反馈-接口
 * User: suyl
 * Date: 2016/6/1
 */
public interface ICustomerFeedbackService extends IBaseService<CustomerFeedback,String> {

    /**
     * 新增客户反馈
     * @param customerFeedback
     * @return
     */
    int addCustomerFeedback(CustomerFeedback customerFeedback);

    /**
     * 得到客户反馈详情
     * @param id
     * @return
     */
    CustomerFeedback getCustomerFeedbackById(String id);
    /**
     * 查询客户反馈
     * @param searchable
     * @param page
     * @return
     */
    List<CustomerFeedback> queryCustomerFeedbackList(CustomerFeedback searchable, Pagination page);
}
