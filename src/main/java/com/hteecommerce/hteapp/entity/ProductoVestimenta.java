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
@Table(name = "producto_vestimentas")
public class ProductoVestimenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpvestimenta;
    
    @Size(max = 4)
    private String talla;
    
    @NotNull
    @Size(max = 50)
    private String marca;
    
    @Size(max = 50)
    private String modelo;

    @NotNull
    @Size(max = 50)
    private String material;
    
    @Size(max = 50)
    private String color;

    private String descripcion;

    public ProductoVestimenta(){

    }    

    public ProductoVestimenta(Integer idpvestimenta, @Size(max = 4) String talla, @NotNull @Size(max = 50) String marca,
            @Size(max = 50) String modelo, @NotNull @Size(max = 50) String material, @Size(max = 50) String color,
            String descripcion) {
        this.idpvestimenta = idpvestimenta;
        this.talla = talla;
        this.marca = marca;
        this.modelo = modelo;
        this.material = material;
        this.color = color;
        this.descripcion = descripcion;
    }

    public Integer getIdpvestimenta() {
        return idpvestimenta;
    }

    public void setIdpvestimenta(Integer idpvestimenta) {
        this.idpvestimenta = idpvestimenta;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    private static final long serialVersionUID = 1L;
    // Tallas, marca, modelo, material, color
}
