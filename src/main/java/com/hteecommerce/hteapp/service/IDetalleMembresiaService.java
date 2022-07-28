package com.hteecommerce.hteapp.service;

import java.time.LocalDate;

import com.hteecommerce.hteapp.entity.DetalleMembresia;

public interface IDetalleMembresiaService {
    
    public DetalleMembresia getByIdcliente(Integer idcliente, LocalDate fecha);
    public DetalleMembresia saveDM(DetalleMembresia detalleMembresia);
    public void deleteDM(Integer iddm);
    public DetalleMembresia getByIddetallemembresia(Integer iddm);
}
