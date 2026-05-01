package com.acme.cargotrak.dao;

import java.util.List;

public interface LoginHistoryDao extends BaseDao {
    List findRecentByUser(Integer userId, int limit);
}
