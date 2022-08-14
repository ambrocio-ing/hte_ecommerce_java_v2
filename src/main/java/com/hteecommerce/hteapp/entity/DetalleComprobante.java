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
import com.hteecommerce.hteapp.exception.InsufficientStockError;

@Entity
@Table(name = "detalle_comprobantes")
public class DetalleComprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddetallecomprobante;

    private String variedad;

    @NotNull
    private Integer cantidad;

    private Double descuento;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddetalleingreso", nullable = false)
    private DetalleIngreso detalleIngreso;

    private Integer idcomprobante;

    public DetalleComprobante() {

    }    

    public Integer decreaseStockActual() throws InsufficientStockError {

        if(this.detalleIngreso.getStockActual() == 0 || !this.detalleIngreso.getEstado()){
            throw new InsufficientStockError(
                    "Stock del producto: " + this.detalleIngreso.getProducto().getNombre() + " insuficiente o vigencia caducada");
        }

        if (this.detalleIngreso.getStockActual() >= this.cantidad) {
            int stock = this.detalleIngreso.getStockActual() - this.cantidad;
            this.detalleIngreso.setStockActual(stock);
            return this.detalleIngreso.getStockActual();
        } 
        else {
            throw new InsufficientStockError(
                    "Stock del producto: " + this.detalleIngreso.getProducto().getNombre() + " insuficiente");
        }

    }

    public Integer replenishStockActual() {
        int stock = this.detalleIngreso.getStockActual() + this.cantidad;
        this.detalleIngreso.setStockActual(stock);
        return this.detalleIngreso.getStockActual();
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

    public DetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(DetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getIdcomprobante() {
        return idcomprobante;
    }

    public void setIdcomprobante(Integer idcomprobante) {
        this.idcomprobante = idcomprobante;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    private static final long serialVersionUID = 1L;
}
