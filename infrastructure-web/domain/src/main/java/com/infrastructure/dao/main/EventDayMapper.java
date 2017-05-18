package com.infrastructure.dao.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.main.EventDay;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 谭永强 on 2016/4/27.
 */
@Repository
public interface EventDayMapper extends BaseMapper<EventDay,String> {

    List<EventDay> findByUId(String userId);

    /**
     * 事件列表
     * @param eventDay
     * @return
     */
    List<Regime> load(EventDay eventDay);
}
