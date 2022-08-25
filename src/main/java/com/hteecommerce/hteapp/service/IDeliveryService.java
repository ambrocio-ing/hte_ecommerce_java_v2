package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Delivery;

public interface IDeliveryService {
    
    public List<Delivery> getAll();
    public void saveDEL(Delivery delivery);
    public void deleteDEL(Integer iddelivery);
    public Delivery getByIddelivery(Integer iddelivery);   

    public List<Delivery> getBySucursal(String sucursal);

    public boolean isExistsByEmpresaAndSucursal(String empresa, String sucursal);
    public boolean isExistsByEmpresaAndSucursalAndIddelivery(String empresa, String sucursal, Integer iddelivery);   

}
