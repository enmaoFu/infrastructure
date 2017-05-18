package com.infrastructure.service.order;

import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.order.Instructions;
import com.infrastructure.entity.system.SysFile;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 指令下达-接口
 * User: suyl
 * Date: 2016/6/22
 */
public interface IInstructionsService extends IBaseService<Instructions,String> {

    /**
     * 新增指令
     * @param Instructions
     * @return
     */
    int addInstructions(Instructions Instructions, SysFile sysFile);

    /**
     * 修改指令
     * @param Instructions
     * @return
     */
    int modifyInstructions(Instructions Instructions, SysFile sysFile, String path);

    /**
     * 确认指令完成
     * @param params
     * @return
     */
    int confirm(Map<String, Object> params);
    /**
     * 批量删除指令
     * @param ids
     * @return
     */
    int deleteBatchInstructions(String[] ids, String path);

    /**
     * 得到指令详情
     * @param id
     * @return
     */
    Instructions getInstructionsById(String id);
    /**
     * 查询指令
     * @param searchable
     * @param page
     * @return
     */
    List<Instructions> queryInstructionsList(Instructions searchable, Pagination page);

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
