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
    private String imagen;   
    
    @Column(name = "producto_id")
    private Integer productoId;

    public ProductoImagen() {

    }    

    public Integer getIdpimagen() {
        return idpimagen;
    }

    public void setIdpimagen(Integer idpimagen) {
        this.idpimagen = idpimagen;
    }   

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    private static final long serialVersionUID = 1L;
    
}
