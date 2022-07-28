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

    private Double calorias;

    private Double grasa;

    private Double colesterol;

    private Double sodio;

    private Double carbohidrato;

    private Double proteina;

    @Size(max = 12)
    private String vitamina;

    private Double calcio;

    private Double hierro;

    @Size(max = 100)
    private String minerales;

    public ProductoDatoNutricional() {

    }

    public ProductoDatoNutricional(Integer iddnutricional, Double calorias, Double grasa, Double colesterol,
            Double sodio, Double carbohidrato, Double proteina, @Size(max = 12) String vitamina, Double calcio,
            Double hierro, @Size(max = 100) String minerales) {
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

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public Double getGrasa() {
        return grasa;
    }

    public void setGrasa(Double grasa) {
        this.grasa = grasa;
    }

    public Double getColesterol() {
        return colesterol;
    }

    public void setColesterol(Double colesterol) {
        this.colesterol = colesterol;
    }

    public Double getSodio() {
        return sodio;
    }

    public void setSodio(Double sodio) {
        this.sodio = sodio;
    }

    public Double getCarbohidrato() {
        return carbohidrato;
    }

    public void setCarbohidrato(Double carbohidrato) {
        this.carbohidrato = carbohidrato;
    }

    public Double getProteina() {
        return proteina;
    }

    public void setProteina(Double proteina) {
        this.proteina = proteina;
    }

    public String getVitamina() {
        return vitamina;
    }

    public void setVitamina(String vitamina) {
        this.vitamina = vitamina;
    }

    public Double getCalcio() {
        return calcio;
    }

    public void setCalcio(Double calcio) {
        this.calcio = calcio;
    }

    public Double getHierro() {
        return hierro;
    }

    public void setHierro(Double hierro) {
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
