package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "variedades")
public class Variedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idvariedad;

    @NotNull
    @Size(max = 20)
    private String nombre;

    @NotNull
    @Min(0)
    private Integer cantidad;

    private Integer idpvestimenta;

    private Integer idcarrito;

    public Variedad() {

    }

    public Variedad(@NotNull @Size(max = 20) String nombre, @NotNull @Min(0) Integer cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Integer getIdvariedad() {
        return idvariedad;
    }

    public void setIdvariedad(Integer idvariedad) {
        this.idvariedad = idvariedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdpvestimenta() {
        return idpvestimenta;
    }

    public void setIdpvestimenta(Integer idpvestimenta) {
        this.idpvestimenta = idpvestimenta;
    }

    public Integer getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Integer idcarrito) {
        this.idcarrito = idcarrito;
    }

    private static final long serialVersionUID = 1L;

}
