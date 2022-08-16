package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "proveedor_comprobantes")
public class ProveedorComprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproveedorcomprobante;

    private LocalDateTime fecha;

    @NotNull
    @Size(max = 20)
    private String estado;

    @NotNull
    private Double igv;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;

    @NotNull
    private Double total;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedorcomprobante_id")
    private List<ProveedorOferta> proveedorOfertas;

    public ProveedorComprobante() {
        this.proveedorOfertas = new ArrayList<>();
    }

    @PrePersist
    public void uploadDate() {
        this.fecha = LocalDateTime.now();
    }

    public Integer getIdproveedorcomprobante() {
        return idproveedorcomprobante;
    }

    public void setIdproveedorcomprobante(Integer idproveedorcomprobante) {
        this.idproveedorcomprobante = idproveedorcomprobante;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<ProveedorOferta> getProveedorOfertas() {
        return proveedorOfertas;
    }

    public void setProveedorOfertas(List<ProveedorOferta> proveedorOfertas) {
        this.proveedorOfertas = proveedorOfertas;
    }

    private static final long serialVersionUID = 1L;
}
