package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Producto;

public class MDetalleIngreso {

    private Integer iddetalleingreso;

    private Double precioCompra;

    private Double precioVenta;

    private Double precioVentaAnterior;

    private Double porcentajeDescuento;

    private Integer stockInicial;

    private Integer stockActual;

    private LocalDate fechaProduccion;

    private LocalDate fechaVencimiento;

    private Boolean estado;

    private String sucursal;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Producto producto;

    private Integer ingresoId;

    public MDetalleIngreso() {

    }

    public MDetalleIngreso(DetalleIngreso di) {
        this.iddetalleingreso = di.getIddetalleingreso();
        this.precioCompra = di.getPrecioCompra();
        this.precioVenta = di.getPrecioVenta();
        this.precioVentaAnterior = di.getPrecioVentaAnterior();
        this.porcentajeDescuento = di.getPorcentajeDescuento();
        this.stockInicial = di.getStockInicial();
        this.stockActual = di.getStockActual();
        this.fechaProduccion = di.getFechaProduccion();
        this.fechaVencimiento = di.getFechaVencimiento();
        this.estado = di.getEstado();
        this.sucursal = di.getSucursal();
        this.producto = di.getProducto();
        this.ingresoId = di.getIngresoId();
    }

    public MDetalleIngreso(Integer iddetalleingreso, Double precioVenta, Double precioVentaAnterior,
            Double porcentajeDescuento, Integer stockInicial, Integer stockActual,
            LocalDate fechaProduccion, LocalDate fechaVencimiento, Boolean estado,
            String sucursal, Producto producto, Integer ingresoId) {
        this.iddetalleingreso = iddetalleingreso;
        this.precioVenta = precioVenta;
        this.precioVentaAnterior = precioVentaAnterior;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stockInicial = stockInicial;
        this.stockActual = stockActual;
        this.fechaProduccion = fechaProduccion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.sucursal = sucursal;
        this.producto = producto;
        this.ingresoId = ingresoId;
    }

    public MDetalleIngreso(Integer iddetalleingreso, Double precioVenta, Double precioVentaAnterior,
            Double porcentajeDescuento, Integer stockActual, LocalDate fechaProduccion,
            LocalDate fechaVencimiento, Boolean estado, String sucursal, Producto producto) {
        this.iddetalleingreso = iddetalleingreso;
        this.precioVenta = precioVenta;
        this.precioVentaAnterior = precioVentaAnterior;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stockActual = stockActual;
        this.fechaProduccion = fechaProduccion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.sucursal = sucursal;
        this.producto = producto;
    }

    public MDetalleIngreso(Integer iddetalleingreso, Double precioVenta, Double precioVentaAnterior,
            Double porcentajeDescuento, Integer stockInicial, Integer stockActual,
            Boolean estado, String sucursal) {
        this.iddetalleingreso = iddetalleingreso;
        this.precioVenta = precioVenta;
        this.precioVentaAnterior = precioVentaAnterior;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stockInicial = stockInicial;
        this.stockActual = stockActual;
        this.estado = estado;
        this.sucursal = sucursal;
    }

    public MDetalleIngreso(Integer iddetalleingreso, String sucursal, Producto producto, Integer ingresoId) {
        this.iddetalleingreso = iddetalleingreso;
        this.sucursal = sucursal;
        this.producto = producto;
        this.ingresoId = ingresoId;
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

    public Integer getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(Integer ingresoId) {
        this.ingresoId = ingresoId;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}
