package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "comprobantes")
public class Comprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomprobante;
    
    @Column(unique = true)
    @Size(max = 10, min = 10)
    private String numero;

    @Size(max = 35)
    private String idtransaccion;

    @NotNull
    @Size(max = 15)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
    
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @NotNull
    @Size(max = 15)
    private String estado;

    @NotNull
    private Double igv;
    
    @Column(name = "monto_envio")
    private Double montoEnvio;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;

    @NotNull
    private Double total;

    private Double descuento;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    private String hora;

    @JsonIgnoreProperties(value = { "comprobantes", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddireccion", nullable = false)
    private DireccionEnvio direccionEnvio;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idcomprobante")
    public List<DetalleComprobante> detalleComprobantes;

    public Comprobante() {
        this.detalleComprobantes = new ArrayList<>();
    }

    @PrePersist
    public void cargarFecha() {
        this.fechaPedido = LocalDateTime.now();
    }

    public Double calculateIGV() {
        double igvtotal = this.total * 0.18;
        double igvmat = Math.round(igvtotal * 100);
        double igv = igvmat / 100;
        return igv;
    }    

    public Double calculateSubTotal() {
        return this.total - this.igv;
    }

    public Double calculateTotal() {
        Double total = 0.00;
        for (DetalleComprobante dc : this.detalleComprobantes) {
            total += dc.getSubTotal();
        }

        return total;
    }

    public Integer getIdcomprobante() {
        return idcomprobante;
    }

    public void setIdcomprobante(Integer idcomprobante) {
        this.idcomprobante = idcomprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public List<DetalleComprobante> getDetalleComprobantes() {
        return detalleComprobantes;
    }

    public void setDetalleComprobantes(List<DetalleComprobante> detalleComprobantes) {
        this.detalleComprobantes = detalleComprobantes;
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

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public DireccionEnvio getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(DireccionEnvio direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Double getMontoEnvio() {
        return montoEnvio;
    }

    public void setMontoEnvio(Double montoEnvio) {
        this.montoEnvio = montoEnvio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    private static final long serialVersionUID = 1L;

}
