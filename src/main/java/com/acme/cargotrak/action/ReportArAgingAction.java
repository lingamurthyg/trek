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
import com.acme.cargotrak.util.Util;

public class ReportArAgingAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        ReportForm rf = (ReportForm) form;
        Date asOf = Util.parseDate(rf.getAsOfDate());
        if (asOf == null) asOf = new Date();
        if ("pdf".equalsIgnoreCase(rf.getFormat())) {
            File pdf = cargo().generateArAgingPdf(asOf);
            res.setContentType("application/pdf");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + pdf.getName() + "\"");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(pdf);
                Util.copy(fis, res.getOutputStream());
            } finally {
                try { if (fis != null) fis.close(); } catch (Exception ex) {}
            }
            return null;
        }
        req.setAttribute("rows", cargo().arAgingReport(asOf));
        req.setAttribute("asOf", asOf);
        return mapping.findForward("success");
    }
}
