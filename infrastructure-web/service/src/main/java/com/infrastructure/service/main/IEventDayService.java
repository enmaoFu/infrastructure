package com.infrastructure.service.main;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.main.EventDay;

import java.util.List;

/**
 * Created by 谭永强 on 2016/4/27.
 */
public interface IEventDayService extends IBaseService<EventDay,String> {

    List<EventDay> findByUId(String userId);

    /**
     * 查询事件列表
     * @param eventDay
     * @return
     */
    List<Regime> load(EventDay eventDay);

}
