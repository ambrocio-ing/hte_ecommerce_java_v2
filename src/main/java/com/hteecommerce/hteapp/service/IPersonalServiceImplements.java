package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Personal;
import com.hteecommerce.hteapp.repository.IPersonalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IPersonalServiceImplements implements IPersonalService {
    
    @Autowired
    private IPersonalRepository personalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Personal> getAll() {
        
        return personalRepository.findAll();
    }

    @Override
    @Transactional
    public void savePe(Personal personal) {
        
        personalRepository.save(personal);
        
    }

    @Override
    @Transactional
    public void deletePe(Integer idpersonal) {
        
        personalRepository.deleteById(idpersonal);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Personal getByIdpersonal(Integer idpersonal) {
        
        return personalRepository.findById(idpersonal).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Personal getByUsername(String username) {
        
        return personalRepository.personalByUsername(username).orElse(null);
    }

}
