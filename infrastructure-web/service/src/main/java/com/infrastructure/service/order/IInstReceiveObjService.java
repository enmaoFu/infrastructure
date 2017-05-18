package com.infrastructure.service.order;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.order.InstReceiveObj;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * 接收对象-接口
 * User: suyl
 * Date: 2016/6/22
 */
public interface IInstReceiveObjService extends IBaseService<InstReceiveObj,String> {

    /**
     * 新增接受指令
     * @param InstReceiveObj
     * @return
     */
    int addInstReceiveObj(List<InstReceiveObj> InstReceiveObj);

    /**
     * 修改接受指令
     * @param InstReceiveObj
     * @return
     */
    int modifyInstReceiveObj(InstReceiveObj InstReceiveObj);

    /**
     * 批量删除接受指令
     * @param ids
     * @return
     */
    int deleteBatchInstReceiveObj(String[] ids);

    /**
     * 得到接受指令详情
     * @param id
     * @return
     */
    InstReceiveObj getInstReceiveObjById(String id);
    /**
     * 查询接受指令
     * @param searchable
     * @param page
     * @return
     */
    List<InstReceiveObj> queryInstReceiveObjList(InstReceiveObj searchable, Pagination page);

}
