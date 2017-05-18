package com.infrastructure.entity.main;

import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 战略
 * Created by 谭永强 on 2016/4/21.
 */
public class Strategy extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = -6348818174483149125L;
    private String content;//战略内容
    private String companyId;//所属公司

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
