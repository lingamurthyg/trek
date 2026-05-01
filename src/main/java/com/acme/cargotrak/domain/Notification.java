package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    private Long notificationId;
    private Integer userId;
    private String subject;
    private String body;
    private Date sentAt;
    private String deliveredFlag;

    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }
    public String getDeliveredFlag() { return deliveredFlag; }
    public void setDeliveredFlag(String deliveredFlag) { this.deliveredFlag = deliveredFlag; }
}
