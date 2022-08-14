package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.repository.IVariedadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hteecommerce.hteapp.entity.Variedad;

@Service
public class VariedadServiceImplements implements IVariedadService {

    @Autowired
    private IVariedadRepository variedadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Variedad> getAll() {
        
        return variedadRepository.findAll();
    }

    @Override
    @Transactional
    public void saveVA(Variedad variedad) {
        
        variedadRepository.save(variedad);
        
    }

    @Override
    @Transactional
    public void deleteVA(Integer idvariedad) {
        
        variedadRepository.deleteById(idvariedad);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Variedad getByIdvariedad(Integer idvariedad) {
        
        return variedadRepository.findById(idvariedad).orElse(null);
    }    
    
}
