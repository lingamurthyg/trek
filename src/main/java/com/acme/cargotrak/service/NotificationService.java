package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.NotificationDao;

public class NotificationService {

    private NotificationDao notificationDao;
    public void setNotificationDao(NotificationDao n) { this.notificationDao = n; }

    public List listRecent(int limit) { return notificationDao.findRecent(limit); }
    public List listUndelivered() { return notificationDao.findUndelivered(); }
}
