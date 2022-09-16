package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.entity.Variedad;
import com.hteecommerce.hteapp.mapper.Mapper;

public class MCarrito {

    private Integer idcarrito;     
       
    private Double cantidad;

    private Double descuento;    
   
    private Double subTotal;       
    
    private Integer idcliente;

    private LocalDate createAt;
    
    private MDetalleIngreso detalleIngreso;

    private List<Variedad> variedades;

    public MCarrito() {

    }

    public MCarrito(Carrito carrito) {
        this.idcarrito = carrito.getIdcarrito();        
        this.cantidad = carrito.getCantidad();
        this.descuento = carrito.getDescuento();
        this.subTotal = carrito.getSubTotal();
        this.idcliente = carrito.getIdcliente();
        this.createAt = carrito.getCreateAt();
        this.detalleIngreso = Mapper.mapDetalleIngreso(carrito.getDetalleIngreso());
        this.variedades = carrito.getVariedades();
    }

    public Integer getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Integer idcarrito) {
        this.idcarrito = idcarrito;
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

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
    
}
