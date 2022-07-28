package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ingresos")
public class Ingreso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idingreso;
    
    private LocalDate fecha;

    @NotNull
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @NotNull
    private Double igv;

    @NotNull
    @Size(max = 12)
    private String estado;

    @JsonIgnoreProperties(value = {"ingresos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonal", nullable = false)
    private Personal personal;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idingreso")
    public List<DetalleIngreso> detalleIngresos;

    @NotNull
    @Size(max = 11, min = 10)
    private String ruc;

    public Ingreso(){
        this.detalleIngresos = new ArrayList<>();
    }    

    @PrePersist
    public void uploadDate(){
        this.fecha = LocalDate.now();
    }

    public Integer getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<DetalleIngreso> getDetalleIngresos() {
        return detalleIngresos;
    }

    public void setDetalleIngresos(List<DetalleIngreso> detalleIngresos) {
        this.detalleIngresos = detalleIngresos;
    }

    private static final long serialVersionUID = 1L;
}
