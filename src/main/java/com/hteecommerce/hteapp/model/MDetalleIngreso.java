package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.Variedad;

public class MDetalleIngreso {

    private Integer iddetalleingreso;

    private Double precioCompra;

    private Double precioVenta;

    private Double precioVentaAnterior;

    private Double porcentajeDescuento;

    private Double stockInicial;

    private Double stockActual;

    private LocalDate fechaProduccion;

    private LocalDate fechaVencimiento;

    private Boolean estado;

    private Boolean ventaPorGramo;

    private String sucursal;   

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Producto producto;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<Variedad> variedades;

    private Integer ingresoId;

    public MDetalleIngreso() {

    }

    //constructor para vista del lado administrativo
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
        this.ventaPorGramo = di.getVentaPorGramo();
        this.sucursal = di.getSucursal();
        this.producto = di.getProducto();

        if(di.getVariedades() != null && di.getVariedades().size() != 0){
            this.variedades = di.getVariedades();
        }
        else{
            this.variedades = new ArrayList<>();
        }
        
        this.ingresoId = di.getIngresoId();
    }    

    //constructor para vista del lado ecommerce
    public MDetalleIngreso(Integer iddetalleingreso, Double precioVenta, Double precioVentaAnterior,
            Double porcentajeDescuento, Double stockActual, LocalDate fechaProduccion,
            LocalDate fechaVencimiento, Boolean estado, Boolean ventaPorGramo, String sucursal, Producto producto, List<Variedad> variedades) {
        this.iddetalleingreso = iddetalleingreso;
        this.precioVenta = precioVenta;
        this.precioVentaAnterior = precioVentaAnterior;
        this.porcentajeDescuento = porcentajeDescuento;
        this.stockActual = stockActual;
        this.fechaProduccion = fechaProduccion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.ventaPorGramo = ventaPorGramo;
        this.sucursal = sucursal;
        this.producto = producto;
        
        if(variedades != null && variedades.size() != 0){
            this.variedades = variedades;
        }
        else{
            this.variedades = null;
        }
    }       

    //para que retorne con comprobante
    public MDetalleIngreso(Integer iddetalleingreso, Double precioVenta, String sucursal, Producto producto) {
        this.iddetalleingreso = iddetalleingreso;
        this.precioVenta = precioVenta;
        this.sucursal = sucursal;
        this.producto = producto;
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

    public Double getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Double stockInicial) {
        this.stockInicial = stockInicial;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
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

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public Boolean getVentaPorGramo() {
        return ventaPorGramo;
    }

    public void setVentaPorGramo(Boolean ventaPorGramo) {
        this.ventaPorGramo = ventaPorGramo;
    }     

}
