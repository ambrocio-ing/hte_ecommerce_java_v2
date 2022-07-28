package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.ClienteProveedor;

public interface IClienteProveedorService {
    
    public ClienteProveedor saveCP(ClienteProveedor clienteProveedor);
    public boolean isExistsRuc(String ruc);
    public boolean isExistsRazonSocial(String razonSocial);

    public boolean isExistsRucAndIdcp(String ruc, Integer idcp);
    public boolean isExistsRazonSocialAndIdcp(String razonSocial, Integer idcp);
}
