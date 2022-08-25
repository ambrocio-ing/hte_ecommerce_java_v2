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
@Table(name = "deliverys")
public class Delivery implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddelivery;

    @NotNull
    @Size(max = 100)    
    private String empresa;

    @NotNull
    private Double costo;

    @NotNull
    private String sucursal;

    @Size(max = 100)
    private String detalle;

    public Delivery() {
        
    }

    public Integer getIddelivery() {
        return iddelivery;
    }

    public void setIddelivery(Integer iddelivery) {
        this.iddelivery = iddelivery;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public static final long serialVersionUID = 1L;
}
