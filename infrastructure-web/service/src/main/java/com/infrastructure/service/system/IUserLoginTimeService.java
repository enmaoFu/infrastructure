package com.infrastructure.service.system;

import com.infrastructure.common.service.IBaseService;
import com.infrastructure.entity.system.UserLoginTime;
import com.infrastructure.entity.userLoginTime.UserLoginTimeChart;

import java.util.List;

/**
 * 记录用户登录/模块点击时间-接口
 * User: suyl
 * Date: 2016/5/23
 */
public interface IUserLoginTimeService extends IBaseService<UserLoginTime,String> {

    /**
     * 新增
     * @param userLoginTime
     * @return
     */
    int addUserLoginTime(UserLoginTime userLoginTime);

    /**
     * 根据时间查询用户登录情况
     * @param time 时间
     * @return
     */
    List<UserLoginTimeChart> queryLoginUsers(String time);
}
