package com.infrastructure.service.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import com.infrastructure.dao.system.UserLoginTimeMapper;
import com.infrastructure.entity.system.UserLoginTime;
import com.infrastructure.entity.userLoginTime.UserLoginTimeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记录用户登录/模块点击时间-接口实现
 * User: suyl
 * Date: 2016/5/23
 */
@Service
public class UserLoginTimeService extends BaseService<UserLoginTime,String> implements IUserLoginTimeService{

    @Autowired
    private UserLoginTimeMapper userLoginTimeMapper;

    @Override
    public BaseMapper<UserLoginTime, String> getMapper() {
        return userLoginTimeMapper;
    }

    /**
     * 添加用户登录信息
     * @param userLoginTime
     * @return
     */
    @Override
    public int addUserLoginTime(UserLoginTime userLoginTime) {
        return userLoginTimeMapper.insert(userLoginTime);
    }

    /**
     * 根据时间查询用户登录情况
     * @param time 时间
     * @return
     */
    @Override
    public List<UserLoginTimeChart> queryLoginUsers(String time) {
        return userLoginTimeMapper.queryLoginUsers(time);
    }
}
