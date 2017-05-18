package com.infrastructure.dao.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.main.AnnOrMeet;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/11.
 */
@Repository
public interface AnnOrMeetMapper extends BaseMapper<AnnOrMeet,String> {

    /**
     * 查询通知公告/会议通知
     * @param annOrMeet
     * @return
     */
    List<AnnOrMeet> queryAnnOrMeet(AnnOrMeet annOrMeet, Pagination page);

}
