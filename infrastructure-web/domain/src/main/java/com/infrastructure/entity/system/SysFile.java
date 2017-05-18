package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by 谭永强 on 2016/3/15.
 */
public class SysFile extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = 2333245693676813263L;
    private String attachmentId;//附件ID
    private String originalFileName;//原文件名称
    private String nowFileName;//新文件名称
    private String suff;//文件后缀

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNowFileName() {
        return nowFileName;
    }

    public void setNowFileName(String nowFileName) {
        this.nowFileName = nowFileName;
    }

    public String getSuff() {
        return suff;
    }

    public void setSuff(String suff) {
        this.suff = suff;
    }
}
