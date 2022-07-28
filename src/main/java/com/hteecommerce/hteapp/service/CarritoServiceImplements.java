package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.repository.ICarritoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoServiceImplements implements ICarritoService {

    @Autowired
    private ICarritoRepository carritoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Carrito> getByCliente(Integer idcliente) {
        
        return carritoRepository.findByIdcliente(idcliente);
    }

    @Override
    @Transactional
    public Carrito saveC(Carrito carrito) {
        
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void deleteC(Integer idcarrito) {
        
        carritoRepository.deleteById(idcarrito);
    }

    @Override
    @Transactional(readOnly = true)
    public Carrito getByIdcarrito(Integer idcarrito) {
        
        return carritoRepository.findById(idcarrito).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Carrito getByIdddiAndIdcliente(Integer iddi, Integer idcliente) {
        
        return carritoRepository.findByIddetalleingresoAndIdcliente(iddi, idcliente).orElse(null);
    }

    @Override
    @Transactional
    public void saveAllC(List<Carrito> carritos) {
        
        carritoRepository.saveAll(carritos);
        
    }
    
}
