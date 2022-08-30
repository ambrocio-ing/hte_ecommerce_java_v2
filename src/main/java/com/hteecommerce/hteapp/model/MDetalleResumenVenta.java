package com.hteecommerce.hteapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.Variedad;

public class MDetalleResumenVenta {

    private String dniCliente;
    private String nombreCliente;
    private String numeroComprobante;
    private Integer comprobanteId;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEntrega;
    private Double cantidadProducto;
    private List<Variedad> variedades;

    public MDetalleResumenVenta() {

    }      

    public MDetalleResumenVenta(String dniCliente, String nombreCliente, String numeroComprobante,
            LocalDateTime fechaPedido, LocalDateTime fechaEntrega, DetalleComprobante dc) {
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.numeroComprobante = numeroComprobante;
        this.comprobanteId = dc.getComprobanteId();
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.cantidadProducto = dc.getCantidad().doubleValue();
        this.variedades = dc.getVariedades();
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Double cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }    

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public Integer getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Integer comprobanteId) {
        this.comprobanteId = comprobanteId;
    }    

}
