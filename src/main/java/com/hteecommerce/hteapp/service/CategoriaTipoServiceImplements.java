package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Categoria;
import com.hteecommerce.hteapp.entity.Tipo;
import com.hteecommerce.hteapp.repository.ICategoriaRepository;
import com.hteecommerce.hteapp.repository.ITipoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaTipoServiceImplements implements ICategoriaTipoService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired    
    private ITipoRepository tipoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCAT(Categoria categoria) {
        
        categoriaRepository.save(categoria);
        
    }

    @Override
    @Transactional
    public void deleteCAT(Integer idcategoria) {
        
        categoriaRepository.deleteById(idcategoria);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoriaByIdcategoria(Integer idcategoria) {
        
        return categoriaRepository.findById(idcategoria).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoriaByNombre(String nombre) {
        
        return categoriaRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistCatByNombre(String nombre) {
        
        return categoriaRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tipo> getTipos() {
        
        return tipoRepository.findAll();
    }

    @Override
    @Transactional
    public void saveTI(Tipo tipo) {
        
        tipoRepository.save(tipo);
        
    }

    @Override
    @Transactional
    public void deleteTI(Integer idtipo) {
        
        tipoRepository.deleteById(idtipo);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo getTipoByIdtipo(Integer idtipo) {
        
        return tipoRepository.findById(idtipo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo getTipoByNombre(String nombre) {
        
        return tipoRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistTiByNombre(String nombre) {
        
        return tipoRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tipo> getTiposByIdcategoria(Integer idcategoria) {
        
        return tipoRepository.listByIdcategoria(idcategoria);
    }    
    
}
