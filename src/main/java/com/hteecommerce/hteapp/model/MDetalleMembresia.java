package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.DetalleMembresia;
import com.hteecommerce.hteapp.entity.DetallePago;
import com.hteecommerce.hteapp.entity.Membresia;
import com.hteecommerce.hteapp.mapper.Mapper;

public class MDetalleMembresia {

    private Integer iddetallemembresia;

    private String idtransaccion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String imagen;

    private Double montoTotal;

    private MCliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Membresia membresia;

    private MDetallePago detallePago;

    public MDetalleMembresia(){

    }

    public MDetalleMembresia(DetalleMembresia dm){
        this.iddetallemembresia = dm.getIddetallemembresia();
        this.idtransaccion = dm.getIdtransaccion();
        this.fechaInicio = dm.getFechaInicio();
        this.fechaFin = dm.getFechaFin();
        this.imagen = dm.getImagen();
        this.montoTotal = dm.getMontoTotal();
        this.cliente = new MCliente(dm.getCliente().getIdcliente());
        this.membresia = dm.getMembresia();
        this.detallePago = Mapper.mapDetallePago(dm.getDetallePago());
    }

    

    public MDetalleMembresia(Integer iddetallemembresia, String idtransaccion, LocalDate fechaInicio,
            LocalDate fechaFin, String imagen, Double montoTotal, Cliente cliente, Membresia membresia, DetallePago detallePago) {
        this.iddetallemembresia = iddetallemembresia;
        this.idtransaccion = idtransaccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.imagen = imagen;
        this.montoTotal = montoTotal;
        this.cliente = new MCliente(cliente.getIdcliente(), cliente.getPersona());
        this.membresia = membresia;
        this.detallePago = Mapper.mapDetallePago(detallePago);
    }

    public Integer getIddetallemembresia() {
        return iddetallemembresia;
    }

    public void setIddetallemembresia(Integer iddetallemembresia) {
        this.iddetallemembresia = iddetallemembresia;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public MCliente getCliente() {
        return cliente;
    }

    public void setCliente(MCliente cliente) {
        this.cliente = cliente;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public MDetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(MDetallePago detallePago) {
        this.detallePago = detallePago;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

}
