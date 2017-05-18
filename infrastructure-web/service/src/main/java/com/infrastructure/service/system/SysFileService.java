package com.infrastructure.service.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.SysFileMapper;
import com.infrastructure.entity.system.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 谭永强 on 2016/3/16.
 */
@Service
public class SysFileService extends BaseService<SysFile,String> implements ISysFileService{

    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public BaseMapper<SysFile, String> getMapper() {
        return sysFileMapper;
    }
}
