package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "variedades")
public class Variedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idvariedad;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre_talla")
    private String nombreTalla;

    @NotNull
    @Min(0)
    @Column(name = "cantidad_talla")
    private Integer cantidadTalla;

    @Column(name = "detalleingreso_id")
    private Integer detalleingresoId;

    @Column(name = "detallecomprobante_id")
    private Integer detallecomprobanteId;

    @Column(name = "carrito_id")
    private Integer carritoId;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "variedad_id", nullable = true)
    private List<Color> colores = null;

    public Variedad() {
        this.colores = new ArrayList<>();
    }

    public Variedad(@NotNull @Size(max = 20) String nombreTalla, @NotNull @Min(0) Integer cantidadTalla) {
        this.nombreTalla = nombreTalla;
        this.cantidadTalla = cantidadTalla;
    }

    public Integer getIdvariedad() {
        return idvariedad;
    }

    public void setIdvariedad(Integer idvariedad) {
        this.idvariedad = idvariedad;
    }

    public String getNombreTalla() {
        return nombreTalla;
    }

    public void setNombreTalla(String nombreTalla) {
        this.nombreTalla = nombreTalla;
    }

    public Integer getCantidadTalla() {
        return cantidadTalla;
    }

    public void setCantidadTalla(Integer cantidadTalla) {
        this.cantidadTalla = cantidadTalla;
    }   

    public Integer getDetalleingresoId() {
        return detalleingresoId;
    }

    public Integer getDetallecomprobanteId() {
        return detallecomprobanteId;
    }

    public Integer getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Integer carritoId) {
        this.carritoId = carritoId;
    }

    public List<Color> getColores() {
        return colores;
    }

    public void setColores(List<Color> colores) {
        this.colores = colores;
    }

    private static final long serialVersionUID = 1L;

}
