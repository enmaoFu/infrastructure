package com.infrastructure.service.main;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.main.AnnOrMeet;
import net.oschina.archx.mybatis.Pagination;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/11.
 */
public interface IAnnOrMeetService extends IBaseService<AnnOrMeet,String> {

    /**
     * 查询通知公告/会议通知
     * @param annOrMeet
     * @return
     */
    List<AnnOrMeet> queryAnnOrMeet(AnnOrMeet annOrMeet, Pagination page);
}
