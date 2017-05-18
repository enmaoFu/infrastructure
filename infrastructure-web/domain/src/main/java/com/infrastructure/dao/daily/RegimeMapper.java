package com.infrastructure.dao.daily;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.daily.Regime;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 谭永强 on 2016/4/25.
 */
@Repository
public interface RegimeMapper extends BaseMapper<Regime,String> {

    /**
     * 制度查询
     * @param regime
     * @param page
     * @return
     */
    List<Regime> load(Regime regime, Pagination page);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteArray(String[] ids);

    /**
     * 根据所属模块查询制度
     * model 模块ID
     * companyId 公司ID
     * @return
     */
    Regime findByModel(Map<String, Object> map);
}
