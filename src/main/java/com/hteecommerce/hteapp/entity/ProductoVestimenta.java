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
        
    @Size(max = 50)
    private String modelo;

    @Size(max = 50)
    private String material; 

    public ProductoVestimenta(){        
    }        

    public ProductoVestimenta(Integer idpvestimenta, @Size(max = 50) String modelo,
            @NotNull @Size(max = 50) String material) {
        this.idpvestimenta = idpvestimenta;
        this.modelo = modelo;
        this.material = material;       
        
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

    
    private static final long serialVersionUID = 1L;
    // Tallas, marca, modelo, material, color
}
