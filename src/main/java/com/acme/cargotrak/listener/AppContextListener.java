package com.acme.cargotrak.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.SpringContextHolder;

/**
 * Application bootstrap.
 *
 * Creates the on-disk directory tree the app expects.
 * Triggers the Spring context (already loaded by ContextLoaderListener)
 * to be exposed via SpringContextHolder for legacy code.
 */
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("CargoTrak starting up...");
        FileUtil.ensureDir(Constants.UPLOADS_DIR);
        FileUtil.ensureDir(Constants.REPORTS_DIR);
        FileUtil.ensureDir(Constants.LOGS_DIR);
        FileUtil.ensureDir(Constants.INBOUND_DIR);
        FileUtil.ensureDir(Constants.ARCHIVE_DIR);
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        // ensure the static holder is populated even though springContextHolder bean
        // already had setApplicationContext called on it.
        if (SpringContextHolder.getContext() == null) {
            SpringContextHolder h = (SpringContextHolder) ctx.getBean("springContextHolder");
            try { h.setApplicationContext(ctx); } catch (Exception e) { e.printStackTrace(); }
        }
        logger.info("CargoTrak ready.");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("CargoTrak shutting down...");
    }
}
