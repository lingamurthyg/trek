package com.acme.cargotrak.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.ReportForm;
import com.acme.cargotrak.util.DateUtils;
import com.acme.cargotrak.util.Util;

public class ReportRevenueAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ReportForm rf = (ReportForm) form;
        Date from = Util.parseDate(rf.getFromDate());
        Date to = Util.parseDate(rf.getToDate());
        if (from == null) from = DateUtils.addDays(new Date(), -90);
        if (to == null) to = new Date();
        if ("xls".equalsIgnoreCase(rf.getFormat())) {
            File xls = cargo().generateRevenueExcel(from, to);
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + xls.getName() + "\"");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(xls);
                Util.copy(fis, res.getOutputStream());
            } finally {
                try { if (fis != null) fis.close(); } catch (Exception ex) {}
            }
            return null;
        }
        req.setAttribute("rows", cargo().revenueReport(from, to));
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        return mapping.findForward("success");
    }
}
