package com.infrastructure.service.order;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.order.InstFeedback;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * 反馈-接口
 * User: suyl
 * Date: 2016/6/22
 */
public interface IInstFeedbackService extends IBaseService<InstFeedback,String> {

    /**
     * 新增指令
     * @param InstFeedback
     * @return
     */
    int addInstFeedback(InstFeedback InstFeedback);

    /**
     * 修改指令
     * @param InstFeedback
     * @return
     */
    int modifyInstFeedback(InstFeedback InstFeedback);

    /**
     * 批量删除指令
     * @param ids
     * @return
     */
    int deleteBatchInstFeedback(String[] ids);

    /**
     * 得到指令详情
     * @param id
     * @return
     */
    InstFeedback getInstFeedbackById(String id);
    /**
     * 查询指令
     * @param searchable
     * @param page
     * @return
     */
    List<InstFeedback> queryInstFeedbackList(InstFeedback searchable, Pagination page);

}
