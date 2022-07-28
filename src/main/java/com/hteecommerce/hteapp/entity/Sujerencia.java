package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sujerencias")
public class Sujerencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idsujerencia;

    @NotNull
    private String detalle;

    private LocalDate fecha;

    public Sujerencia() {

    }

    @PrePersist
    public void generarFecha() {
        this.fecha = LocalDate.now();
    }

    public Integer getIdsujerencia() {
        return idsujerencia;
    }

    public void setIdsujerencia(Integer idsujerencia) {
        this.idsujerencia = idsujerencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    private static final long serialVersionUID = 1L;
}
