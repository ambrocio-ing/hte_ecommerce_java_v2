package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Reclamo;
import com.hteecommerce.hteapp.repository.IReclamoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReclamoServiceImplements implements IReclamoService {

    @Autowired
    private IReclamoRepository reclamoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reclamo> getByCliente(Integer idcliente) {
        
        return reclamoRepository.listByIdcliente(idcliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reclamo> getByProveedor(Integer idproveedor) {
        
        return reclamoRepository.listByIdproveedor(idproveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reclamo> getByFecha(LocalDate fecha) {
        
        return reclamoRepository.findByFecha(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamo> getAll(Pageable pageable) {
        
        return reclamoRepository.paginateReclamos(pageable);
    }

    @Override
    @Transactional
    public void saveR(Reclamo reclamo) {
        
        reclamoRepository.save(reclamo);
    }

    @Override
    @Transactional
    public void deleteR(Integer idreclamo) {
        
        reclamoRepository.deleteById(idreclamo);
    }

    @Override
    @Transactional(readOnly = true)
    public Reclamo getByIdreclamo(Integer idreclamo) {
        
        return reclamoRepository.findById(idreclamo).orElse(null);
    }
    
}
