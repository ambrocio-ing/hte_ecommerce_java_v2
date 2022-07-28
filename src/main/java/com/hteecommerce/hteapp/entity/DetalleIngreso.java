package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalle_ingreso")
public class DetalleIngreso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Integer iddetalleingreso;
    
    @Column(name = "precio_compra")
    private Double precioCompra;

    @NotNull
    @Column(name = "precio_venta")
    private Double precioVenta;

    @NotNull
    @Column(name = "precio_venta_anterior")
    private Double precioVentaAnterior;

    @NotNull
    @Column(name = "porcentaje_descuento")
    private Double porcentajeDescuento;

    @NotNull
    @Column(name = "stock_inicial")
    private Integer stockInicial;

    @NotNull
    @Column(name = "stock_actual")
    private Integer stockActual;

    @NotNull
    @Column(name = "fecha_produccion")
    private LocalDate fechaProduccion;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @NotNull
    private Boolean estado;

    @Column(name = "create_at")
    private LocalDate createAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false)
    private Producto producto;

    private Integer idingreso;    

    public DetalleIngreso() {

    }

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDate.now();
    }
    
    public Integer calculateStockActual(Integer stock_actual){
        return this.stockInicial + stock_actual;
    }

    public Integer getIddetalleingreso() {
        return iddetalleingreso;
    }

    public void setIddetalleingreso(Integer iddetalleingreso) {
        this.iddetalleingreso = iddetalleingreso;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public LocalDate getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(LocalDate fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }    

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }    

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }    

    public Integer getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public Double getPrecioVentaAnterior() {
        return precioVentaAnterior;
    }

    public void setPrecioVentaAnterior(Double precioVentaAnterior) {
        this.precioVentaAnterior = precioVentaAnterior;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "DetalleIngreso [estado=" + estado + ", fechaProduccion=" + fechaProduccion + ", fechaVencimiento="
                + fechaVencimiento + ", iddetalleingreso=" + iddetalleingreso + ", idingreso=" + idingreso
                + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta + ", producto=" + producto
                + ", stockActual=" + stockActual + ", stockInicial=" + stockInicial + "]";
    }

    private static final long serialVersionUID = 1L;
    
}
