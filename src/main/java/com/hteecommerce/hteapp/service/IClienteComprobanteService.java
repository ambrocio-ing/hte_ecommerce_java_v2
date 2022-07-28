package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ClienteComprobante;
import com.hteecommerce.hteapp.entity.ClienteOferta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteComprobanteService {
    
    public List<ClienteComprobante> getByClientes(Integer idcp);
    public List<ClienteComprobante> getByFechas(LocalDate finicio, LocalDate ffin);
    public Page<ClienteComprobante> getByEstado(String estado, Pageable pageable);
    public void saveCLICOM(ClienteComprobante clienteComprobante);
    public void deleteCLICOM(Integer idcc);
    public ClienteComprobante getByIdclientecomprobante(Integer idcc);

    //cliente oferta
    public ClienteOferta getByIdclienteoferta(Integer idco);
    public void deleteCO(Integer idco);
}
