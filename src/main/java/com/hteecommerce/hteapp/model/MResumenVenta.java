package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//import com.hteecommerce.hteapp.entity.DetalleComprobante;

public class MResumenVenta {

    private String nombreProducto;
    private String imagenProducto;    
    private Double cantidadTotal;
    private LocalDate fechaEntrega;
    private List<MDetalleResumenVenta> detalleResumenVentas;

    public MResumenVenta(){

    }      

    public MResumenVenta(String nombreProducto, String imagenProducto, List<MDetalleResumenVenta> detalleResumenes) {
        this.nombreProducto = nombreProducto;
        this.imagenProducto = imagenProducto;
        this.cantidadTotal = detalleResumenes.stream().collect(Collectors.summingDouble(drm -> drm.getCantidadProducto()));        
        this.detalleResumenVentas = detalleResumenes;
    }    

    public MResumenVenta(String nombreProducto, String imagenProducto,
            LocalDate fechaEntrega, List<MDetalleResumenVenta> detalleResumenVentas) {
        this.nombreProducto = nombreProducto;
        this.imagenProducto = imagenProducto;
        this.cantidadTotal = detalleResumenVentas.stream().collect(Collectors.summingDouble(drm -> drm.getCantidadProducto()));
        this.fechaEntrega = fechaEntrega;
        this.detalleResumenVentas = detalleResumenVentas;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }     

    public List<MDetalleResumenVenta> getDetalleResumenVentas() {
        return detalleResumenVentas;
    }

    public void setDetalleResumenVentas(List<MDetalleResumenVenta> detalleResumenVentas) {
        this.detalleResumenVentas = detalleResumenVentas;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }      
    
}
