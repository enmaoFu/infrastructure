package com.infrastructure.service.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.main.AnnOrMeetMapper;
import com.infrastructure.entity.main.AnnOrMeet;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 谭永强 on 2016/5/11.
 */
@Service
public class AnnOrMeetService extends BaseService<AnnOrMeet,String> implements IAnnOrMeetService{

    @Autowired
    private AnnOrMeetMapper annOrMeetMapper;

    @Override
    public BaseMapper<AnnOrMeet, String> getMapper() {
        return annOrMeetMapper;
    }

    @Override
    public List<AnnOrMeet> queryAnnOrMeet(AnnOrMeet annOrMeet,Pagination page) {
        return annOrMeetMapper.queryAnnOrMeet(annOrMeet,page);
    }
}
