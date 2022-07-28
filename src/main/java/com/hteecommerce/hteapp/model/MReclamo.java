package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import com.hteecommerce.hteapp.entity.Reclamo;

public class MReclamo {
    
    private Integer idreclamo;   

    private String asunto;
    
    private LocalDate fecha;

    private String descripcion;
    
    private MCliente cliente;

    private MProveedor proveedor;

    public MReclamo(){

    }

    public MReclamo(Reclamo reclamo){
        this.idreclamo = reclamo.getIdreclamo();
        this.asunto = reclamo.getAsunto();
        this.fecha = reclamo.getFecha();
        this.descripcion = reclamo.getDescripcion();

        if(reclamo.getCliente() != null){
            this.cliente = new MCliente(reclamo.getCliente().getIdcliente(), reclamo.getCliente().getPersona());
        }

        if(reclamo.getProveedor() != null){
            this.proveedor = new MProveedor(
                reclamo.getProveedor().getIdproveedor(),reclamo.getProveedor().getRuc(),reclamo.getProveedor().getRazonSocial(),
                reclamo.getProveedor().getTelefono(),reclamo.getProveedor().getDireccion() ,reclamo.getProveedor().getFotoPerfil());
        }
                
    }

    public Integer getIdreclamo() {
        return idreclamo;
    }

    public void setIdreclamo(Integer idreclamo) {
        this.idreclamo = idreclamo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MCliente getCliente() {
        return cliente;
    }

    public void setCliente(MCliente cliente) {
        this.cliente = cliente;
    }

    public MProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(MProveedor proveedor) {
        this.proveedor = proveedor;
    }

    
}
