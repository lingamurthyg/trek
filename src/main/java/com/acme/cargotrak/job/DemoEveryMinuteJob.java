package com.acme.cargotrak.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.service.AuditService;
import com.acme.cargotrak.util.SpringContextHolder;
import com.acme.cargotrak.util.Util;

/**
 * Demo job that fires every minute. Used to confirm Quartz wiring at startup.
 * Drops an audit_log row each fire.
 */
public class DemoEveryMinuteJob implements Job {

    private static final Logger logger = Logger.getLogger(DemoEveryMinuteJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            AuditService a = (AuditService) SpringContextHolder.getBean("auditService");
            String when = Util.formatDateTime(new Date());
            a.log("scheduler", "DEMO_TICK", "System", "0", "demo job ticked at " + when);
            logger.info("DemoEveryMinuteJob ticked at " + when);
        } catch (Exception e) {
            logger.error("DemoEveryMinuteJob failed", e);
        }
    }
}
