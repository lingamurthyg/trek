package com.acme.cargotrak.job;

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.service.IngestionService;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Polls /var/app/cargo/inbound/ for carrier-feed status update files
 * (EDI 214 fixed-width) and runs them through the ingestion pipeline.
 */
public class HourlyCarrierFeedJob implements Job {

    private static final Logger logger = Logger.getLogger(HourlyCarrierFeedJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            FileUtil.ensureDir(Constants.INBOUND_DIR);
            FileUtil.ensureDir(Constants.ARCHIVE_DIR);
            File dir = new File(Constants.INBOUND_DIR);
            File[] files = dir.listFiles();
            if (files == null) return;
            IngestionService ing = (IngestionService) SpringContextHolder.getBean("ingestionService");
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (!f.isFile()) continue;
                if (!f.getName().toLowerCase().endsWith(".edi214")) continue;
                logger.info("Processing carrier feed file: " + f.getName());
                ing.ingestEdi214(f, "ops@cargotrak.local");
                File archived = new File(Constants.ARCHIVE_DIR, f.getName() + "." + System.currentTimeMillis());
                if (!f.renameTo(archived)) {
                    logger.warn("could not archive " + f);
                }
            }
        } catch (Exception e) {
            logger.error("HourlyCarrierFeedJob failed", e);
        }
    }
}
