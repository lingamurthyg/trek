package com.acme.cargotrak.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.service.BillingService;
import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Generates invoices for all delivered shipments that don't already have one.
 * Runs nightly at 01:00.
 */
public class NightlyInvoiceJob implements Job {

    private static final Logger logger = Logger.getLogger(NightlyInvoiceJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            BillingService bs = (BillingService) SpringContextHolder.getBean("billingService");
            int n = bs.generateInvoicesForAllDelivered("scheduler");
            logger.info("NightlyInvoiceJob generated " + n + " invoices");
        } catch (Exception e) {
            logger.error("NightlyInvoiceJob failed", e);
        }
    }
}
