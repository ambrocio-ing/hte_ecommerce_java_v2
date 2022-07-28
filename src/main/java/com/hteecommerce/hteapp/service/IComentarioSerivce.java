package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Comentario;

public interface IComentarioSerivce {
    
    public List<Comentario> getByIddetalleingreso(Integer iddi);
    public void saveCOM(Comentario comentario);
    public void deleteCOM(Integer idcomentario);
    public Comentario getByIdcomentario(Integer idcomentario);

    public List<Comentario> getByIdproducto(Integer idproducto);
}
