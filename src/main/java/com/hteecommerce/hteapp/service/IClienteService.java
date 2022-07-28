package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.ClienteProveedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {
    
    public Page<Cliente> getAll(Pageable pageable);
    public List<Cliente> getByNombreOrDni(String nombreOrDni);

    public Cliente saveCli(Cliente cliente);
    public void updateCli(Cliente cliente);    
    public void deleteCli(Integer idcliente);
    public Cliente getByIdcliente(Integer idcliente);

    public Cliente getByUsername(String username);

    public ClienteProveedor getCPByIdcp(Integer idcp);

}
