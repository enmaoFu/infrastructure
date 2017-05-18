package com.infrastructure.service.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.DistrictMapper;
import com.infrastructure.entity.system.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DistrictService
 *
 * @author tyq
 * @date 2016/1/14
 */
@Service
public class DistrictService extends BaseService<District, String> implements IDistrictService {

    @Autowired
    private DistrictMapper dmp;

    @Override
    public BaseMapper<District, String> getMapper() {
        return dmp;
    }

    @Override
    public List<District> findAll() {
        return dmp.findAll();
    }

    @Override
    public List<District> findChildrens(String code) {
        return dmp.findChildrens(code);
    }

    @Override
    public District queryDistrictByCode(District district) {
        return dmp.queryDistrictByCode(district);
    }
}
