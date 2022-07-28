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
@Table(name = "cliente_comprobantes")
public class ClienteComprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idclientecomprobante;

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
    @JoinColumn(name = "idcp", nullable = false)
    private ClienteProveedor clienteProveedor;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idclientecomprobante")
    private List<ClienteOferta> clienteOfertas;

    public ClienteComprobante() {
        this.clienteOfertas = new ArrayList<>();
    }

    @PrePersist
    public void uploadDate() {
        this.fecha = LocalDateTime.now();
    }

    public Integer getIdclientecomprobante() {
        return idclientecomprobante;
    }

    public void setIdclientecomprobante(Integer idclientecomprobante) {
        this.idclientecomprobante = idclientecomprobante;
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

    public ClienteProveedor getClienteProveedor() {
        return clienteProveedor;
    }

    public void setClienteProveedor(ClienteProveedor clienteProveedor) {
        this.clienteProveedor = clienteProveedor;
    }

    public List<ClienteOferta> getClienteOfertas() {
        return clienteOfertas;
    }

    public void setClienteOfertas(List<ClienteOferta> clienteOfertas) {
        this.clienteOfertas = clienteOfertas;
    }

    private static final long serialVersionUID = 1L;
}
