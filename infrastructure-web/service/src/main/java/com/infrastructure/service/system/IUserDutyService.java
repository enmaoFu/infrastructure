package com.infrastructure.service.system;

/**
 * IUserDuty
 * 用户职责相关
 *
 * @author tyq
 * @date 2016/1/14
 */
public interface IUserDutyService {

    /**
     * 得到用户  相关职责
     *
     * @param id
     * @return
     */
    String queryUserDuty(String id);


    /**
     * 查询组成员
     *
     * @param id
     * @return
     */
    String selectGroupMember(String id);

}
