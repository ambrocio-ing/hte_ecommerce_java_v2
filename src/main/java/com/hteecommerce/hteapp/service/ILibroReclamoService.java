package com.hteecommerce.hteapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hteecommerce.hteapp.entity.LibroReclamo;
import com.hteecommerce.hteapp.entity.Sujerencia;

public interface ILibroReclamoService {
    
    public Page<LibroReclamo> getAll(Pageable pageable);
    public void saveLR(LibroReclamo libroReclamo);
    public void deleteLR(Integer idlibro);
    public LibroReclamo getByIdlibro(Integer idlibro);
    public String getLatestNumero();

    //metodos para sujerencia
    public Page<Sujerencia> getAllSujerencia(Pageable pageable);
    public void saveSU(Sujerencia sujerencia);
    public void deleteSU(Integer idsujerencia);
    public Sujerencia getByIdsujerencia(Integer idsujerencia);
}
