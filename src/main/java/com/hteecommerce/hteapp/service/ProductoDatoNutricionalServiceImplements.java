package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;
import com.hteecommerce.hteapp.repository.IProductoDatoNutricionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoDatoNutricionalServiceImplements implements IProductoDatoNutricionalService {

    @Autowired
    private IProductoDatoNutricionalRepository productoDatoNutricionalRepository;

    @Override
    public ProductoDatoNutricional saveDN(ProductoDatoNutricional pdn) {
        
        return productoDatoNutricionalRepository.save(pdn);
    }
    
}
