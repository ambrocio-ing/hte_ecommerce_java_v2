package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "producto_imagenes")
public class ProductoImagen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpimagen;

    @Size(max = 100)
    @Column(name = "imagen_uno")
    private String imagenUno;

    @Size(max = 100)
    @Column(name = "imagen_dos")
    private String imagenDos;

    @Size(max = 100)
    @Column(name = "imagen_tres")
    private String imagenTres;

    public ProductoImagen() {

    }    

    public Integer getIdpimagen() {
        return idpimagen;
    }

    public void setIdpimagen(Integer idpimagen) {
        this.idpimagen = idpimagen;
    }

    public String getImagenUno() {
        return imagenUno;
    }

    public void setImagenUno(String imagenUno) {
        this.imagenUno = imagenUno;
    }

    public String getImagenDos() {
        return imagenDos;
    }

    public void setImagenDos(String imagenDos) {
        this.imagenDos = imagenDos;
    }

    public String getImagenTres() {
        return imagenTres;
    }

    public void setImagenTres(String imagenTres) {
        this.imagenTres = imagenTres;
    }

    private static final long serialVersionUID = 1L;
}
