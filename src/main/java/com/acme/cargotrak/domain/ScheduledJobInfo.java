package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * NOTE: named ScheduledJobInfo and not ScheduledJob to avoid clashing with quartz Job class
 * once somebody (probably miyer) imports the wrong one in the IDE.
 */
public class ScheduledJobInfo implements Serializable {

    private Integer jobId;
    private String jobName;
    private String cronExpr;
    private Date lastRun;
    private Date nextRun;
    private String enabledFlag;

    public Integer getJobId() { return jobId; }
    public void setJobId(Integer jobId) { this.jobId = jobId; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
    public String getCronExpr() { return cronExpr; }
    public void setCronExpr(String cronExpr) { this.cronExpr = cronExpr; }
    public Date getLastRun() { return lastRun; }
    public void setLastRun(Date lastRun) { this.lastRun = lastRun; }
    public Date getNextRun() { return nextRun; }
    public void setNextRun(Date nextRun) { this.nextRun = nextRun; }
    public String getEnabledFlag() { return enabledFlag; }
    public void setEnabledFlag(String enabledFlag) { this.enabledFlag = enabledFlag; }
}
