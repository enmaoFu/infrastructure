package com.infrastructure.service.daily;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.system.SysFile;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 制度-service
 * Created by 谭永强 on 2016/4/25.
 */
public interface IRegimeService extends IBaseService<Regime,String> {

    /**
     *
     * @param regime
     * @param page
     * @return
     */
    List<Regime> load(Regime regime, Pagination page);

    /**
     * 新增
     * @param regime
     * @param sysFile
     * @return
     */
    int insert(Regime regime, SysFile sysFile);

    /**
     * 删除
     * @param ids
     * @param path
     * @return
     */
    int deleteArray(String[] ids, String path);

    /**
     * 修改
     * @param regime
     * @param sysFile
     * @param path
     * @return
     */
    int update(Regime regime, SysFile sysFile, String path);

    /**
     * 根据所属模块查询制度
     * model 模块ID
     * companyId 公司ID
     * @return
     */
    Regime findByModel(Map<String, Object> map);
}
