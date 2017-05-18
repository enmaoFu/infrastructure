package com.infrastructure.service.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.entity.main.Strategy;
import com.infrastructure.dao.main.StrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 谭永强 on 2016/4/21.
 */
@Service
public class StrategyService extends BaseService<Strategy,String> implements IStrategyService{

    @Autowired
    private StrategyMapper strategyMapper;

    @Override
    public BaseMapper<Strategy, String> getMapper() {
        return strategyMapper;
    }

}
