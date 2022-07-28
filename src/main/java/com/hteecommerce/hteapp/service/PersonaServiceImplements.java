package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.repository.IPersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaServiceImplements implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    @Override   
    @Transactional
    public Persona saveP(Persona persona) {
        
        return personaRepository.save(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona getByDni(String dni) {
        
        return personaRepository.findById(dni).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsDni(String dni) {
        
        return personaRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsTelefono(String telefono) {
        
        return personaRepository.existsByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsTelefonoAndDni(String telefono, String dni) {
        
        Persona per = personaRepository.validTelefono(telefono, dni).orElse(null);
        if(per != null){
            return true;
        }
        
        return false;
    }
    
}
