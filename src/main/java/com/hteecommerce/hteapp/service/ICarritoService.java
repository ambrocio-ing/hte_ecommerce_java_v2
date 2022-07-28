package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Carrito;

public interface ICarritoService {
    
    public List<Carrito> getByCliente(Integer idcliente);
    public Carrito saveC(Carrito carrito);
    public void saveAllC(List<Carrito> carritos);
    public void deleteC(Integer idcarrito);
    public Carrito getByIdcarrito(Integer idcarrito);

    public Carrito getByIdddiAndIdcliente(Integer iddi, Integer idcliente);
}
