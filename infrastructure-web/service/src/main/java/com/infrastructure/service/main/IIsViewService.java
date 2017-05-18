package com.infrastructure.service.main;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.main.IsView;

/**
 * Created by 谭永强 on 2016/5/6.
 */
public interface IIsViewService extends IBaseService<IsView,String> {

    /**
     * 查询此数据是否已查看
     * @param isView
     * @return
     */
    IsView findByView(IsView isView);

}
