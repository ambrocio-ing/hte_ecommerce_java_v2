package com.hteecommerce.hteapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hteecommerce.hteapp.entity.LibroReclamo;
import com.hteecommerce.hteapp.entity.Sujerencia;
import com.hteecommerce.hteapp.repository.ILibroReclamoRepository;
import com.hteecommerce.hteapp.repository.ISujerenciaRepository;

@Service
public class LibroReclamoServiceImplements implements ILibroReclamoService {

    @Autowired
    private ILibroReclamoRepository libroReclamoRepository;

    @Autowired
    private ISujerenciaRepository sujerenciaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<LibroReclamo> getAll(Pageable pageable) {
        
        return libroReclamoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveLR(LibroReclamo libroReclamo) {
        
        libroReclamoRepository.save(libroReclamo);
        
    }

    @Override
    @Transactional
    public void deleteLR(Integer idlibro) {
        
        libroReclamoRepository.deleteById(idlibro);
        
    }

    @Override
    @Transactional(readOnly = true)
    public LibroReclamo getByIdlibro(Integer idlibro) {
        
        return libroReclamoRepository.findById(idlibro).orElse(null);
    }

    @Override
    public String getLatestNumero() {
        String numero = null;
        LibroReclamo lr = libroReclamoRepository.findTopByOrderByIdlibroreclamoDesc().orElse(null);
        if(lr != null){
            numero = lr.getNumero();
        }

        return numero;
    }

    //METOS PARA SUJERENCIA

    @Override
    @Transactional(readOnly = true)
    public Page<Sujerencia> getAllSujerencia(Pageable pageable) {
        
        return sujerenciaRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveSU(Sujerencia sujerencia) {
        
        sujerenciaRepository.save(sujerencia);
        
    }

    @Override
    @Transactional
    public void deleteSU(Integer idsujerencia) {
        
        sujerenciaRepository.deleteById(idsujerencia);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Sujerencia getByIdsujerencia(Integer idsujerencia) {
        
        return sujerenciaRepository.findById(idsujerencia).orElse(null);
    }    
    

}
