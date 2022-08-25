package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idingrediente;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_producto")
    private String nombreProducto;

    @NotNull
    private Double cantidad;

    private String unidad;

    private String descripcion;

    @Column(name = "potaje_id")
    private Integer potajeId;

    public Ingrediente(){

    }
    
    public Integer getIdingrediente() {
        return idingrediente;
    }

    public void setIdingrediente(Integer idingrediente) {
        this.idingrediente = idingrediente;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    public Integer getPotajeId() {
        return potajeId;
    }

    public void setPotajeId(Integer potajeId) {
        this.potajeId = potajeId;
    }
}
