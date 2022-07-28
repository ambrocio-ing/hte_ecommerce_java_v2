package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ProductoOtros;
import com.hteecommerce.hteapp.repository.IProductoOtrosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoOtrosServiceImplements implements IProductoOtrosService {

    @Autowired
    private IProductoOtrosRepository productoOtrosRepository;

    @Override    
    public ProductoOtros savePO(ProductoOtros po) {
        
        return productoOtrosRepository.save(po);
    }
    
}
