package com.acme.cargotrak.service;

import java.util.Date;
import java.util.List;

import com.acme.cargotrak.dao.InventoryDao;
import com.acme.cargotrak.dao.WarehouseDao;
import com.acme.cargotrak.dao.WarehouseZoneDao;
import com.acme.cargotrak.domain.InventoryItem;
import com.acme.cargotrak.domain.Warehouse;
import com.acme.cargotrak.domain.WarehouseZone;

public class WarehouseService {

    private WarehouseDao warehouseDao;
    private WarehouseZoneDao zoneDao;
    private InventoryDao inventoryDao;

    public void setWarehouseDao(WarehouseDao w) { this.warehouseDao = w; }
    public void setZoneDao(WarehouseZoneDao z) { this.zoneDao = z; }
    public void setInventoryDao(InventoryDao i) { this.inventoryDao = i; }

    public List listWarehouses() { return warehouseDao.findAll(Warehouse.class); }
    public Warehouse findWarehouse(Integer id) { return (Warehouse) warehouseDao.findById(Warehouse.class, id); }
    public Integer createWarehouse(Warehouse w) { return (Integer) warehouseDao.save(w); }
    public void updateWarehouse(Warehouse w) { warehouseDao.update(w); }

    public List listZones(Integer warehouseId) { return zoneDao.findByWarehouse(warehouseId); }
    public Integer createZone(Integer warehouseId, WarehouseZone z) {
        Warehouse w = findWarehouse(warehouseId);
        if (w == null) return null;
        z.setWarehouse(w);
        return (Integer) zoneDao.save(z);
    }

    public List listInventory(Integer zoneId) { return inventoryDao.findByZone(zoneId); }
    public List searchInventory(String fragment) { return inventoryDao.searchBySku(fragment); }

    public Integer addInventory(InventoryItem item) {
        if (item.getLastUpdated() == null) item.setLastUpdated(new Date());
        return (Integer) inventoryDao.save(item);
    }

    public void transferInventory(Integer itemId, Integer destZoneId, Integer qty) {
        InventoryItem src = (InventoryItem) inventoryDao.findById(InventoryItem.class, itemId);
        if (src == null || qty == null || qty.intValue() <= 0) return;
        if (src.getQuantity() == null || src.getQuantity().intValue() < qty.intValue()) return;
        src.setQuantity(new Integer(src.getQuantity().intValue() - qty.intValue()));
        src.setLastUpdated(new Date());
        inventoryDao.update(src);

        InventoryItem dst = new InventoryItem();
        dst.setSku(src.getSku());
        dst.setDescription(src.getDescription());
        dst.setZoneId(destZoneId);
        dst.setQuantity(qty);
        dst.setWeightKg(src.getWeightKg());
        dst.setLastUpdated(new Date());
        inventoryDao.save(dst);
    }
}
