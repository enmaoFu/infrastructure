package com.infrastructure.dao.system;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.system.UserLoginTime;
import com.infrastructure.entity.userLoginTime.UserLoginTimeChart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 记录用户登录/模块点击时间
 * Created by suyl on 2016/5/23.
 */
@Repository
public interface UserLoginTimeMapper extends BaseMapper<UserLoginTime, String> {

    /**
     * 根据时间查询用户登录情况
     * @param time 时间
     * @return
     */
    List<UserLoginTimeChart> queryLoginUsers(String time);
}
