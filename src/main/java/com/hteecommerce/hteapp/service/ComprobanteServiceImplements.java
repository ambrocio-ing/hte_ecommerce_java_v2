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

    //OTRAS TAREAS
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
    public void deleteCOM(Integer idcom, List<DetalleIngreso> dis) {
        
        detalleIngresoRepository.saveAll(dis);
        comprobanteRepository.deleteById(idcom);
        
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
    public Comprobante getByNumero(String numero) {
        
        return comprobanteRepository.findByNumero(numero).orElse(null);
    }

    //BUSQUEDAS
    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaByEstadoPedido(LocalDate fecha, String sucursal) {
        
        return comprobanteRepository.listByFechaByEstadoPedido(fecha, sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaByEstadoEntregado(LocalDate fecha, String sucursal) {
        
        return comprobanteRepository.listByFechaByEstadoEntregado(fecha, sucursal);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByClienteByDniOrNombre(String dniOrNombre) {
        
        return comprobanteRepository.listByClienteByDniOrNombre(dniOrNombre, dniOrNombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByClienteByIdcliente(Integer idcliente) {
        
        return comprobanteRepository.listByClienteByIdcliente(idcliente);
    }

    //lista paginada para sucursal huacho
    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoEntregadoHuacho(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoEntregadoHuacho(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoPedidoHuacho(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoPedidoHuacho(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoAnuladoHuacho(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoAnuladoHuacho(pageable);
    }

    //lista paginada para sucursal barranca
    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoEntregadoBarranca(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoEntregadoBarranca(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoPedidoBarranca(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoPedidoBarranca(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoAnuladoBarranca(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoAnuladoBarranca(pageable);
    }

    //lista para resumen de productos vendidos
    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByEntregaPendienteSucursal(String sucursal) {
        
        return comprobanteRepository.listarPorEntregaPendienteSucursal(sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaEntregaByEstadoPedido(LocalDate fecha, String sucursal) {
        
        return comprobanteRepository.listByFechaEntregaByEstadoPedido(fecha, sucursal);
    }

    //buscar productos con estado en validacion pendiente por fecha y sucursal
    @Override
    @Transactional(readOnly = true)
    public List<Comprobante> getByFechaByEstadoPedidoValidar(LocalDate fecha, String sucursal) {
        
        return comprobanteRepository.listByFechaByEstadoPedidoValidar(fecha, sucursal);
    }

    //lista paginada de ventas por validar en sucursal huacho
    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoPedidoValidarHuacho(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoPedidoValidarHuacho(pageable);
    }  

    //lista paginada de ventas por validar en sucursal Barranca
    @Override
    @Transactional(readOnly = true)
    public Page<Comprobante> getByEstadoPedidoValidarBarranca(Pageable pageable) {
        
        return comprobanteRepository.listByEstadoPedidoValidarBarranca(pageable);
    }  


    //Detalle comprobante
    @Override
    @Transactional(readOnly = true)
    public DetalleComprobante getDCByIddetallecomprobante(Integer iddc) {
        
        return detalleComprobanteRepository.findById(iddc).orElse(null);
    }
    
    @Override
    @Transactional
    public DetalleComprobante updateDC(DetalleComprobante dc) {
        
        return detalleComprobanteRepository.save(dc);
    }  

    @Override
    @Transactional
    public void deleteDC(Integer iddc, DetalleIngreso di) {
       
        detalleComprobanteRepository.deleteById(iddc);
        detalleIngresoRepository.save(di);        
    }    
    
}
