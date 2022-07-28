package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ProveedorComprobante;
import com.hteecommerce.hteapp.entity.ProveedorOferta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProveedorComprobanteService {

    public List<ProveedorComprobante> getByFechas(LocalDate finicio, LocalDate ffin);
    
    public Page<ProveedorComprobante> getAll(Pageable pageable);
    public Page<ProveedorComprobante> getByEstadoOfertado(Pageable pageable);
    public Page<ProveedorComprobante> getByEstadoRechazado(Pageable pageable);
    public Page<ProveedorComprobante> getByEstadoAceptado(Pageable pageable);

    public void savePC(ProveedorComprobante proveedorComprobante);
    public void deletePC(Integer idpc);
    public ProveedorComprobante getByidproveedorcomprobante(Integer idpc);

    public void deletePO(Integer idpo);
    public ProveedorOferta getPOByIdproveedoroferta(Integer idpo);
}
