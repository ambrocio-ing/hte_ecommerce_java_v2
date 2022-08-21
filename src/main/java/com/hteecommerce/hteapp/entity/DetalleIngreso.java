package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalle_ingresos")
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
    @Min(0)
    private Integer stockActual;

    @Column(name = "fecha_produccion")
    private LocalDate fechaProduccion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @NotNull
    private Boolean estado;

    @NotNull
    @Size(max = 30)
    private String sucursal;

    @Column(name = "create_at")
    private LocalDate createAt;

    //@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detalleingreso_id", nullable = true)
    private List<Variedad> variedades = null;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "ingreso_id")
    private Integer ingresoId;

    public DetalleIngreso() {
        this.variedades = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    public Integer calculateStockActual(Integer stock_actual) {
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

    public Integer getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(Integer ingresoId) {
        this.ingresoId = ingresoId;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    private static final long serialVersionUID = 1L;

}
