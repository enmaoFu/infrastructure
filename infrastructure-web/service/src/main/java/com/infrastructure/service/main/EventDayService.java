package com.infrastructure.service.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.main.EventDayMapper;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.main.EventDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 谭永强 on 2016/4/27.
 */
@Service
public class EventDayService extends BaseService<EventDay,String> implements IEventDayService {

    @Autowired
    private EventDayMapper eventDayMapper;

    @Override
    public BaseMapper<EventDay, String> getMapper() {
        return eventDayMapper;
    }

    @Override
    public List<EventDay> findByUId(String userId) {
        return eventDayMapper.findByUId(userId);
    }

    /**
     * 查询事件列表
     * @param eventDay
     * @return
     */
    @Override
    public List<Regime> load(EventDay eventDay) {
        return eventDayMapper.load(eventDay);
    }
}
