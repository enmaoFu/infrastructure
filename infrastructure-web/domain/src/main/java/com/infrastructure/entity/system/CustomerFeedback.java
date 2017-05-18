package com.infrastructure.entity.system;

import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户反馈
 * Created by suyl on 2016/6/1.
 */
public class CustomerFeedback extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    /**
     * 反馈内容
     */
    private String content;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 创建人所属公司ID
     */
    private String companyId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 名称
     */
    private String userName;

    /**
     * 公司
     */
    private String coName;

    /**
     * 开始创建时间
     */
    private String stertsignDate;

    /**
     * 结束创建时间
     */
    private String endsignDate;

    public CustomerFeedback() {
        super();
    }

    public CustomerFeedback(String content, String userId, String companyId, Date createTime, String userName, String coName, String stertsignDate, String endsignDate) {
        this.content = content;
        this.userId = userId;
        this.companyId = companyId;
        this.createTime = createTime;
        this.userName = userName;
        this.coName = coName;
        this.stertsignDate = stertsignDate;
        this.endsignDate = endsignDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getStertsignDate() {
        return stertsignDate;
    }

    public void setStertsignDate(String stertsignDate) {
        this.stertsignDate = stertsignDate;
    }

    public String getEndsignDate() {
        return endsignDate;
    }

    public void setEndsignDate(String endsignDate) {
        this.endsignDate = endsignDate;
    }

    @Override
    public String toString() {
        return "CustomerFeedback{" +
                "content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", coName='" + coName + '\'' +
                ", stertsignDate='" + stertsignDate + '\'' +
                ", endsignDate='" + endsignDate + '\'' +
                '}';
    }
}
