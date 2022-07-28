package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Categoria;
import com.hteecommerce.hteapp.entity.Tipo;

public interface ICategoriaTipoService {
    
    public List<Categoria> getCategorias();
    public void saveCAT(Categoria categoria);
    public void deleteCAT(Integer idcategoria);
    public Categoria getCategoriaByIdcategoria(Integer idcategoria);
    public Categoria getCategoriaByNombre(String nombre);
    public boolean isExistTiByNombre(String nombre);

    public List<Tipo> getTipos();
    public void saveTI(Tipo tipo);
    public void deleteTI(Integer idtipo);
    public Tipo getTipoByIdtipo(Integer idtipo);
    public Tipo getTipoByNombre(String nombre);
    public boolean isExistCatByNombre(String nombre);

    public List<Tipo> getTiposByIdcategoria(Integer idcategoria);
}
