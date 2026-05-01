package com.acme.cargotrak.action;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Util;

public class InvoicePdfAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        Integer id = Util.paramAsInteger(req, "id");
        if (id == null) return mapping.findForward("error");
        File pdf = cargo().generateInvoicePdf(id);
        if (pdf == null || !pdf.exists()) return mapping.findForward("error");
        res.setContentType("application/pdf");
        res.setHeader("Content-Disposition", "inline; filename=\"" + pdf.getName() + "\"");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pdf);
            Util.copy(fis, res.getOutputStream());
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception ex) {}
        }
        return null;
    }
}
