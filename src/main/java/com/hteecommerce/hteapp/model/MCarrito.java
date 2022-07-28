package com.hteecommerce.hteapp.model;

import com.hteecommerce.hteapp.entity.Carrito;

public class MCarrito {

    private Integer idcarrito;  
    
    private String variedad;
   
    private Integer cantidad;

    private Double descuento;    
   
    private Double subTotal;    
    
    private Integer idcliente;
    
    private MDetalleIngreso detalleIngreso;

    public MCarrito() {

    }

    public MCarrito(Carrito carrito) {
        this.idcarrito = carrito.getIdcarrito();
        this.variedad = carrito.getVariedad();
        this.cantidad = carrito.getCantidad();
        this.descuento = carrito.getDescuento();
        this.subTotal = carrito.getSubTotal();
        this.idcliente = carrito.getIdcliente();
        this.detalleIngreso = new MDetalleIngreso(
            carrito.getDetalleIngreso().getIddetalleingreso(),
            carrito.getDetalleIngreso().getPrecioVenta(),
            carrito.getDetalleIngreso().getPrecioVentaAnterior(), 
            carrito.getDetalleIngreso().getPorcentajeDescuento(),
            carrito.getDetalleIngreso().getStockActual(),
            carrito.getDetalleIngreso().getFechaProduccion(),
            carrito.getDetalleIngreso().getFechaVencimiento(),
            carrito.getDetalleIngreso().getEstado(),
            carrito.getDetalleIngreso().getProducto());
    }

    public Integer getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Integer idcarrito) {
        this.idcarrito = idcarrito;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
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

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public MDetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(MDetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }
    
}
