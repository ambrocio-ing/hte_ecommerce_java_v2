package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "colores")
public class Color implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcolor;

    @NotNull
    @Column(name = "nombre_color")
    private String nombreColor;

    @NotNull
    @Min(0)
    @Column(name = "cantidad_color")
    private Integer cantidadColor;

    @Column(name = "variedad_id")
    private Integer variedadId;

    public Color(){

    }

    public Integer getIdcolor() {
        return idcolor;
    }

    public void setIdcolor(Integer idcolor) {
        this.idcolor = idcolor;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    public Integer getCantidadColor() {
        return cantidadColor;
    }

    public void setCantidadColor(Integer cantidadColor) {
        this.cantidadColor = cantidadColor;
    }

    private static final long serialVersionUID = 1L;
}
