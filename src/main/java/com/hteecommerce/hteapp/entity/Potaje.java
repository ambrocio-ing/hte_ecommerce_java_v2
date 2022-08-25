package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "potajes")
public class Potaje implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpotaje;

    @Column(name = "nombre_potaje")
    @Size(max = 100)
    private String nombrePotaje;

    private String detalle;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "potaje_ingredientes", 
                joinColumns = @JoinColumn(name = "potaje_id"),
                inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredientes = null;

    public Potaje(){
        this.ingredientes = new ArrayList<>();
    }

    public Integer getIdpotaje() {
        return idpotaje;
    }

    public void setIdpotaje(Integer idpotaje) {
        this.idpotaje = idpotaje;
    }

    public String getNombrePotaje() {
        return nombrePotaje;
    }

    public void setNombrePotaje(String nombrePotaje) {
        this.nombrePotaje = nombrePotaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    private static final long serialVersionUID = 1L;
}
