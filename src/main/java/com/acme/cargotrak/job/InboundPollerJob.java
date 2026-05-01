package com.acme.cargotrak.job;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.acme.cargotrak.ingestion.IngestResult;
import com.acme.cargotrak.service.IngestionService;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Polls /var/app/cargo/inbound/ for ANY supported file type
 * (.csv -> manifest, .edi204 -> load tender, .edi214 -> status update),
 * routes by extension, then moves the file into /var/app/cargo/archive/<yyyyMMddHHmmss>/.
 *
 * In production this runs every 30 minutes. For demo purposes the bean wiring
 * in applicationContext.xml triggers it every 2 minutes so dropped files get
 * picked up promptly.
 *
 * @author L. Chen 2013-04 (originally just for EDI214; extended in 2014 by S. Patel)
 *
 * NOTE: the older HourlyCarrierFeedJob still exists for the EDI214-only nightly
 * carrier feed. Yes, both run. Yes, they share the inbound directory. Ops sets
 * file naming conventions so they don't collide. miyer 2014.
 */
public class InboundPollerJob implements Job {

    private static final Logger logger = Logger.getLogger(InboundPollerJob.class);
    private static final SimpleDateFormat ARCHIVE_TS = new SimpleDateFormat("yyyyMMddHHmmss");

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            FileUtil.ensureDir(Constants.INBOUND_DIR);
            FileUtil.ensureDir(Constants.ARCHIVE_DIR);
            File inDir = new File(Constants.INBOUND_DIR);
            File[] files = inDir.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            IngestionService ing = (IngestionService) SpringContextHolder.getBean("ingestionService");
            String stamp = ARCHIVE_TS.format(new java.util.Date());
            File archiveDir = new File(Constants.ARCHIVE_DIR, stamp);
            FileUtil.ensureDir(archiveDir.getAbsolutePath());

            int csv = 0, e204 = 0, e214 = 0, skipped = 0;
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (!f.isFile()) continue;
                String name = f.getName().toLowerCase();
                IngestResult result = null;
                try {
                    if (name.endsWith(".csv")) {
                        result = ing.ingestCsvManifest(f, "carrier-feed@cargotrak.local");
                        csv++;
                    } else if (name.endsWith(".edi204")) {
                        result = ing.ingestEdi204(f, "carrier-feed@cargotrak.local");
                        e204++;
                    } else if (name.endsWith(".edi214")) {
                        result = ing.ingestEdi214(f, "carrier-feed@cargotrak.local");
                        e214++;
                    } else {
                        // unknown extension -- skip without archiving so ops can investigate
                        skipped++;
                        logger.warn("InboundPollerJob: unknown file type, leaving in place: " + f.getName());
                        continue;
                    }
                    if (result != null) {
                        logger.info("InboundPollerJob: " + f.getName()
                                + " accepted=" + result.getAccepted()
                                + " rejected=" + result.getRejected());
                    }
                    File dest = new File(archiveDir, f.getName());
                    if (!f.renameTo(dest)) {
                        logger.warn("InboundPollerJob: could not archive " + f);
                    }
                } catch (Exception fileEx) {
                    logger.error("InboundPollerJob: failed processing " + f.getName(), fileEx);
                }
            }
            if (csv + e204 + e214 + skipped > 0) {
                logger.info("InboundPollerJob tick: csv=" + csv + " edi204=" + e204
                        + " edi214=" + e214 + " skipped=" + skipped
                        + " archived=" + archiveDir.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("InboundPollerJob failed", e);
        }
    }
}
