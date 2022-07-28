package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ProductoVestimenta;
import com.hteecommerce.hteapp.repository.IProductoVestimentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoVestimentaServiceImplements implements IProductoVestimentaService {

    @Autowired
    private IProductoVestimentaRepository productoVestimentaRepository;

    @Override
    public ProductoVestimenta savePV(ProductoVestimenta pv) {
        
        return productoVestimentaRepository.save(pv);
    }
    
}
