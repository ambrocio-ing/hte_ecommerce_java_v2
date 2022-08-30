package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComprobanteService {    
    
    public boolean isExistsByNumero(String numero);
    public Comprobante saveCOM(Comprobante comprobante);
    public void deleteCOM(Integer idcom, List<DetalleIngreso> dis);

    public Comprobante getByIdcomprobante(Integer idcomprobante);
    public String getMaxId();

    //busquedas
    public List<Comprobante> getByFechaPedido(LocalDate fecha);
    public List<Comprobante> getByFechaByEstadoEntregado(LocalDate fecha);
    public List<Comprobante> getByClienteByDniOrNombre(String dniOrNombre);
    public List<Comprobante> getByClienteByIdcliente(Integer idcliente);

    //lista paginada
    public Page<Comprobante> getByEstadoEntregado(Pageable pageable);
    public Page<Comprobante> getByEstadoPedido(Pageable pageable);
    public Page<Comprobante> getByEstadoAnulado(Pageable pageable);

    public Comprobante getByNumero(String numero);

    //lista para resumen
    public List<Comprobante> getByEstado(String estado);

    //detalle comprobante
    public DetalleComprobante updateDC(DetalleComprobante dc);
    public void deleteDC(Integer iddc, DetalleIngreso di);    
    public DetalleComprobante getDCByIddetallecomprobante(Integer iddc);    

}
