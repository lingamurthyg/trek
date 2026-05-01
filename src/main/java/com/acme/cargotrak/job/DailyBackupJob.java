package com.acme.cargotrak.job;

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;

/**
 * Copies everything in /var/app/cargo/uploads/ into /var/app/cargo/archive/<yyyymmdd>/
 * once a day.
 */
public class DailyBackupJob implements Job {

    private static final Logger logger = Logger.getLogger(DailyBackupJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            FileUtil.ensureDir(Constants.ARCHIVE_DIR);
            File src = new File(Constants.UPLOADS_DIR);
            String stamp = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
            File dest = new File(Constants.ARCHIVE_DIR, stamp);
            FileUtil.ensureDir(dest.getAbsolutePath());
            File[] files = src.listFiles();
            if (files == null) return;
            int copied = 0;
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isFile()) continue;
                File out = new File(dest, files[i].getName());
                FileUtil.copyFile(files[i], out);
                copied++;
            }
            logger.info("DailyBackupJob copied " + copied + " files to " + dest.getAbsolutePath());
        } catch (Exception e) {
            logger.error("DailyBackupJob failed", e);
        }
    }
}
