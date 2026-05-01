package com.acme.cargotrak.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.form.InventoryForm;
import com.acme.cargotrak.service.WarehouseService;
import com.acme.cargotrak.util.SpringContextHolder;

public class InventoryTransferAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        InventoryForm ifm = (InventoryForm) form;
        WarehouseService ws = (WarehouseService) SpringContextHolder.getBean("warehouseService");
        ws.transferInventory(ifm.getItemId(), ifm.getDestZoneId(), ifm.getTransferQty());
        cargo().auditAction(currentUsername(req), "TRANSFER", "Inventory", String.valueOf(ifm.getItemId()),
                            "to zone " + ifm.getDestZoneId() + " qty=" + ifm.getTransferQty());
        return mapping.findForward("success");
    }
}
