package com.hteecommerce.hteapp.model;

import java.util.List;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.entity.Variedad;

public class MCarrito {

    private Integer idcarrito;  
    
    private List<Variedad> variedades;
   
    private Integer cantidad;

    private Double descuento;    
   
    private Double subTotal;    
    
    private Integer idcliente;
    
    private MDetalleIngreso detalleIngreso;

    public MCarrito() {

    }

    public MCarrito(Carrito carrito) {
        this.idcarrito = carrito.getIdcarrito();
        this.variedades = carrito.getVariedades();
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
            carrito.getDetalleIngreso().getSucursal(),
            carrito.getDetalleIngreso().getProducto());
    }

    public Integer getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Integer idcarrito) {
        this.idcarrito = idcarrito;
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

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }
    
}
