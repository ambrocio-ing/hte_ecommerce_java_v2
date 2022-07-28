package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "carritos")
public class Carrito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcarrito;  
    
    private String variedad;

    @NotNull
    private Integer cantidad;

    private Double descuento;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;
    
    @NotNull
    private Integer idcliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddetalleingreso", nullable = false)
    private DetalleIngreso detalleIngreso;

    public Carrito(){
        
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

    public DetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(DetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }    

    private static final long serialVersionUID = 1L;
}
