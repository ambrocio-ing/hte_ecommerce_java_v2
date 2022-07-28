package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.repository.IComentarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioServiceImplements implements IComentarioSerivce {

    @Autowired
    private IComentarioRepository comentarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comentario> getByIddetalleingreso(Integer iddi) {
        
        return comentarioRepository.listByIddetalleingreso(iddi);
    }

    @Override
    @Transactional
    public void saveCOM(Comentario comentario) {
        
        comentarioRepository.save(comentario);
        
    }

    @Override
    @Transactional
    public void deleteCOM(Integer idcomentario) {
        
        comentarioRepository.deleteById(idcomentario);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Comentario getByIdcomentario(Integer idcomentario) {
        
        return comentarioRepository.findById(idcomentario).orElse(null);
    }

    @Override
    public List<Comentario> getByIdproducto(Integer idproducto) {
        
        return comentarioRepository.listByIdproducto(idproducto);
    }
    
}
