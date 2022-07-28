package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ClienteCaracteristica;
import com.hteecommerce.hteapp.repository.IClienteCaracteristicaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteCaracteristicaServiceImplements implements IClienteCaracteristicaService {

    @Autowired
    private IClienteCaracteristicaRepository clienteCaracteristicaRepository;

    @Override    
    public ClienteCaracteristica saveCC(ClienteCaracteristica clienteCaracteristica) {
        
        return clienteCaracteristicaRepository.save(clienteCaracteristica);
    }
    
}
