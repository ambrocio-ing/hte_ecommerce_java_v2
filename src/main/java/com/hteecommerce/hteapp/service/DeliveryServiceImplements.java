package com.hteecommerce.hteapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hteecommerce.hteapp.entity.Delivery;
import com.hteecommerce.hteapp.repository.IDeliveryRepository;

@Service
public class DeliveryServiceImplements implements IDeliveryService {

    @Autowired
    private IDeliveryRepository deliveryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> getAll() {
        
        return deliveryRepository.findAll();
    }

    @Override
    @Transactional
    public void saveDEL(Delivery delivery) {
        
        deliveryRepository.save(delivery);
        
    }

    @Override
    @Transactional
    public void deleteDEL(Integer iddelivery) {
        
        deliveryRepository.deleteById(iddelivery);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Delivery getByIddelivery(Integer iddelivery) {
        
        return deliveryRepository.findById(iddelivery).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByEmpresaAndSucursal(String empresa, String sucursal) {
        
        return deliveryRepository.existsByEmpresaAndSucursal(empresa, sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByEmpresaAndSucursalAndIddelivery(String empresa, String sucursal, Integer iddelivery) {
        
        Delivery del = deliveryRepository.getByEmpresaAndSucursalAndIddelivery(empresa, sucursal, iddelivery).orElse(null);
        if(del != null){
            return true;
        }
        
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> getBySucursal(String sucursal) {
        
        return deliveryRepository.findBySucursal(sucursal);
    }    
    
}
