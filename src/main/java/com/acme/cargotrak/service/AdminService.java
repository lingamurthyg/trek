package com.acme.cargotrak.service;

import java.util.List;
import java.util.Map;

import com.acme.cargotrak.dao.ReportDao;

public class AdminService {

    private ReportDao reportDao;
    public void setReportDao(ReportDao r) { this.reportDao = r; }

    public List<Map<String,String>> showTableStatus() { return reportDao.showTableStatus(); }
    public List<List<String>> runQuery(String sql) { return reportDao.runSql(sql); }
    public int runUpdate(String sql) { return reportDao.runUpdate(sql); }
}
