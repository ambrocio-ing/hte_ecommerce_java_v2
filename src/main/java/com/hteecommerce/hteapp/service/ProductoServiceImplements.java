package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.ProductoImagen;
import com.hteecommerce.hteapp.repository.IProductoImagenRepository;
import com.hteecommerce.hteapp.repository.IProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImplements implements IProductoService {   

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IProductoImagenRepository productoImagenRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> getAll(Pageable pageable) {
        
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> getAllNotIngresados(Pageable pageable) {
        
        return productoRepository.listAllNotIngresados(pageable);
    }   

    @Override
    @Transactional
    public Producto savePto(Producto producto) {       
       
        return productoRepository.save(producto);        
    }

    @Override
    @Transactional
    public void updatePto(Producto producto) {
        
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deletePto(Integer idproducto) {
        
        productoRepository.deleteById(idproducto);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getByIdproducto(Integer Idproducto) {
        
        return productoRepository.findById(Idproducto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByNombre(String nombre) {
        
        return productoRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByCodigo(String codigo) {
        
        return productoRepository.existsByCodigo(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getByCodigoOrNombre(String codigoOrNombre) {
        
        return productoRepository.listByCodigoOrNombre(codigoOrNombre, codigoOrNombre);
    }

    @Override
    @Transactional
    public void savePI(ProductoImagen pi) {
        
        productoImagenRepository.save(pi);
    }

    @Override
    @Transactional
    public void saveAllPI(List<ProductoImagen> pis) {
        
        productoImagenRepository.saveAll(pis);        
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoImagen getByIdimagen(Integer idimagen) {
        
        return productoImagenRepository.findById(idimagen).orElse(null);
    }

    @Override
    @Transactional
    public void deleteImg(Integer idimagen) {
        
        productoImagenRepository.deleteById(idimagen);
    }    
    
}
