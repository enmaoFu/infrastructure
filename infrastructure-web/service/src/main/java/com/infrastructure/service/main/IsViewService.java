package com.infrastructure.service.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.main.IsViewMapper;
import com.infrastructure.entity.main.IsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 谭永强 on 2016/5/6.
 */
@Service
public class IsViewService extends BaseService<IsView,String> implements IIsViewService{

    @Autowired
    private IsViewMapper isViewMapper;

    @Override
    public BaseMapper<IsView, String> getMapper() {
        return isViewMapper;
    }

    /**
     * 查询此数据是否已查看
     * @param isView
     * @return
     */
    @Override
    public IsView findByView(IsView isView) {
        return isViewMapper.findByView(isView);
    }
}
