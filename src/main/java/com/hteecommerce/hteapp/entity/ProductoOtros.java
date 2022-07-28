package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "producto_otros")
public class ProductoOtros implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpotros;

    @Size(max = 50)
    private String marca;

    @Size(max = 50)
    private String modelo;

    @Size(max = 50)
    private String color;

    @Size(max = 50)
    private String material;

    @Size(max = 50)
    private String medida;

    @Size(max = 50)
    private String peso;

    private String detalle;

    public ProductoOtros() {

    }    

    public ProductoOtros(Integer idpotros, @Size(max = 50) String marca, @Size(max = 50) String modelo,
            @Size(max = 50) String color, @Size(max = 50) String material, @Size(max = 50) String medida,
            @Size(max = 50) String peso, String detalle) {
        this.idpotros = idpotros;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.material = material;
        this.medida = medida;
        this.peso = peso;
        this.detalle = detalle;
    }

    public Integer getIdpotros() {
        return idpotros;
    }

    public void setIdpotros(Integer idpotros) {
        this.idpotros = idpotros;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    // Marca, modelo, color, material, medida, peso, detalle
    private static final long serialVersionUID = 1L;
}
