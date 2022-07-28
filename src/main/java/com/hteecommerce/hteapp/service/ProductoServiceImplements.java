package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;
import com.hteecommerce.hteapp.entity.ProductoImagen;
import com.hteecommerce.hteapp.entity.ProductoOtros;
import com.hteecommerce.hteapp.entity.ProductoVestimenta;
import com.hteecommerce.hteapp.repository.IProductoDatoNutricionalRepository;
import com.hteecommerce.hteapp.repository.IProductoImagenRepository;
import com.hteecommerce.hteapp.repository.IProductoOtrosRepository;
import com.hteecommerce.hteapp.repository.IProductoRepository;
import com.hteecommerce.hteapp.repository.IProductoVestimentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImplements implements IProductoService {

    @Autowired
    private IProductoDatoNutricionalRepository productoDatoNutricionalRepository;

    @Autowired
    private IProductoVestimentaRepository productoVestimentaRepository;

    @Autowired
    private IProductoOtrosRepository productoOtrosRepository;

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
    @Transactional
    public Producto savePto(Producto producto) {
        
        if(producto.getProductoDatoNutricional() != null){
            ProductoDatoNutricional pdn = productoDatoNutricionalRepository.save(producto.getProductoDatoNutricional());
            producto.setProductoDatoNutricional(pdn);
        }

        if(producto.getProductoVestimenta() != null){
            ProductoVestimenta pv = productoVestimentaRepository.save(producto.getProductoVestimenta());
            producto.setProductoVestimenta(pv);
        }

        if(producto.getProductoOtros() != null){
            ProductoOtros po = productoOtrosRepository.save(producto.getProductoOtros());
            producto.setProductoOtros(po);
        }

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
    
}
