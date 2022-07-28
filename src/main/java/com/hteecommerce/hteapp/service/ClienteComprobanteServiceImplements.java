package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ClienteComprobante;
import com.hteecommerce.hteapp.entity.ClienteOferta;
import com.hteecommerce.hteapp.repository.IClienteComprobanteRepository;
import com.hteecommerce.hteapp.repository.IClienteOfertaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteComprobanteServiceImplements implements IClienteComprobanteService {

    @Autowired
    private IClienteComprobanteRepository clienteComprobanteRepository;

    @Autowired
    private IClienteOfertaRepository clienteOfertaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteComprobante> getByFechas(LocalDate finicio, LocalDate ffin) {
        
        return clienteComprobanteRepository.listByFechas(finicio, ffin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteComprobante> getByEstado(String estado, Pageable pageable) {
        
        return clienteComprobanteRepository.listByEstado(estado, pageable);
    }

    @Override
    @Transactional
    public void saveCLICOM(ClienteComprobante clienteComprobante) {
        
        clienteComprobanteRepository.save(clienteComprobante);
        
    }

    @Override
    @Transactional
    public void deleteCLICOM(Integer idcc) {
        
        clienteComprobanteRepository.deleteById(idcc);
        
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteComprobante getByIdclientecomprobante(Integer idcc) {
        
        return clienteComprobanteRepository.findById(idcc).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteOferta getByIdclienteoferta(Integer idco) {
        
        return clienteOfertaRepository.findById(idco).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCO(Integer idco) {
        
        clienteOfertaRepository.deleteById(idco);
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteComprobante> getByClientes(Integer idcp) {
        
        return clienteComprobanteRepository.listByCliente(idcp);
    }
    
}
