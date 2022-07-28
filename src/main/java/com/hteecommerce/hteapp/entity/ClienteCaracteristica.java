package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente_caracteristica")
public class ClienteCaracteristica implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcaracteristica;

    private String pasatiempo;

    private String enfermedad;

    private String alergia;

    private String dieta;

    private String ocupacion;

    private String deseo;

    //-Id, Pasatiempo, enfermedades, alergias, régimen alimenticio-dieta, 
    //ocupación, deseo aprender (aspiraciones personales).   

    public ClienteCaracteristica(){

    }

    public ClienteCaracteristica(String pasatiempo, String enfermedad, String alergia, String dieta, String ocupacion,
            String deseo) {
        this.pasatiempo = pasatiempo;
        this.enfermedad = enfermedad;
        this.alergia = alergia;
        this.dieta = dieta;
        this.ocupacion = ocupacion;
        this.deseo = deseo;
    }

    public Integer getIdcaracteristica() {
        return idcaracteristica;
    }

    public void setIdcaracteristica(Integer idcaracteristica) {
        this.idcaracteristica = idcaracteristica;
    }

    public String getPasatiempo() {
        return pasatiempo;
    }

    public void setPasatiempo(String pasatiempo) {
        this.pasatiempo = pasatiempo;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDeseo() {
        return deseo;
    }

    public void setDeseo(String deseo) {
        this.deseo = deseo;
    }    

    private static final long serialVersionUID = 1L;

}
