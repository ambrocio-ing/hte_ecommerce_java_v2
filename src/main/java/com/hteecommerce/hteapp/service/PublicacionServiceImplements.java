package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Publicacion;
import com.hteecommerce.hteapp.repository.IPublicacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicacionServiceImplements implements IPublicacionService {

    @Autowired
    private IPublicacionRepository publicacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Publicacion> getAll() {
        
        return publicacionRepository.findAll();
    }

    @Override
    @Transactional
    public Publicacion saveP(Publicacion publicacion) {
        
        return publicacionRepository.save(publicacion);
    }

    @Override
    @Transactional
    public void deleteP(Integer idpublicacion) {
        
        publicacionRepository.deleteById(idpublicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Publicacion getByIdpublicacion(Integer idpublicacion) {
        
        return publicacionRepository.findById(idpublicacion).orElse(null);
    }

    @Override
    public List<Publicacion> getByEstado(String estado) {
        
        return publicacionRepository.findByEstado(estado);
    }
    
}
