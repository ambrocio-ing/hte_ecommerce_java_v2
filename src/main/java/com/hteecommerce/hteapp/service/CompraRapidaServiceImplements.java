package com.hteecommerce.hteapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hteecommerce.hteapp.entity.CompraRapida;
import com.hteecommerce.hteapp.repository.ICompraRapidaRepository;

@Service
public class CompraRapidaServiceImplements implements ICompraRapidaService {
    
    @Autowired
    private ICompraRapidaRepository compraRapidaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompraRapida> getAll() {
        
        return compraRapidaRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCR(CompraRapida compraRapida) {
        
        compraRapidaRepository.save(compraRapida);
        
    }

    @Override
    @Transactional
    public void deleteCR(Integer idcompra) {
        
        compraRapidaRepository.deleteById(idcompra);
        
    }

    @Override
    @Transactional(readOnly = true)
    public CompraRapida getByIdcompra(Integer idcompra) {
        
        return compraRapidaRepository.findById(idcompra).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraRapida> getByIdcliente(Integer idcliente) {
        
        return compraRapidaRepository.findByIdcliente(idcliente);
    }

    @Override
    @Transactional(readOnly = true)
    public CompraRapida getByIddetalleingreso(Integer idcliente, Integer iddetalleingreso) {
        
        return compraRapidaRepository.getByIddetalleingreso(idcliente, iddetalleingreso).orElse(null);
    }

}
