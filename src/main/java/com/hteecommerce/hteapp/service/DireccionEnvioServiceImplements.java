package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.repository.IDestinatarioRepository;
import com.hteecommerce.hteapp.repository.IDireccionEnvioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionEnvioServiceImplements implements IDireccionEnvioService {

    @Autowired
    private IDireccionEnvioRepository direccionEnvioRepository;

    @Autowired
    private IDestinatarioRepository destinatarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DireccionEnvio> getByCliente(Integer idcliente) {
        
        return direccionEnvioRepository.listByIdcliente(idcliente);
    }

    @Override
    @Transactional
    public DireccionEnvio saveDE(DireccionEnvio de) {    

        if(de.getDestinatario() != null){            
            de.setDestinatario(destinatarioRepository.save(de.getDestinatario()));            
        }
                
        return direccionEnvioRepository.save(de);
    }

    @Override
    @Transactional
    public void deleteDE(Integer idde) {
        
        direccionEnvioRepository.deleteById(idde);
    }

    @Override
    @Transactional(readOnly = true)
    public DireccionEnvio getByIddireccionenvio(Integer idde) {
        
        return direccionEnvioRepository.findById(idde).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByDni(String dni) {
        
        return destinatarioRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public Destinatario getByDni(String dni) {
        
        return destinatarioRepository.findByDni(dni).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Destinatario> getAllDestinatario(Pageable pageable) {
        
        return destinatarioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Destinatario getByIddestinatario(Integer iddestinatario) {
        
        return destinatarioRepository.findById(iddestinatario).orElse(null);
    }

    @Override
    @Transactional
    public void updateDES(Destinatario destinatario) {
        
        destinatarioRepository.save(destinatario);
    }
    
}
