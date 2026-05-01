package com.acme.cargotrak.dao;

import java.util.List;

public interface NotificationDao extends BaseDao {
    List findRecent(int limit);
    List findUndelivered();
}
