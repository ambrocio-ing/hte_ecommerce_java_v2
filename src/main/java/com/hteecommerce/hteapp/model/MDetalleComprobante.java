package com.hteecommerce.hteapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.Variedad;

public class MDetalleComprobante {

    private Integer iddetallecomprobante;

    private Double cantidad;

    private Double descuento;

    private Double precioUnitario;

    private Double subTotal;

    private MDetalleIngreso detalleIngreso;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<Variedad> variedades;

    private Integer comprobanteId;

    public MDetalleComprobante() {

    }

    public MDetalleComprobante(DetalleComprobante dc) {
        this.iddetallecomprobante = dc.getIddetallecomprobante();
        this.cantidad = dc.getCantidad();
        this.descuento = dc.getDescuento();
        this.precioUnitario = dc.getPrecioUnitario();
        this.subTotal = dc.getSubTotal();
        this.detalleIngreso = new MDetalleIngreso(dc.getDetalleIngreso().getIddetalleingreso(),
                dc.getDetalleIngreso().getPrecioVenta(), dc.getDetalleIngreso().getSucursal(),
                dc.getDetalleIngreso().getProducto());

        this.variedades = dc.getVariedades();
        this.comprobanteId = dc.getComprobanteId();
    }        

    public Integer getIddetallecomprobante() {
        return iddetallecomprobante;
    }

    public void setIddetallecomprobante(Integer iddetallecomprobante) {
        this.iddetallecomprobante = iddetallecomprobante;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public MDetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(MDetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }

    public Integer getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Integer comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}
