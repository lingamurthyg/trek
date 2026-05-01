package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.InventoryForm;
import com.acme.cargotrak.util.Util;

public class InventoryListAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        InventoryForm ifm = (InventoryForm) form;
        if (Util.isNotBlank(ifm.getFragment())) {
            req.setAttribute("items", cargo().searchInventory(ifm.getFragment()));
        } else if (ifm.getZoneId() != null) {
            req.setAttribute("items", cargo().listInventory(ifm.getZoneId()));
        }
        req.setAttribute("warehouses", cargo().listWarehouses());
        return mapping.findForward("success");
    }
}
