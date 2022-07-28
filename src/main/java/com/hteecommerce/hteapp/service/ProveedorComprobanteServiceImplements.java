package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ProveedorComprobante;
import com.hteecommerce.hteapp.entity.ProveedorOferta;
import com.hteecommerce.hteapp.repository.IProveedorComprobanteRepository;
import com.hteecommerce.hteapp.repository.IProveedorOfertaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorComprobanteServiceImplements implements IProveedorComprobanteService {

    @Autowired
    private IProveedorComprobanteRepository proveedorComprobanteRepository;

    @Autowired
    private IProveedorOfertaRepository proveedorOfertaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorComprobante> getByEstadoOfertado(Pageable pageable) {
        
        return proveedorComprobanteRepository.listByEstadoOfertado(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorComprobante> getByEstadoRechazado(Pageable pageable) {
        
        return proveedorComprobanteRepository.listByEstadoRechazado(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorComprobante> getByEstadoAceptado(Pageable pageable) {
        
        return proveedorComprobanteRepository.listByEstadoAceptado(pageable);
    }

    @Override
    @Transactional
    public void savePC(ProveedorComprobante proveedorComprobante) {
        
        proveedorComprobanteRepository.save(proveedorComprobante);
    }

    @Override
    @Transactional
    public void deletePC(Integer idpc) {
        
        proveedorComprobanteRepository.deleteById(idpc);
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorComprobante getByidproveedorcomprobante(Integer idpc) {
        
        return proveedorComprobanteRepository.findById(idpc).orElse(null);
    }

    @Override
    @Transactional
    public void deletePO(Integer idpo) {
        
        proveedorOfertaRepository.deleteById(idpo);
        
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorOferta getPOByIdproveedoroferta(Integer idpo) {
        
        return proveedorOfertaRepository.findById(idpo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorComprobante> getByFechas(LocalDate finicio, LocalDate ffin) {
        
        return proveedorComprobanteRepository.listByFechas(finicio, ffin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorComprobante> getAll(Pageable pageable) {
        
        return proveedorComprobanteRepository.listAll(pageable);
    }
    
}
