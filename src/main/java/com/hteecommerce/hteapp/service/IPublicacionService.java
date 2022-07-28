package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Publicacion;

public interface IPublicacionService {
    
    public List<Publicacion> getAll();
    public List<Publicacion> getByEstado(String estado);
    public Publicacion saveP(Publicacion publicacion);
    public void deleteP(Integer idpublicacion);
    public Publicacion getByIdpublicacion(Integer idpublicacion);
}
