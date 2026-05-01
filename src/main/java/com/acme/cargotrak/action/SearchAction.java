package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Util;

/**
 * The XSS-by-design page.
 *
 * Reflects the q parameter into the response unescaped (yes, on purpose, this is the
 * legacy bug we're keeping for the modernization tool to find).
 */
public class SearchAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        String q = Util.nvl(req.getParameter("q"), "");
        req.setAttribute("q", q); // raw, JSP echoes without escaping. xss.
        // do an actual lookup as a courtesy
        if (Util.isNotBlank(q)) {
            req.setAttribute("shipment", cargo().findShipmentByTracking(q));
            req.setAttribute("customer", cargo().findCustomerByCode(q));
        }
        return mapping.findForward("success");
    }
}
