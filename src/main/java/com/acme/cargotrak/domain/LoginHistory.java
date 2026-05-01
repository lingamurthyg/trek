package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long loginId;
    private Integer userId;
    private String username;
    private Date loginTime;
    private String ipAddress;
    private String successFlag;

    public Long getLoginId() { return loginId; }
    public void setLoginId(Long loginId) { this.loginId = loginId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Date getLoginTime() { return loginTime; }
    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getSuccessFlag() { return successFlag; }
    public void setSuccessFlag(String successFlag) { this.successFlag = successFlag; }
}
