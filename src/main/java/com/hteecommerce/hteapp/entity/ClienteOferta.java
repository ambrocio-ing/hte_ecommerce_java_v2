package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente_ofertas")
public class ClienteOferta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idclienteoferta;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    private Double precio;

    @NotNull
    private int cantidad;

    @NotNull
    @Size(max = 10)
    private String unidad;

    @NotNull
    private String calidad;

    @NotNull
    private String descripcion;

    @NotNull
    private String imagen;    
    
    private Integer idclientecomprobante;
    
    public ClienteOferta() {

    }

    public Integer getIdclienteoferta() {
        return idclienteoferta;
    }

    public void setIdclienteoferta(Integer idclienteoferta) {
        this.idclienteoferta = idclienteoferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getIdclientecomprobante() {
        return idclientecomprobante;
    }

    public void setIdclientecomprobante(Integer idclientecomprobante) {
        this.idclientecomprobante = idclientecomprobante;
    }

    private static final long serialVersionUID = 1L;

}
