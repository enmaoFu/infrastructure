package com.infrastructure.common.able;

/**
 * 同步接口
 *
 * @author tyq
 * @data 2016/1/11
 */
public interface Synchronizable {

    /**
     * 设置创建时间
     *
     * @param createdTime
     */
    void setCreatedTime(Long createdTime);

    /**
     * 获取创建时间
     *
     * @return
     */
    Long getCreatedTime();

    /**
     * 设置更新时间
     *
     * @param updatedTime
     */
    void setUpdatedTime(Long updatedTime);

    /**
     * 获取更新时间
     *
     * @return
     */
    Long getUpdatedTime();
}
