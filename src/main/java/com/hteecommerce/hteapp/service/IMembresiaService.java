package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Membresia;

public interface IMembresiaService {
    
    public List<Membresia> getAll();
    public List<Membresia> getByEstado(String estado);

    public Membresia saveM(Membresia membresia);
    public void deleteM(Integer idmembresia);
    public Membresia getByIdmembresia(Integer idmembresia);
}
