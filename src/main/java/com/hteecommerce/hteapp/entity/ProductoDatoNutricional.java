package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "producto_datos_nutricionales")
public class ProductoDatoNutricional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddnutricional;

    @Size(max = 50)
    private String calorias;

    @Size(max = 50)
    private String grasa;

    @Size(max = 50)
    private String colesterol;

    @Size(max = 50)
    private String sodio;

    @Size(max = 50)
    private String carbohidrato;

    @Size(max = 50)
    private String proteina;

    @Size(max = 100)
    private String vitamina;

    @Size(max = 50)
    private String calcio;

    @Size(max = 50)
    private String hierro;

    @Size(max = 100)
    private String minerales;

    public ProductoDatoNutricional() {

    }

    public ProductoDatoNutricional(Integer iddnutricional, @Size(max = 50) String calorias,
            @Size(max = 50) String grasa, @Size(max = 50) String colesterol, @Size(max = 50) String sodio,
            @Size(max = 50) String carbohidrato, @Size(max = 50) String proteina, @Size(max = 50) String vitamina,
            @Size(max = 50) String calcio, @Size(max = 50) String hierro, @Size(max = 100) String minerales) {
        this.iddnutricional = iddnutricional;
        this.calorias = calorias;
        this.grasa = grasa;
        this.colesterol = colesterol;
        this.sodio = sodio;
        this.carbohidrato = carbohidrato;
        this.proteina = proteina;
        this.vitamina = vitamina;
        this.calcio = calcio;
        this.hierro = hierro;
        this.minerales = minerales;
    }

    public Integer getIddnutricional() {
        return iddnutricional;
    }

    public void setIddnutricional(Integer iddnutricional) {
        this.iddnutricional = iddnutricional;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public String getGrasa() {
        return grasa;
    }

    public void setGrasa(String grasa) {
        this.grasa = grasa;
    }

    public String getColesterol() {
        return colesterol;
    }

    public void setColesterol(String colesterol) {
        this.colesterol = colesterol;
    }

    public String getSodio() {
        return sodio;
    }

    public void setSodio(String sodio) {
        this.sodio = sodio;
    }

    public String getCarbohidrato() {
        return carbohidrato;
    }

    public void setCarbohidrato(String carbohidrato) {
        this.carbohidrato = carbohidrato;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getVitamina() {
        return vitamina;
    }

    public void setVitamina(String vitamina) {
        this.vitamina = vitamina;
    }

    public String getCalcio() {
        return calcio;
    }

    public void setCalcio(String calcio) {
        this.calcio = calcio;
    }

    public String getHierro() {
        return hierro;
    }

    public void setHierro(String hierro) {
        this.hierro = hierro;
    }

    public String getMinerales() {
        return minerales;
    }

    public void setMinerales(String minerales) {
        this.minerales = minerales;
    }

    private static final long serialVersionUID = 1L;
    // -Calorías, Grasa total, Colesterol, Sodio, Carbohidrato, Proteína, Vitaminas,
    // Calcio, Hierro
}
