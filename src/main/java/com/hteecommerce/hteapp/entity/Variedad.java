package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
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

    @Column(name = "pvestimenta_id")
    private Integer pvestimentaId;

    @Column(name = "carrito_id")
    private Integer carritoId;

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

    public Integer getPvestimentaId() {
        return pvestimentaId;
    }

    public void setPvestimentaId(Integer pvestimentaId) {
        this.pvestimentaId = pvestimentaId;
    }

    public Integer getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Integer carritoId) {
        this.carritoId = carritoId;
    }

    private static final long serialVersionUID = 1L;

}
