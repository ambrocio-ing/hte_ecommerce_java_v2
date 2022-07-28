package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import com.hteecommerce.hteapp.entity.DetalleMembresia;
import com.hteecommerce.hteapp.entity.Membresia;

public class MDetalleMembresia {

    private Integer iddetallemembresia;

    private String idtransaccion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private MCliente cliente;

    private Membresia membresia;

    public MDetalleMembresia(){

    }

    public MDetalleMembresia(DetalleMembresia dm){
        this.iddetallemembresia = dm.getIddetallemembresia();
        this.idtransaccion = dm.getIdtransaccion();
        this.fechaInicio = dm.getFechaInicio();
        this.fechaFin = dm.getFechaFin();
        this.cliente = new MCliente(dm.getCliente().getIdcliente());
        this.membresia = dm.getMembresia();
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

    
}
