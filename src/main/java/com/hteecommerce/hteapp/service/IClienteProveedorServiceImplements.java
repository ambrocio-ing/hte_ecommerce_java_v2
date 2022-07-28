package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ClienteProveedor;
import com.hteecommerce.hteapp.repository.IClienteProveedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IClienteProveedorServiceImplements implements IClienteProveedorService {

    @Autowired
    private IClienteProveedorRepository clienteProveedorRepository;

    @Override  
    @Transactional  
    public ClienteProveedor saveCP(ClienteProveedor clienteProveedor) {
        
        return clienteProveedorRepository.save(clienteProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsRuc(String ruc) {
        
        return clienteProveedorRepository.existsByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsRazonSocial(String razonSocial) {
        
        return clienteProveedorRepository.existsByRazonSocial(razonSocial);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsRucAndIdcp(String ruc, Integer idcp) {
        
        ClienteProveedor cp = clienteProveedorRepository.validRuc(ruc, idcp).orElse(null);
        if(cp != null){
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsRazonSocialAndIdcp(String razonSocial, Integer idcp) {
        
        ClienteProveedor cp = clienteProveedorRepository.validRuc(razonSocial, idcp).orElse(null);
        if(cp != null){
            return true;
        }
        return false;
    }


    
}
