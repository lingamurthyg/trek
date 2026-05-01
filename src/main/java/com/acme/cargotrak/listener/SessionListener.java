package com.acme.cargotrak.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener {

    private static final Logger logger = Logger.getLogger(SessionListener.class);

    private static int activeSessions = 0;

    public void sessionCreated(HttpSessionEvent se) {
        synchronized (SessionListener.class) {
            activeSessions++;
        }
        logger.debug("session created: " + se.getSession().getId() + " (active=" + activeSessions + ")");
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        synchronized (SessionListener.class) {
            activeSessions--;
        }
        logger.debug("session destroyed: " + se.getSession().getId() + " (active=" + activeSessions + ")");
    }

    public static int getActiveSessions() { return activeSessions; }
}
