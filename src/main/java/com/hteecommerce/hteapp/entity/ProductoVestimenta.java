package com.hteecommerce.hteapp.entity;

import java.util.Set;
import java.util.HashSet;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "producto_vestimentas")
public class ProductoVestimenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpvestimenta;  
        
    @Size(max = 50)
    private String modelo;

    @NotNull
    @Size(max = 50)
    private String material;
    
    @Size(max = 50)
    private String color;

    private String descripcion;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pvestimenta_id", referencedColumnName = "idpvestimenta")
    private Set<Variedad> variedades = null;

    public ProductoVestimenta(){
        this.variedades = new HashSet<>();
    }        

    public ProductoVestimenta(Integer idpvestimenta, @Size(max = 50) String modelo,
            @NotNull @Size(max = 50) String material, @Size(max = 50) String color, String descripcion,
            Set<Variedad> variedades) {
        this.idpvestimenta = idpvestimenta;
        this.modelo = modelo;
        this.material = material;
        this.color = color;
        this.descripcion = descripcion;
        this.variedades = variedades;
    }

    public Integer getIdpvestimenta() {
        return idpvestimenta;
    }

    public void setIdpvestimenta(Integer idpvestimenta) {
        this.idpvestimenta = idpvestimenta;
    }    

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   

    public Set<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(Set<Variedad> variedades) {
        this.variedades = variedades;
    }

    private static final long serialVersionUID = 1L;
    // Tallas, marca, modelo, material, color
}
