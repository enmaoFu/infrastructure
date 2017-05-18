package com.infrastructure.dao.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.District;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DistrictMapper
 *
 * @author tyq
 * @date 2016/1/14
 */
@Repository
public interface DistrictMapper extends BaseMapper<District, String> {

    /**
     * 查找所有的地区
     *
     * @return
     */
    List<District> findAll();

    /**
     * 查找所有的下级地区
     *
     * @param code 地区编码
     * @return
     */
    List<District> findChildrens(String code);

    /**
     * 根据code查询信息
     * @param district
     * @return
     */
    District queryDistrictByCode(District district);
}
