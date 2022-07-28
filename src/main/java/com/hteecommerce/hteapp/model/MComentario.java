package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import com.hteecommerce.hteapp.entity.Comentario;

public class MComentario {

    private Integer idcomentario;

    private LocalDate fecha;

    private String nombre;

    private String descripcion;

    private int estrellas;

    private MCliente cliente;

    private MDetalleIngreso detalleIngreso;     

    public MComentario() {

    }
    
    public MComentario(Comentario comentario) {
        this.idcomentario = comentario.getIdcomentario();
        this.fecha = comentario.getFecha();
        this.nombre = comentario.getNombre();        
        this.descripcion = comentario.getDescripcion();
        this.estrellas = comentario.getEstrellas();
        this.cliente = new MCliente(comentario.getCliente().getIdcliente(), comentario.getCliente().getPersona());        
    }

    public Integer getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public MDetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(MDetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }  
    
}
