package com.infrastructure.service.order;

import com.infrastructure.dao.order.InstFeedbackMapper;
import com.infrastructure.entity.order.InstFeedback;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈-接口实现
 * User: suyl
 * Date: 2016/6/22
 */
@Service
public class InstFeedbackService extends BaseService<InstFeedback,String> implements IInstFeedbackService{

    @Autowired
    private InstFeedbackMapper instFeedbackMapper;

    @Override
    public BaseMapper<InstFeedback, String> getMapper() {
        return instFeedbackMapper;
    }

    @Override
    public int addInstFeedback(InstFeedback InstFeedback) {
        return instFeedbackMapper.insert(InstFeedback);
    }

    @Override
    public int modifyInstFeedback(InstFeedback InstFeedback) {
        return instFeedbackMapper.update(InstFeedback);
    }

    @Override
    public int deleteBatchInstFeedback(String[] ids) {
        return instFeedbackMapper.deleteBatch(ids);
    }

    @Override
    public InstFeedback getInstFeedbackById(String id) {
        return instFeedbackMapper.getById(id);
    }

    @Override
    public List<InstFeedback> queryInstFeedbackList(InstFeedback searchable, Pagination page) {
        return instFeedbackMapper.queryList(searchable,page);
    }
}
