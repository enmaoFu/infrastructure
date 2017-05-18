package com.infrastructure.service.targetManage;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.entity.system.CustomerFeedback;
import com.infrastructure.dao.system.CustomerFeedbackMapper;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户反馈-接口实现
 * User: suyl
 * Date: 2016/6/1
 */
@Service
public class CustomerFeedbackService extends BaseService<CustomerFeedback,String> implements ICustomerFeedbackService{

    @Autowired
    private CustomerFeedbackMapper customerFeedbackMapper;

    @Override
    public BaseMapper<CustomerFeedback, String> getMapper() {
        return customerFeedbackMapper;
    }

    @Override
    public int addCustomerFeedback(CustomerFeedback customerFeedback) {
        return customerFeedbackMapper.insert(customerFeedback);
    }

    @Override
    public CustomerFeedback getCustomerFeedbackById(String id) {
        return customerFeedbackMapper.getById(id);
    }

    @Override
    public List<CustomerFeedback> queryCustomerFeedbackList(CustomerFeedback searchable, Pagination page) {
        return customerFeedbackMapper.queryList(searchable,page);
    }
}
