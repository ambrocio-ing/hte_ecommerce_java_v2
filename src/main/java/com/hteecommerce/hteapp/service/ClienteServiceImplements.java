package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.ClienteProveedor;
//import com.hteecommerce.hteapp.repository.IClienteCaracteristicaRepository;
import com.hteecommerce.hteapp.repository.IClienteProveedorRepository;
import com.hteecommerce.hteapp.repository.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImplements implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

   /*  @Autowired
    private IClienteCaracteristicaRepository clienteCaracteristicaRepository; */

    @Autowired
    private IClienteProveedorRepository clienteProveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> getAll(Pageable pageable) {
        
        return clienteRepository.listClientes(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getByNombreOrDni(String nombreOrDni) {
        
        return clienteRepository.listByNombreOrDni(nombreOrDni, nombreOrDni);
    }

    @Override
    @Transactional
    public Cliente saveCli(Cliente cliente) {
       
        /* //guardar si es que viene cliente caracteristicas
        if(cliente.getClienteCaracteristica() != null){
            if(cliente.getClienteCaracteristica().getIdcaracteristica() == null){
                ClienteCaracteristica cc = clienteCaracteristicaRepository.save(cliente.getClienteCaracteristica());
                cliente.setClienteCaracteristica(cc);
            }            
        }

        //guardar si es que viene cliente proveedor
        if(cliente.getClienteProveedor() != null){
            if(cliente.getClienteProveedor().getIdcp() == null){
                ClienteProveedor cp = clienteProveedorRepository.save(cliente.getClienteProveedor());
                cliente.setClienteProveedor(cp);
            }            
        } */

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void updateCli(Cliente cliente) {
              
        clienteRepository.save(cliente);       
        
    } 

    @Override
    @Transactional
    public void deleteCli(Integer idcliente) {
        
        clienteRepository.deleteById(idcliente);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getByIdcliente(Integer idcliente) {
        
        return clienteRepository.findById(idcliente).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getByUsername(String username) {
        
        return clienteRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteProveedor getCPByIdcp(Integer idcp) {
        
        return clienteProveedorRepository.findById(idcp).orElse(null);
    }         
    
}
