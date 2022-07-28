package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Reclamo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReclamoService {
    
    public List<Reclamo> getByCliente(Integer idcliente);
    public List<Reclamo> getByProveedor(Integer idproveedor);
    public List<Reclamo> getByFecha(LocalDate fecha);
    public Page<Reclamo> getAll(Pageable pageable);

    public void saveR(Reclamo reclamo);
    public void deleteR(Integer idreclamo);
    public Reclamo getByIdreclamo(Integer idreclamo);
}
