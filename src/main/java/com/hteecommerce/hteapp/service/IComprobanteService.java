package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComprobanteService {    
    
    //OTRAS TAREAS
    public boolean isExistsByNumero(String numero);
    public Comprobante saveCOM(Comprobante comprobante);
    public void deleteCOM(Integer idcom, List<DetalleIngreso> dis);
    public Comprobante getByIdcomprobante(Integer idcomprobante);
    public String getMaxId();
    public Comprobante getByNumero(String numero);

    //busquedas
    public List<Comprobante> getByFechaByEstadoPedido(LocalDate fecha, String sucursal);
    public List<Comprobante> getByFechaByEstadoEntregado(LocalDate fecha, String sucursal);

    public List<Comprobante> getByClienteByDniOrNombre(String dniOrNombre);
    public List<Comprobante> getByClienteByIdcliente(Integer idcliente);    

    //lista paginada para sucursal huacho
    public Page<Comprobante> getByEstadoEntregadoHuacho(Pageable pageable);
    public Page<Comprobante> getByEstadoPedidoHuacho(Pageable pageable);
    public Page<Comprobante> getByEstadoAnuladoHuacho(Pageable pageable);

    //lista paginada para sucursal barranca
    public Page<Comprobante> getByEstadoEntregadoBarranca(Pageable pageable);
    public Page<Comprobante> getByEstadoPedidoBarranca(Pageable pageable);
    public Page<Comprobante> getByEstadoAnuladoBarranca(Pageable pageable);

    //lista para resumen de productos vendidos
    public List<Comprobante> getByEntregaPendienteSucursal(String sucursal);

    //buscar productos con estado en validacion pendiente por fecha y sucursal
    public List<Comprobante> getByFechaByEstadoPedidoValidar(LocalDate fecha, String sucursal);

    //lista paginada de ventas por validar en sucursal huacho
    public Page<Comprobante> getByEstadoPedidoValidarHuacho(Pageable pageable);

    //lista paginada de ventas por validar en sucursal huacho
    public Page<Comprobante> getByEstadoPedidoValidarBarranca(Pageable pageable);

    //detalle comprobante
    public DetalleComprobante updateDC(DetalleComprobante dc);
    public void deleteDC(Integer iddc, DetalleIngreso di);    
    public DetalleComprobante getDCByIddetallecomprobante(Integer iddc);   
    
}
