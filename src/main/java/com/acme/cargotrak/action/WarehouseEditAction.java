package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Warehouse;
import com.acme.cargotrak.form.WarehouseForm;
import com.acme.cargotrak.util.Util;

public class WarehouseEditAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        WarehouseForm wf = (WarehouseForm) form;
        Integer id = Util.paramAsInteger(req, "id");
        if (id != null) {
            Warehouse w = cargo().findWarehouse(id);
            if (w != null) {
                wf.setWarehouseId(w.getWarehouseId());
                wf.setCode(w.getCode());
                wf.setName(w.getName());
                wf.setCity(w.getCity());
                wf.setCapacityM3(w.getCapacityM3());
                req.setAttribute("warehouse", w);
                req.setAttribute("zones", cargo().listWarehouseZones(id));
            }
        }
        return mapping.findForward("success");
    }
}
