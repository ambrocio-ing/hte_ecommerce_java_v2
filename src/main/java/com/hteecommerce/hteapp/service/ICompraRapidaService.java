package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.CompraRapida;

public interface ICompraRapidaService {
    
    public List<CompraRapida> getAll();
    public void saveCR(CompraRapida compraRapida);
    public void deleteCR(Integer idcompra);
    public CompraRapida getByIdcompra(Integer idcompra);

    public List<CompraRapida> getByIdcliente(Integer idcliente);
    public CompraRapida getByIddetalleingreso(Integer idcliente, Integer iddetalleingreso);

}
