package com.infrastructure.service.system;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.District;

import java.util.List;

/**
 * IDistrictService
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface IDistrictService extends IBaseService<District, String> {
    
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
