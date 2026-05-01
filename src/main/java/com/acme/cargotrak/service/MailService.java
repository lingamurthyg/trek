package com.acme.cargotrak.service;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.acme.cargotrak.dao.EmailTemplateDao;
import com.acme.cargotrak.dao.NotificationDao;
import com.acme.cargotrak.dao.SystemConfigDao;
import com.acme.cargotrak.domain.EmailTemplate;
import com.acme.cargotrak.domain.Notification;
import com.acme.cargotrak.util.Util;

/**
 * Mail service. Spawns raw threads. No executor.
 *
 * @author Rajesh Kumar 2009-01
 * @author L. Chen 2012-08 added template-based send
 */
public class MailService {

    private static final Logger logger = Logger.getLogger(MailService.class);

    private EmailTemplateDao emailTemplateDao;
    private NotificationDao notificationDao;
    private SystemConfigDao systemConfigDao;

    public void setEmailTemplateDao(EmailTemplateDao d) { this.emailTemplateDao = d; }
    public void setNotificationDao(NotificationDao d) { this.notificationDao = d; }
    public void setSystemConfigDao(SystemConfigDao d) { this.systemConfigDao = d; }

    public void sendAsync(final String to, final String subject, final String body) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                doSend(to, subject, body);
            }
        }, "mail-" + System.currentTimeMillis());
        t.start();
    }

    public void sendByTemplate(String code, String to, Map<String,?> params) {
        EmailTemplate tpl = emailTemplateDao.findByCode(code);
        if (tpl == null) {
            logger.warn("no template for " + code);
            return;
        }
        String subject = Util.interpolate(tpl.getSubject(), params);
        String body    = Util.interpolate(tpl.getBody(), params);
        String resolvedTo = to;
        if (resolvedTo == null) {
            resolvedTo = systemConfigDao.getValue("finance.team.email");
            if (resolvedTo == null) resolvedTo = "noreply@cargotrak.local";
        }
        sendAsync(resolvedTo, subject, body);
    }

    private void doSend(String to, String subject, String body) {
        // log to db
        try {
            Notification n = new Notification();
            n.setSubject(subject);
            n.setBody(body);
            n.setSentAt(new Date());
            n.setDeliveredFlag("Y");
            notificationDao.save(n);
        } catch (Exception e) {
            logger.error("error");
        }
        // attempt actual SMTP send
        try {
            String host = systemConfigDao.getValue("mail.smtp.host");
            String port = systemConfigDao.getValue("mail.smtp.port");
            String from = systemConfigDao.getValue("mail.from");
            if (host == null) host = "localhost";
            if (port == null) port = "25";
            if (from == null) from = "noreply@cargotrak.local";

            Properties p = new Properties();
            p.put("mail.smtp.host", host);
            p.put("mail.smtp.port", port);
            Session session = Session.getInstance(p);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            if (to != null) {
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            }
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            logger.info("Sent mail to " + to + " subject='" + subject + "'");
        } catch (MessagingException me) {
            // mail server not up in dev: fall back to logging
            logger.warn("SMTP send failed (probably no mail server in dev): " + me.getMessage());
            logger.info("--- Mail (would-have-sent) ---");
            logger.info("To: " + to);
            logger.info("Subject: " + subject);
            logger.info(body);
            logger.info("--- end ---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
