package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @NotNull
    private Double cantidad;

    private Double descuento;

    @NotNull
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalleingreso_id", nullable = false)
    private DetalleIngreso detalleIngreso;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detallecomprobante_id", nullable = true)
    private List<Variedad> variedades = null;

    @Column(name = "comprobante_id")
    private Integer comprobanteId;

    public DetalleComprobante() {

    }

    public Double decreaseStockActual() throws InsufficientStockError {

        if (this.detalleIngreso.getStockActual() == 0 || !this.detalleIngreso.getEstado()) {
            throw new InsufficientStockError(
                    "Stock del producto: " + this.detalleIngreso.getProducto().getNombre()
                            + " insuficiente o vigencia caducada");
        }

        if (this.detalleIngreso.getStockActual() >= this.cantidad) {
            double stock = this.detalleIngreso.getStockActual() - this.cantidad;
            this.detalleIngreso.setStockActual(stock);
            return this.detalleIngreso.getStockActual();
        } else {
            throw new InsufficientStockError(
                    "Stock del producto: " + this.detalleIngreso.getProducto().getNombre() + " insuficiente");
        }

    }

    public Double replenishStockActual() {
        double stock = this.detalleIngreso.getStockActual() + this.cantidad;
        this.detalleIngreso.setStockActual(stock);
        return this.detalleIngreso.getStockActual();
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

    private static final long serialVersionUID = 1L;
}
