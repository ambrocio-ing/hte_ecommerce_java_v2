package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.repository.IComprobanteRepository;
import com.hteecommerce.hteapp.repository.IDetalleComprobanteRepository;
import com.hteecommerce.hteapp.repository.IDetalleIngresoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComprobanteServiceImplements implements IComprobanteService {

    @Autowired
    private IComprobanteRepository comprobanteRepository;

    @Autowired
    private IDetalleComprobanteRepository detalleComprobanteRepository;

    @Autowired
    private IDetalleIngresoRepository detalleIngresoRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByNumero(String numero) {
        
        return comprobanteRepository.existsByNumero(numero);
    }

    @Override
    @Transactional
    public Comprobante saveCOM(Comprobante comprobante) {
        
        return comprobanteRepository.save(comprobante);
    }

    @Override
    @Transactional
    public DetalleComprobante updateDC(DetalleComprobante dc) {
        
        return detalleComprobanteRepository.save(dc);
    }  

    @Override
    @Transactional
    public void deleteCOM(Integer idcom, List<DetalleIngreso> dis) {
        
        detalleIngresoRepository.saveAll(dis);
        comprobanteRepository.deleteById(idcom);
        
    }

    @Override
    @Transactional
    public void deleteDC(Integer iddc, DetalleIngreso di) {
       
        detalleComprobanteRepository.deleteById(iddc);
        detalleIngresoRepository.save(di);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Comprobante getByIdcomprobante(Integer idcomprobante) {
        
        return comprobanteRepository.findById(idcomprobante).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public String getMaxId() {
        
        Comprobante com = comprobanteRepository.findTopByOrderByIdcomprobanteDesc().orElse(null);
        if(com != null){
            return com.getNumero();
        }
        else{
            return null;
        }       

    }      

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaPedido(LocalDate fecha) {
        
        return comprobanteRepository.listByFechaByEstadoPedido(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaByEstadoEntregado(LocalDate fecha) {
        
        return comprobanteRepository.listByFechaByEstadoEntregado(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByClienteByDniOrNombre(String dniOrNombre) {
        
        return comprobanteRepository.listByClienteByDniOrNombre(dniOrNombre, dniOrNombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoEntregado(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoEntregado(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoPedido(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoPedido(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoAnulado(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoAnulado(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Comprobante getByNumero(String numero) {
        
        return comprobanteRepository.findByNumero(numero).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleComprobante getDCByIddetallecomprobante(Integer iddc) {
        
        return detalleComprobanteRepository.findById(iddc).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByClienteByIdcliente(Integer idcliente) {
        
        return comprobanteRepository.listByClienteByIdcliente(idcliente);
    }     
    
}
