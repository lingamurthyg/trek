package com.acme.cargotrak.dao;

import java.util.Date;
import java.util.List;

public interface AuditLogDao extends BaseDao {
    List findRecent(int limit);
    List findByEntity(String entityType, String entityId);
    List findBetween(Date from, Date to);
}
