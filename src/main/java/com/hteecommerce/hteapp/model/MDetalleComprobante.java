package com.hteecommerce.hteapp.model;

import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;

public class MDetalleComprobante {

    private Integer iddetallecomprobante;

    private String variedad;

    private Integer cantidad;

    private Double descuento;

    private Double subTotal;

    private MDetalleIngreso detalleIngreso;

    private Integer comprobanteId;

    public MDetalleComprobante() {

    }

    public MDetalleComprobante(DetalleComprobante dc) {
        this.iddetallecomprobante = dc.getIddetallecomprobante();
        this.variedad = dc.getVariedad();
        this.cantidad = dc.getCantidad();
        this.descuento = dc.getDescuento();
        this.subTotal = dc.getSubTotal();
        this.detalleIngreso = new MDetalleIngreso(
                dc.getDetalleIngreso().getIddetalleingreso(), dc.getDetalleIngreso().getPrecioVenta(),
                dc.getDetalleIngreso().getPrecioVentaAnterior(), dc.getDetalleIngreso().getPorcentajeDescuento(),
                dc.getDetalleIngreso().getStockInicial(), dc.getDetalleIngreso().getStockActual(),
                dc.getDetalleIngreso().getFechaProduccion(), dc.getDetalleIngreso().getFechaVencimiento(),
                dc.getDetalleIngreso().getEstado(), dc.getDetalleIngreso().getProducto(),
                dc.getDetalleIngreso().getIngresoId());
        this.comprobanteId = dc.getComprobanteId();
    }

    public MDetalleComprobante(Integer iddetallecomprobante, String variedad, Integer cantidad, Double descuento,
            Double subTotal,
            DetalleIngreso detalleIngreso, Integer comprobanteId) {
        this.iddetallecomprobante = iddetallecomprobante;
        this.variedad = variedad;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.detalleIngreso = new MDetalleIngreso(detalleIngreso.getIddetalleingreso(), detalleIngreso.getProducto(),
                detalleIngreso.getIngresoId());
        this.comprobanteId = comprobanteId;
    }

    public Integer getIddetallecomprobante() {
        return iddetallecomprobante;
    }

    public void setIddetallecomprobante(Integer iddetallecomprobante) {
        this.iddetallecomprobante = iddetallecomprobante;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
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

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public Integer getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Integer comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

}
