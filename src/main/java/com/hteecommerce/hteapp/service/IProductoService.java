package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.ProductoImagen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoService {
    
    public Page<Producto> getAll(Pageable pageable);
    public Producto savePto(Producto producto);
    public void updatePto(Producto producto);
    public void deletePto(Integer idproducto);
    public Producto getByIdproducto(Integer Idproducto);

    public boolean isExistsByNombre(String nombre);
    public boolean isExistsByCodigo(String codigo);

    public List<Producto> getByCodigoOrNombre(String codigoOrNombre);

    //imagen
    public void savePI(ProductoImagen pi);
    public void saveAllPI(List<ProductoImagen> pis);
}
