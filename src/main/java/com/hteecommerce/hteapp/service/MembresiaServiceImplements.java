package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Membresia;
import com.hteecommerce.hteapp.repository.IMembresiaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembresiaServiceImplements implements IMembresiaService {

    @Autowired
    private IMembresiaRepository membresiaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Membresia> getAll() {
        
        return membresiaRepository.findAll();
    }

    @Override
    @Transactional
    public Membresia saveM(Membresia membresia) {
        
        return membresiaRepository.save(membresia);
    }

    @Override
    @Transactional
    public void deleteM(Integer idmembresia) {
        
        membresiaRepository.deleteById(idmembresia);
    }

    @Override
    @Transactional(readOnly = true)
    public Membresia getByIdmembresia(Integer idmembresia) {
        
        return membresiaRepository.findById(idmembresia).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Membresia> getByEstado(String estado) {
        
        return membresiaRepository.findByEstadoOrderByIdmembresiaDesc(estado);
    }
    
}
