package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.domain.Warehouse;
import com.acme.cargotrak.form.WarehouseForm;
import com.acme.cargotrak.service.WarehouseService;
import com.acme.cargotrak.util.SpringContextHolder;

public class WarehouseSaveAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        WarehouseForm wf = (WarehouseForm) form;
        WarehouseService ws = (WarehouseService) SpringContextHolder.getBean("warehouseService");
        Warehouse w;
        if (wf.getWarehouseId() != null) {
            w = ws.findWarehouse(wf.getWarehouseId());
            if (w == null) w = new Warehouse();
        } else {
            w = new Warehouse();
        }
        w.setCode(wf.getCode());
        w.setName(wf.getName());
        w.setCity(wf.getCity());
        w.setCapacityM3(wf.getCapacityM3());
        Integer id;
        if (wf.getWarehouseId() == null) {
            id = ws.createWarehouse(w);
        } else {
            id = wf.getWarehouseId();
            ws.updateWarehouse(w);
        }
        req.setAttribute("savedId", id);
        return mapping.findForward("success");
    }
}
