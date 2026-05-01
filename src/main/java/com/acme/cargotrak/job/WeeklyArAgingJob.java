package com.acme.cargotrak.job;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.service.MailService;
import com.acme.cargotrak.service.ReportService;
import com.acme.cargotrak.service.SystemConfigService;
import com.acme.cargotrak.util.SpringContextHolder;

public class WeeklyArAgingJob implements Job {

    private static final Logger logger = Logger.getLogger(WeeklyArAgingJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            ReportService rs = (ReportService) SpringContextHolder.getBean("reportService");
            MailService ms = (MailService) SpringContextHolder.getBean("mailService");
            SystemConfigService cs = (SystemConfigService) SpringContextHolder.getBean("systemConfigService");
            Date asOf = new Date();
            File pdf = rs.generateArAgingPdf(asOf);
            String to = cs.getOrDefault("finance.team.email", "finance@cargotrak.local");
            Map<String,String> p = new HashMap<String,String>();
            p.put("asOf", asOf.toString());
            p.put("path", pdf.getAbsolutePath());
            ms.sendByTemplate("AR_AGING_REPORT", to, p);
            logger.info("WeeklyArAgingJob completed: " + pdf.getAbsolutePath());
        } catch (Exception e) {
            logger.error("WeeklyArAgingJob failed", e);
        }
    }
}
